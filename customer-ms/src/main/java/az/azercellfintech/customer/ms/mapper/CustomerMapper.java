package az.azercellfintech.customer.ms.mapper;

import az.azercellfintech.customer.ms.dao.entity.CustomerEntity;
import az.azercellfintech.customer.ms.model.request.CreateCustomerRequest;
import az.azercellfintech.customer.ms.model.response.CustomerCredentialsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {

    CustomerMapper CUSTOMER_MAPPER = Mappers.getMapper(CustomerMapper.class);

    CustomerEntity toEntity(CreateCustomerRequest request);

    CustomerCredentialsResponse toResponse(CustomerEntity customer);
}
