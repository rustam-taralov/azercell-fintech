package az.azercellfintech.customer.ms.dao.repository;

import az.azercellfintech.customer.ms.dao.entity.CustomerEntity;
import az.azercellfintech.customer.ms.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByNumber(String number);
    List<CustomerEntity> findByNumberAndCreatedAtAfter(String number, LocalDateTime afterDateTime);

    Optional<CustomerEntity> findFirstByNumberAndCreatedAtAfter(String number, LocalDateTime afterDateTime);

    Optional<CustomerEntity> findByNumberAndStatusAndIsDeleteFalse(String number, Status status);
}
