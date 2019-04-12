package Domain;


    public class MedicineValidationException extends RuntimeException {
        public MedicineValidationException(String message) {
            super("MedicineValidationException " + message);
        }
    }

