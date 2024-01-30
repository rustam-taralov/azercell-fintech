package az.azercellfintech.customer.ms.dao.repository.service;

import az.azercellfintech.customer.ms.dao.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {

    Optional<ServiceEntity> findByServiceIdAndIsDeleteFalse(String serviceId);
}
