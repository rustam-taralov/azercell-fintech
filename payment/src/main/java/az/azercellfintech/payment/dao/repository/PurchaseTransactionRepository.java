package az.azercellfintech.payment.dao.repository;

import az.azercellfintech.payment.dao.entity.PurchaseTransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransactionsEntity, Long> {

    Optional<PurchaseTransactionsEntity> findByTransactionId(String transactionId);
}
