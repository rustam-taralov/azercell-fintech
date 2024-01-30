package az.azercellfintech.emailservice.queue;

import az.azercellfintech.emailservice.model.dto.MailContentDto;
import az.azercellfintech.emailservice.service.MailService;
import az.azercellfintech.emailservice.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static az.azercellfintech.emailservice.util.MapperUtil.mapToObject;
import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MailListener {
    MailService mailService;

    @RabbitListener(queues = "${rabbit.queue}")
    public void getMessage(String message){
        mailService.sendMail(mapToObject(message,MailContentDto.class));
    }
}
