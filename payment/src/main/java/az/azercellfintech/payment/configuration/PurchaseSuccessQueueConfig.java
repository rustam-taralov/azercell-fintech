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
@ConfigurationProperties(prefix = "rabbit.consumer.purchase-success")
public class PurchaseSuccessQueueConfig {
    String queue;
    String key;
    String exchange;
    String dlq;
    String dlqKey;
    String dlqExchange;

    @Bean
    DirectExchange purchaseSuccessDLQExchange() {
        return new DirectExchange(dlqExchange);
    }

    @Bean
    DirectExchange purchaseSuccessQExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue purchaseSuccessDLQ() {
        return QueueBuilder.durable(dlq).build();
    }

    @Bean
    Queue purchaseSuccessQ() {
        return QueueBuilder.durable(queue)
                .withArgument("x-dead-letter-exchange", dlqExchange)
                .withArgument("x-dead-letter-routing-key", dlqKey)
                .build();
    }

    @Bean
    Binding purchaseSuccessDLQBinding(Queue purchaseDLQ, DirectExchange purchaseDLQExchange) {
        return BindingBuilder.bind(purchaseDLQ)
                .to(purchaseDLQExchange).with(dlqKey);
    }

    @Bean
    Binding purchaseSuccessQBinding(Queue purchaseQ, DirectExchange purchaseQExchange) {
        return BindingBuilder.bind(purchaseQ)
                .to(purchaseQExchange).with(key);
    }

}