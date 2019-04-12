package Domain;

public class TransactionValidationException extends RuntimeException {
    public TransactionValidationException(String message) {
        super("TransactionValidationException !!! " + message);
    }
}


