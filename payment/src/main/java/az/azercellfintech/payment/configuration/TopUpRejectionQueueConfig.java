package az.azercellfintech.payment.configuration;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;

import static lombok.AccessLevel.PRIVATE;

@ConstructorBinding
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ConfigurationProperties(prefix = "rabbit.consumer.top-up-rejection")
public class TopUpRejectionQueueConfig {
    String queue;
    String key;
    String exchange;
    String dlq;
    String dlqKey;
    String dlqExchange;

    @Bean
    DirectExchange topUpDLQExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean
    DirectExchange topUpQExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue topUpDLQ() {
        return QueueBuilder.durable(dlq).build();
    }

    @Bean
    Queue topUpQ() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", dlqExchange)
                .withArgument("x-dead-letter-routing-key", dlqKey)
                .build();
    }

    @Bean
    Binding topUpDLQBinding(Queue topUpDLQ, DirectExchange topUpDLQExchange) {
        return BindingBuilder.bind(topUpDLQ)
                .to(topUpDLQExchange).with(dlqKey);
    }

    @Bean
    Binding topUpQBinding(Queue topUpQ, DirectExchange topUpQExchange) {
        return BindingBuilder.bind(topUpQ)
                .to(topUpQExchange).with(key);
    }

}