package Domain;

public class TransactionValidator implements IValidator<Transaction> {
    /**
     * validates a transaction
     * @param transaction to validate
     * @throws TransactionValidationException if there are validation errors
     */

    public void validate (Transaction transaction) {
        String errors = "";

        if (transaction.getNumberOfItems() <= 0) {
            throw new TransactionValidationException("The number of items must be at least 1!");
        }
        if (!errors.isEmpty()){
            throw new TransactionValidationException("\n" + errors);
        }
    }
}
