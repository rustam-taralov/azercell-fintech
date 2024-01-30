package az.azercellfintech.customer.ms.controller;

import az.azercellfintech.customer.ms.model.request.CreateCustomerRequest;
import az.azercellfintech.customer.ms.model.request.OtpVerifyRequest;
import az.azercellfintech.customer.ms.model.response.CustomerCredentialsResponse;
import az.azercellfintech.customer.ms.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customer")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class CustomerController {

    CustomerService customerService;

    @PostMapping("/create")
    @ResponseStatus(CREATED)
    public void createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        customerService.createCustomer(createCustomerRequest);
    }

    @PatchMapping("/verify")
    @ResponseStatus(CREATED)
    public void verifyCustomer(@RequestBody OtpVerifyRequest otpVerifyRequest) {
        customerService.verifyCustomer(otpVerifyRequest);
    }

    @GetMapping("/{number}")
    public CustomerCredentialsResponse getCustomerCredentials(@PathVariable String number) {
        return customerService.getCustomerCredentialsResponse(number);
    }
}
