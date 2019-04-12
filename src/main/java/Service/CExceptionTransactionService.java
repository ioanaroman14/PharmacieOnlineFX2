package Service;

public class CExceptionTransactionService extends RuntimeException{
        String str;
        public CExceptionTransactionService(String str) {
            this.str = str;
        }
        public String toString() {
            return ("message = " + str);
        }
    }


