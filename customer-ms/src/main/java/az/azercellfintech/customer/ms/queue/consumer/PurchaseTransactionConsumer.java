package az.azercellfintech.customer.ms.queue.consumer;


import az.azercellfintech.customer.ms.model.dto.PurchaseTransactionDto;
import az.azercellfintech.customer.ms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static az.azercellfintech.customer.ms.util.MapperUtil.mapToObject;
import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PurchaseTransactionConsumer {

    CustomerService customerService;

    @RabbitListener(queues = "${rabbit.consumer.purchase.queue}")
    public void consumePurchaseTransactions(String message) {
        customerService.purchaseTransaction(mapToObject(message, PurchaseTransactionDto.class));
    }
}
