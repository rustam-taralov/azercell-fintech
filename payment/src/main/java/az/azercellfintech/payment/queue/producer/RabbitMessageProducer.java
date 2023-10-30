package az.azercellfintech.payment.queue.producer;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static az.azercellfintech.payment.util.MapperUtil.mapToString;
import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RabbitMessageProducer {

    RabbitTemplate rabbitTemplate;

    public <T> void sendMessage(T messageObject, String targetQueue) {
        rabbitTemplate.convertAndSend(targetQueue, mapToString(messageObject));
    }
}
