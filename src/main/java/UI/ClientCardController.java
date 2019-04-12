package UI;

import Domain.CardClient;
import Service.CardClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ClientCardController {
    public Spinner spnId;
    public TextField txtCardFirstName;
    public TextField txtCardLastName;
    public TextField txtCardCNP;
    public Button btnCancel;
    public Button btnAdd;
    public TextField txtCardDayOfBirth;
    public TextField txtCardMonthOfBirth;
    public TextField txtCardYearOfBirth;
    public TextField txtCardDayRegistration;
    public TextField txtCardMonthRegistration;
    public TextField txtCardYearRegistration;
    public TextField txtClientCardLastName;
    public TextField txtClientCardFirstName;
    public Button btnUpdate;

    private CardClientService cardClientService;
    public void setServices (CardClientService cardClientService){
        this.cardClientService = cardClientService;
    }

   public void btnAddClick(ActionEvent actionEvent){
        try{
            CardClient c = upsertClick();
            cardClientService.insert(c.getId(),c.getLastName(), c.getFirstName(),c.getCNP(),
                    c.getDateOfBirth(), c.getDateOfRegistration());
            btnCancelClick (actionEvent);
        }catch (RuntimeException rex){
            Common.showValidationError(rex.getMessage());
        }
   }
   public void btnCancelClick (ActionEvent actionEvent){
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
   }

    public void btnUpdateClick(ActionEvent actionEvent) {
        try {
            CardClient c = upsertClick();
            cardClientService.update(c.getId(),c.getLastName(),c.getFirstName(),c.getCNP(),
                    c.getDateOfBirth(),c.getDateOfRegistration());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
    }
   private CardClient upsertClick(){
        try{
            String id = String.valueOf(spnId.getValue());
            String lastName = txtCardLastName.getText();
            String firstName = txtCardFirstName.getText();
            String CNP = txtCardCNP.getText();
            LocalDate dateOfBirth = LocalDate.of(Integer.parseInt(txtCardYearOfBirth.getText()),
                    Integer.parseInt(txtCardMonthOfBirth.getText()), Integer.parseInt((txtCardDayOfBirth.getText())));

            LocalDate dateOfRegistration = LocalDate.of(Integer.parseInt(txtCardYearRegistration.getText()),
                    Integer.parseInt(txtCardMonthRegistration.getText()), Integer.parseInt((txtCardDayRegistration.getText())));

            return new CardClient(id,lastName,firstName,CNP,dateOfBirth,dateOfRegistration);
        } catch (RuntimeException rex){
            Common.showValidationError(rex.getMessage());
        }
        return null;
   }


}
