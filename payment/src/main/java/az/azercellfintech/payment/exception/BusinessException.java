package az.azercellfintech.payment.exception;

public class BusinessException extends RuntimeException {
    String code;
    public BusinessException(ExceptionMessages exceptionMessages) {
        super(exceptionMessages.getMessage());
        this.code = exceptionMessages.name();
    }
}
