package az.azercellfintech.payment.mapper;

import az.azercellfintech.payment.dao.entity.TransactionsEntity;
import az.azercellfintech.payment.model.dto.RefundTransactionDto;
import az.azercellfintech.payment.model.dto.TopUpTransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper TOP_UP_TRANSACTION_MAPPER = Mappers.getMapper(TransactionMapper.class);

    TopUpTransactionDto toTopUpDto(TransactionsEntity entity);
    RefundTransactionDto toRefundDto(TransactionsEntity entity);
}
