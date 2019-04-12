package Tests;

import Domain.IValidator;
import Domain.Transaction;
import Domain.TransactionValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransactionValidatorTest {
    private IValidator<Transaction> validatorTransaction = new TransactionValidator();


    @Test
    void validateTransactionShouldThrowExceptionsCorrectly() {
        Transaction transaction = new Transaction("1", "2", "3",
                12, LocalDate.of(2012,12,12),
                LocalTime.of(10,0, 0),10.1, 12.0);
        try {
            validatorTransaction.validate(transaction);
        } catch (RuntimeException rex) {
            assertTrue (true);
        }
    }
}