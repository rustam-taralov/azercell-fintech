package az.azercellfintech.payment.dao.repository;

import az.azercellfintech.payment.dao.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopUpTransactionsRepository extends JpaRepository<TransactionsEntity, Long> {

    Optional<TransactionsEntity> findByTransactionId(String transactionId);
}
