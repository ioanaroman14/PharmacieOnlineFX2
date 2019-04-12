package UI;


import Domain.Medicine;
import Service.CardClientService;
import Service.MedicineService;
import Service.TransactionService;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;

public class MedicineController {

    public TextField txtMedicineName;
    public TextField txtMedicineProducer;
    public TextField txtMedicinePrice;
    public Button btnCancel;
    public Spinner spnId;
    public CheckBox chkRecipe;
    public Button btnAdd;
    public Button btnUpdate;


    private MedicineService medicineService;



    public void setService(MedicineService medicineService) {
        this.medicineService = medicineService;

    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage)btnCancel.getScene().getWindow();
        stage.close();
    }

    public void btnAddClick(ActionEvent actionEvent) {
        try {
            Medicine medicine = upsertClick();
            medicineService.insert(medicine.getId(), medicine.getName(),
                    medicine.getProducer(), medicine.getPrice(), medicine.isRecipe());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }
        public void btnUpdateClick (ActionEvent actionEvent) {
            try {
                Medicine medicine = upsertClick();
                medicineService.update(medicine.getId(), medicine.getName(),
                        medicine.getProducer(), medicine.getPrice(), medicine.isRecipe());
                btnCancelClick(actionEvent);
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
            private Medicine upsertClick() {
                try {

                    String id = String.valueOf(spnId.getValue());
                    String name = txtMedicineName.getText();
                    String producer = txtMedicineProducer.getText();
                    double price = Double.parseDouble(txtMedicinePrice.getText());
                    boolean recipe = chkRecipe.isSelected();

                    return new Medicine (id, name, producer, price, recipe);

                } catch (RuntimeException rex) {
                    Common.showValidationError(rex.getMessage());
                }
                return null;
            }
        }






