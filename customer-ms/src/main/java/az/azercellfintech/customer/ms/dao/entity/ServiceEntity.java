package az.azercellfintech.customer.ms.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service")
@FieldDefaults(level = PRIVATE)
public class ServiceEntity extends BaseEntity {

    String name;
    BigDecimal price;
    String serviceId;
}


