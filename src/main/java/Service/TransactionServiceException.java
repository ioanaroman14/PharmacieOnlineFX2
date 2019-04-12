package Service;

public class TransactionServiceException extends RuntimeException {

        public TransactionServiceException(String message){
            super("BookingServiceException ||| " + message);
        }
}
