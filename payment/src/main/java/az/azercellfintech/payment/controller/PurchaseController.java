package az.azercellfintech.payment.controller;

import az.azercellfintech.payment.model.request.PurchaseRequest;
import az.azercellfintech.payment.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/purchase")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PurchaseController {

    PurchaseService purchaseService;

    @PostMapping
    public void purchaseTransaction(@RequestBody PurchaseRequest purchaseRequest, @RequestHeader String number) {
        purchaseService.purchaseTransaction(purchaseRequest, number);
    }
}
