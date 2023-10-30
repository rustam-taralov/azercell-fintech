package az.azercellfintech.payment.dao.entity;

import az.azercellfintech.payment.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchase_transactions")
@FieldDefaults(level = PRIVATE)
public class PurchaseTransactionsEntity extends BaseEntity {

    String number;
    String transactionId;
    String serviceId;
    Status status;
    String rejectReason;
}
