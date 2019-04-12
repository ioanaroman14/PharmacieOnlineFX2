package Service;

public class CardClientServiceException extends RuntimeException {
        public  CardClientServiceException(String message){
            super("CardServiceException ||| " + message);
        }
    }

