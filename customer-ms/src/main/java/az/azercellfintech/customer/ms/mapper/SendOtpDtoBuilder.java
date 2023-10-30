package az.azercellfintech.customer.ms.mapper;

import az.azercellfintech.customer.ms.model.request.SendOtpRequest;

public enum SendOtpDtoBuilder {
    SEND_OTP_DTO_BUILDER;

    public SendOtpRequest buildSendOtpDto(String destination) {
        return new SendOtpRequest("customer-service",destination);
    }
}
