package Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CardClientValidator implements IValidator<CardClient> {
    /**
     * validates a cardClient
     *
     * @param cardClient to validate
     *                   throws CardClientValidationException if there are validation errors
     */
    public void validate(CardClient cardClient) {
        String errors = "";
        if (cardClient.getCNP().length() != 13) {
            errors += "The CNP must have 13 characters! \n";
        }

        if (cardClient.getDateOfBirth().getYear() < 1900 || cardClient.getDateOfBirth().getYear() >
                Calendar.getInstance().get(Calendar.YEAR)) {

            errors += "The yar of birth must be less than " + Calendar.getInstance().get(Calendar.YEAR) +
                    "and greater than 1900 \n";
        }
        if (!errors.isEmpty()){
            throw new CardClientValidationException("\n" + errors);
        }
    }
}


