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
@ConfigurationProperties(prefix = "rabbit.consumer.top-up-success")
public class TopUpSuccessQueueConfig {
    String queue;
    String key;
    String exchange;
    String dlq;
    String dlqKey;
    String dlqExchange;

    @Bean
    DirectExchange topUpSuccessDLQExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean
    DirectExchange topUpSuccessQExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue topUpSuccessDLQ() {
        return QueueBuilder.durable(dlq).build();
    }

    @Bean
    Queue topUpSuccessQ() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", dlqExchange)
                .withArgument("x-dead-letter-routing-key", dlqKey)
                .build();
    }

    @Bean
    Binding topUpSuccessDLQBinding(Queue topUpDLQ, DirectExchange topUpDLQExchange) {
        return BindingBuilder.bind(topUpDLQ)
                .to(topUpDLQExchange).with(dlqKey);
    }

    @Bean
    Binding topUpSuccessQBinding(Queue topUpQ, DirectExchange topUpQExchange) {
        return BindingBuilder.bind(topUpQ)
                .to(topUpQExchange).with(key);
    }

}