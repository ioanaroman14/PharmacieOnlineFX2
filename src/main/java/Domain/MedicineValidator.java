package Domain;

import UI.CustomException;

public class MedicineValidator implements IValidator<Medicine> {
    /**
     * validate a medicine
     * @param medicine to validate
     * throws MedicineValidationException if there are validation errors
     */

    public void validate (Medicine medicine){
        String errors = "";
        if(medicine.getPrice() > 0){
            errors += "The price must be grater than 0!\n";
        }
        if (errors.isEmpty()){
            throw new MedicineValidationException(errors);
        }
    }
}
