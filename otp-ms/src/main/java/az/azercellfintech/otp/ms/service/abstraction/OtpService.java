package az.azercellfintech.otp.ms.service.abstraction;


import az.azercellfintech.otp.ms.model.request.CheckOtpRequest;
import az.azercellfintech.otp.ms.model.request.SendOtpRequest;
import az.azercellfintech.otp.ms.model.response.SendOtpResponse;

public interface OtpService {

    SendOtpResponse sendOtp(SendOtpRequest sendOtpRequest);

    void checkOtp(CheckOtpRequest checkOtpRequest);
}
