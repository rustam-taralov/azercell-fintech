package az.azercellfintech.payment.configuration;

import static lombok.AccessLevel.PRIVATE;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;

@ConstructorBinding
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@ConfigurationProperties(prefix = "rabbit.consumer.refund-success")
public class RefundSuccessQueueConfig {
    String queue;
    String key;
    String exchange;
    String dlq;
    String dlqKey;
    String dlqExchange;

    @Bean
    DirectExchange refundSuccessDLQExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean
    DirectExchange refundSuccessQExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue refundSuccessDLQ() {
        return QueueBuilder.durable(dlq).build();
    }

    @Bean
    Queue refundSuccessQ() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", dlqExchange)
                .withArgument("x-dead-letter-routing-key", dlqKey)
                .build();
    }

    @Bean
    Binding refundSuccessDLQBinding(Queue refundDLQ, DirectExchange refundDLQExchange) {
        return BindingBuilder.bind(refundDLQ)
                .to(refundDLQExchange).with(dlqKey);
    }

    @Bean
    Binding refundSuccessQBinding(Queue refundQ, DirectExchange refundQExchange) {
        return BindingBuilder.bind(refundQ)
                .to(refundQExchange).with(key);
    }

}