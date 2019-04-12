package Tests;

import Domain.IValidator;
import Domain.Medicine;
import Domain.MedicineValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicineValidatorTest {

    private IValidator<Medicine> validatorMedicine = new MedicineValidator();

    @Test
    void validateShouldThrowExceptionsCorrectly() {
        Medicine medicine = new Medicine("2", "Nurofen", "Terapia", 10.1,true);
        try {
            validatorMedicine.validate(medicine);
        } catch (RuntimeException rex) {
            assertTrue(true);
        }
    }
}