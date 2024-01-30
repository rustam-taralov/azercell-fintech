package az.azercellfintech.payment.queue.consumer;

import az.azercellfintech.payment.model.dto.RejectTransactionDto;
import az.azercellfintech.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static az.azercellfintech.payment.util.MapperUtil.mapToObject;
import static lombok.AccessLevel.PRIVATE;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TransactionConsumer {

    TransactionService transactionService;

    @RabbitListener(queues = "${rabbit.consumer.top-up-rejection.queue}")
    public void consumeTopUpRejectionTransactions(String message) {
        transactionService.rejectionUpdate(mapToObject(message, RejectTransactionDto.class));
    }

    @RabbitListener(queues = "${rabbit.consumer.top-up-success.queue}")
    public void consumeTopUpSuccessTransactions(String message) {
        transactionService.successUpdate(mapToObject(message, RejectTransactionDto.class));
    }
}
