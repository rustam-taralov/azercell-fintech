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
@ConfigurationProperties(prefix = "rabbit.consumer.refund-rejection")
public class RefundRejectionQueueConfig {
    String queue;
    String key;
    String exchange;
    String dlq;
    String dlqKey;
    String dlqExchange;

    @Bean
    DirectExchange refundDLQExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean
    DirectExchange refundQExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue refundDLQ() {
        return QueueBuilder.durable(dlq).build();
    }

    @Bean
    Queue refundQ() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", dlqExchange)
                .withArgument("x-dead-letter-routing-key", dlqKey)
                .build();
    }

    @Bean
    Binding refundDLQBinding(Queue refundDLQ, DirectExchange refundDLQExchange) {
        return BindingBuilder.bind(refundDLQ)
                .to(refundDLQExchange).with(dlqKey);
    }

    @Bean
    Binding refundQBinding(Queue refundQ, DirectExchange refundQExchange) {
        return BindingBuilder.bind(refundQ)
                .to(refundQExchange).with(key);
    }

}