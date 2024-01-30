package az.azercellfintech.otp.ms.service;

import az.azercellfintech.otp.ms.queue.RabbitMessageProducer;
import az.azercellfintech.otp.ms.configuration.QueueProperties;
import az.azercellfintech.otp.ms.exception.OtpException;
import az.azercellfintech.otp.ms.model.enums.MessageTemplate;
import az.azercellfintech.otp.ms.model.redis.RedisOtpDto;
import az.azercellfintech.otp.ms.model.request.CheckOtpRequest;
import az.azercellfintech.otp.ms.model.request.SendOtpRequest;
import az.azercellfintech.otp.ms.model.response.SendOtpResponse;
import az.azercellfintech.otp.ms.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import static az.azercellfintech.otp.ms.mapper.MailRequestBuilder.buildEmailRequest;
import static az.azercellfintech.otp.ms.mapper.RedisOtpObjectBuilder.mapToRedisObject;
import static az.azercellfintech.otp.ms.model.enums.ResponseMessages.OTP_EXPIRED;
import static az.azercellfintech.otp.ms.util.GenerateOtpUtil.generateOtp;
import static az.azercellfintech.otp.ms.util.KeyGenerateUtil.generateKey;
import static az.azercellfintech.otp.ms.util.OtpValidator.validateOtp;
import static java.time.Duration.between;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PRIVATE;

@Log
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OtpService {
    RedisUtil redisUtil;
    RabbitMessageProducer producer;
    QueueProperties queueProperties;

    public SendOtpResponse sendOtp(SendOtpRequest sendOtpRequest) {
        var otp = generateOtp(sendOtpRequest.getOtpLength());
        var identifier = generateKey(sendOtpRequest.getServiceName());
        producer.sendMessage(
                buildEmailRequest(
                        sendOtpRequest.getDestination(),
                        "Otp Message",
                        MessageTemplate.DEFAULT_MAIL_TEMPLATE.getMessage(otp)
                        ),
                queueProperties.getMailPQueue()
        );
        redisUtil.save(identifier, mapToRedisObject(sendOtpRequest, otp), Long.valueOf(sendOtpRequest.getExpirationSecond()));
        return new SendOtpResponse(identifier);
    }

    public void checkOtp(CheckOtpRequest checkOtpRequest) {
        var redisOtpObject = redisUtil.get(checkOtpRequest.getIdentifier(), RedisOtpDto.class)
                .orElseThrow(() -> new OtpException(OTP_EXPIRED.getMessage(), OTP_EXPIRED.name()));
        try {
            validateOtp(checkOtpRequest.getOtp(), redisOtpObject);
            redisUtil.removeKey(checkOtpRequest.getIdentifier());
        } catch (Exception ex) {
            redisOtpObject.setAttemptCount(redisOtpObject.getAttemptCount() + 1);
            redisUtil.updateOrDelete(checkOtpRequest.getIdentifier(), redisOtpObject, between(now(), redisOtpObject.getExpirationTime()).getSeconds());
            throw ex;
        }
    }

}
