package micro.api.exceptions;

public class WrongPhoneNumberFormat extends RuntimeException {
    public WrongPhoneNumberFormat(String message) {
        super(message);
    }
}
