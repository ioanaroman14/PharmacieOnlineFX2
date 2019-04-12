package UI;

import Domain.Transaction;
import Service.TransactionService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowTransactionsPeriodController {
    public TableView tableViewTransactions;
    public TableColumn tableColumnIdTransaction;
    public TableColumn tableColumnIdMedicineTransaction;
    public TableColumn tableColumnIdCardTransaction;
    public TableColumn tableColumnDateOfTransaction;
    public TableColumn tableColumnTimeOfTransaction;
    public TextField startYear;
    public TextField startMonth;
    public TextField endYear;
    public TextField endMonth;
    public Button transactionSearch;
    public Button btnCancel;
    public TextField startDays;
    public TextField endDays;
    private TransactionService transactionService;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public void setService (TransactionService transactionService){
        this.transactionService = transactionService;
    }


    public void btnSearchClick(ActionEvent actionEvent) {
        try {
            transactions.clear();
            LocalDate begin = LocalDate.of(Integer.parseInt(startYear.getText()), Integer.parseInt(startMonth.getText()), Integer
                    .parseInt(startDays.getText()));
            LocalDate end = LocalDate.of(Integer.parseInt(endYear.getText()), Integer.parseInt(endMonth.getText()), Integer
                    .parseInt(endDays.getText()));
            transactions.addAll(transactionService.transactionsByDays(begin, end));
            tableViewTransactions.setItems(transactions);
        } catch (RuntimeException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create Window", e);
            Common.showValidationError(e.getMessage());
        }

    }

    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage =(Stage) btnCancel.getScene().getWindow();
        stage.close();
    }


}
