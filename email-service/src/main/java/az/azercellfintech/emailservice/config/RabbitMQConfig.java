package az.azercellfintech.emailservice.config;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Bean;

import static lombok.AccessLevel.PRIVATE;

@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "rabbit")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RabbitMQConfig {
    String queue;
    String key;
    String exchange;
    String dlq;
    String dlqKey;
    String dlqExchange;

    @Bean
    DirectExchange mailDLQExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean
    DirectExchange mailQExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue mailDLQ() {
        return QueueBuilder.durable(dlq).build();
    }

    @Bean
    Queue mailQ() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", dlqExchange)
                .withArgument("x-dead-letter-routing-key", dlqKey)
                .build();
    }

    @Bean
    Binding mailDLQBinding(Queue mailDLQ, DirectExchange mailDLQExchange) {
        return BindingBuilder.bind(mailDLQ)
                .to(mailDLQExchange).with(dlqKey);
    }

    @Bean
    Binding mailQBinding(Queue mailQ, DirectExchange mailQExchange) {
        return BindingBuilder.bind(mailQ)
                .to(mailQExchange).with(key);
    }

}