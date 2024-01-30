package az.azercellfintech.payment.controller;

import az.azercellfintech.payment.model.request.RefundRequest;
import az.azercellfintech.payment.model.request.TopUpRequest;
import az.azercellfintech.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class TopUpController {

    TransactionService transactionService;

    @PostMapping("top-up")
    public void topUpTransaction(@RequestBody TopUpRequest topUpRequest, @RequestHeader String number) {
        transactionService.topUpTransaction(number, topUpRequest);
    }

    @PostMapping("refund")
    public void refundTransaction(@RequestBody RefundRequest request, @RequestHeader String number) {
        transactionService.refundTransaction(number, request);
    }
}
