package az.azercellfintech.payment.queue.consumer;

import az.azercellfintech.payment.model.dto.RejectTransactionDto;
import az.azercellfintech.payment.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static az.azercellfintech.payment.util.MapperUtil.mapToObject;
import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PurchaseTransactionConsumer {

    PurchaseService purchaseService;

    @RabbitListener(queues = "${rabbit.consumer.purchase-rejection.queue}")
    public void consumePurchaseRejectionTransactions(String message) {
        purchaseService.rejectionUpdate(mapToObject(message, RejectTransactionDto.class));
    }

    @RabbitListener(queues = "${rabbit.consumer.purchase-success.queue}")
    public void consumePurchaseSuccessTransactions(String message) {
        purchaseService.successUpdate(mapToObject(message, RejectTransactionDto.class));
    }
}
