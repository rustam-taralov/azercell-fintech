package az.azercellfintech.auth.ms.client;

import az.azercellfintech.auth.ms.config.ClientErrorDecoder;
import az.azercellfintech.auth.ms.model.request.CreateCustomerRequest;
import az.azercellfintech.auth.ms.model.response.CustomerCredentialsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value= "customer-ms", path = "/customer-ms/v1/customer", configuration = ClientErrorDecoder.class)
public interface CustomerClient {

    @PostMapping("/create")
    void createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest);

    @GetMapping("/{number}")
    CustomerCredentialsDto getCustomerCredentials(@PathVariable String number);
}
