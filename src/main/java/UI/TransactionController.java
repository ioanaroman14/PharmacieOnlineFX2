package UI;

import Domain.Transaction;
import Service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class TransactionController {
    public Spinner spnId;
    public Spinner spnIdMedicine;
    public Spinner spnIdCard;
    public Button btnAdd;
    public Button btnCancel;
    public TextField nbOfItems;
    public TextField txtTransactionDay;
    public TextField txtTransactionMonth;
    public TextField txtTransactionYear;
    public TextField txtTransactionHour;
    public TextField txtTransactionMinutes;
    public Button btnUpdate;
    public Spinner spnIdMovie;

    private TransactionService transactionService;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();


    public void setServices(TransactionService transactionService){
        this.transactionService = transactionService;
    }


    public void btnAddClick (ActionEvent actionEvent){
        try {
            Transaction t = upsertClick();
            transactionService.add(t.getId(), t.getIdMedicine(),
                    t.getIdCardClient(), t.getNumberOfItems(), t.getDate(), t.getTime());

            btnCancelClick(actionEvent);
        } catch (RuntimeException rex){
            Common.showValidationError(rex.getMessage());
        }
    }


    public void btnUpdateClick (ActionEvent actionEvent){
        try {
            Transaction t = upsertClick();

            transactionService.update(t.getId(), t.getIdMedicine(), t.getIdCardClient(), t.getNumberOfItems(),
                    t.getDate(), t.getTime(), t.getBasePrice(), t.getDiscount());
            btnCancelClick(actionEvent);
        } catch (RuntimeException rex){
            Common.showValidationError(rex.getMessage());
        }
    }


    private Transaction upsertClick() {
        try {
            String id = String.valueOf(spnId.getValue());
            String idMedicine = String.valueOf(spnIdMedicine.getValue());
            String idCardClient = String.valueOf(spnIdCard.getValue());
            int numberOfItems = Integer.parseInt(nbOfItems.getText());
            LocalDate date = LocalDate.of(Integer.parseInt(txtTransactionYear.getText()),
                    Integer.parseInt(txtTransactionMonth.getText()), Integer.parseInt(txtTransactionDay.getText()));
            LocalTime time = LocalTime.of(Integer.parseInt(txtTransactionHour.getText()),
                    Integer.parseInt(txtTransactionMinutes.getText()));
            //Double basePrice = Double.parseDouble(txtBasePrice.getText());
            //Double discount = Double.parseDouble(txtDiscount.getText());
            transactionService.add (id,idMedicine, idCardClient,numberOfItems,date, time);
            transactions.clear();
            transactions.addAll(transactionService.getAll());
            //return  new Transaction(id,idMedicine, idCardClient,numberOfItems, date, time);

        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
        return null;
    }
    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

}
