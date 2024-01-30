package az.azercellfintech.customer.ms.client;

import az.azercellfintech.customer.ms.configuration.ClientErrorDecoder;
import az.azercellfintech.customer.ms.model.request.SendOtpRequest;
import az.azercellfintech.customer.ms.model.request.CheckOtpRequest;
import az.azercellfintech.customer.ms.model.response.SendOtpResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(value = "otp-ms", path = "/otp-ms/v1/otp", configuration = ClientErrorDecoder.class)
public interface OtpClient {

    @PostMapping("send")
    SendOtpResponse sendOtp(@RequestBody @Valid SendOtpRequest sendOtpRequest);

    @PostMapping("check")
    void checkOtp(@RequestBody @Valid CheckOtpRequest checkOtpRequest);
}
