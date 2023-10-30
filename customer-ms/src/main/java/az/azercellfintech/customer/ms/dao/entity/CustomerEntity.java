package az.azercellfintech.customer.ms.dao.entity;

import az.azercellfintech.customer.ms.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
@FieldDefaults(level = PRIVATE)
public class CustomerEntity extends BaseEntity {

    String name;
    String surname;
    LocalDate birthDate;
    @Column(name = "gsm_number")
    String number;
    String email;
    String password;
    BigDecimal balance;
    String otpIdentifier;
    @Enumerated(EnumType.STRING)
    Status status;
    @OneToMany
    @JoinTable(
            name= "customer_subscriptions",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    List<ServiceEntity> subscriptions;

}
