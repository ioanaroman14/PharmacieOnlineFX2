package Domain;

public class CardClientValidationException extends RuntimeException {
    public CardClientValidationException(String message){
        super ("CardClientValidationException !!!" + message);
    }
}

