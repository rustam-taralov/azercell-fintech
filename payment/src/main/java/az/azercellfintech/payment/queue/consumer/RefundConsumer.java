package az.azercellfintech.payment.queue.consumer;

import static az.azercellfintech.payment.util.MapperUtil.mapToObject;
import static lombok.AccessLevel.PRIVATE;

import az.azercellfintech.payment.model.dto.RejectTransactionDto;
import az.azercellfintech.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RefundConsumer {

    TransactionService transactionService;

    @RabbitListener(queues = "${rabbit.consumer.refund-rejection.queue}")
    public void consumeTopUpRejectionTransactions(String message) {
        transactionService.rejectionUpdate(mapToObject(message, RejectTransactionDto.class));
    }

    @RabbitListener(queues = "${rabbit.consumer.refund-success.queue}")
    public void consumeTopUpSuccessTransactions(String message) {
        transactionService.rejectionUpdate(mapToObject(message, RejectTransactionDto.class));
    }
}
