package az.azercellfintech.customer.ms.configuration;

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
@ConfigurationProperties(prefix = "rabbit.consumer.purchase")
public class PurchaseQueueConfig {
    String queue;
    String key;
    String exchange;
    String dlq;
    String dlqKey;
    String dlqExchange;

    @Bean
    DirectExchange purchaseDLQExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean
    DirectExchange purchaseQExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue purchaseDLQ() {
        return QueueBuilder.durable(dlq).build();
    }

    @Bean
    Queue purchaseQ() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", dlqExchange)
                .withArgument("x-dead-letter-routing-key", dlqKey)
                .build();
    }

    @Bean
    Binding purchaseDLQBinding(Queue purchaseDLQ, DirectExchange purchaseDLQExchange) {
        return BindingBuilder.bind(purchaseDLQ)
                .to(purchaseDLQExchange).with(dlqKey);
    }

    @Bean
    Binding purchaseQBinding(Queue purchaseQ, DirectExchange purchaseQExchange) {
        return BindingBuilder.bind(purchaseQ)
                .to(purchaseQExchange).with(key);
    }

}