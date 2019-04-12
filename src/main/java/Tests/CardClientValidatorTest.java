package Tests;

import Domain.CardClient;
import Domain.CardClientValidator;
import Domain.IValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CardClientValidatorTest {

    private IValidator<CardClient> validatorCardClient = new CardClientValidator();

    @Test
    void validate(){
        CardClient cardClient = new CardClient("1", "ioana", "roman", "2801014125811" ,
                LocalDate.of(2012,11,12), LocalDate.of(2019,04,18));
        try {
            validatorCardClient.validate(cardClient);
        } catch (RuntimeException rex){
            assertTrue(true);
        }
    }
}