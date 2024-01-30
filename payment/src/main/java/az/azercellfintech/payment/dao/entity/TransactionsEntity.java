package az.azercellfintech.payment.dao.entity;

import az.azercellfintech.payment.model.enums.Status;
import az.azercellfintech.payment.model.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;

import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topup_transactions")
@FieldDefaults(level = PRIVATE)
public class TransactionsEntity extends BaseEntity{

    String number;
    BigDecimal amount;
    String transactionId;
    @Enumerated(STRING)
    Status status;
    @Enumerated(STRING)
    TransactionType transactionType;
    String rejectReason;
}
