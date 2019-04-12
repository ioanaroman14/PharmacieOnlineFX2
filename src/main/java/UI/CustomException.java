package UI;

public class CustomException extends Exception {
    String str;
    public CustomException (String str){
        this.str = str;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String toString(){
        return ("message = " +str);
    }
   // public CustomException1 (String message){
     //   super(message);
    //
    // }

    public CustomException() {
    }
}
