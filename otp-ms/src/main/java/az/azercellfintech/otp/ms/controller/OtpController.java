package az.azercellfintech.otp.ms.controller;

import az.azercellfintech.otp.ms.model.request.CheckOtpRequest;
import az.azercellfintech.otp.ms.model.request.SendOtpRequest;
import az.azercellfintech.otp.ms.model.response.SendOtpResponse;
import az.azercellfintech.otp.ms.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping(path = "/v1/otp")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OtpController {
    OtpService otpService;

    @PostMapping("send")
    public SendOtpResponse sendOtp(@RequestBody @Valid SendOtpRequest sendOtpRequest) {
        return otpService.sendOtp(sendOtpRequest);
    }

    @PostMapping("check")
    @ResponseStatus(NO_CONTENT)
    public void checkOtp(@RequestBody @Valid CheckOtpRequest checkOtpRequest) {
        otpService.checkOtp(checkOtpRequest);
    }
}
