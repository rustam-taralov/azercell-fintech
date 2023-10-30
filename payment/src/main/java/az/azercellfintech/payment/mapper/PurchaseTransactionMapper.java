package az.azercellfintech.payment.mapper;

import az.azercellfintech.payment.dao.entity.PurchaseTransactionsEntity;
import az.azercellfintech.payment.model.dto.PurchaseTransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseTransactionMapper {

    PurchaseTransactionMapper TOP_UP_TRANSACTION_MAPPER = Mappers.getMapper(PurchaseTransactionMapper.class);

    PurchaseTransactionDto toPurchaseDto(PurchaseTransactionsEntity entity);
}
