package UI;

import Domain.CardClient;
import Domain.Medicine;
import Domain.Transaction;
import Service.CardClientService;
import Service.MedicineService;
import Service.TransactionService;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchResultsController {
    public Label result;
    public TextField textToSearch;
    public Button btnSearch;
    public Button btnCancel;

    private MedicineService medicineService;
    private CardClientService cardClientService;
    private TransactionService transactionService;

    public void setService (MedicineService medicineService,
                            CardClientService cardClientService, TransactionService transactionService){
        this.medicineService = medicineService;
        this.cardClientService = cardClientService;
        this.transactionService = transactionService;
    }

    public void btnSearchClick(ActionEvent actionEvent) {
        String txt = textToSearch.getText();
        String found = txt + " found" + medicineSearch(txt) + "\n" + clientCardSearch(txt) + "\n" + transactionSearch(txt);

        if(txt.length()>=1)
            result.setText(found);
    }
    private String transactionSearch (String text){
        String transactionTextFound = "";
        for (Transaction t : transactionService.fullTextSearch(text)){
            transactionTextFound += t + "\n";

        }
        return transactionTextFound;
    }
    private String clientCardSearch (String text){
        String clientCardTextFound = "";
        for (CardClient c : cardClientService.fullTextSearch(text)){
            clientCardTextFound += c + "\n";

        }
        return clientCardTextFound;
    }

    private String medicineSearch (String text){
        String medicineTextFound = "";
        for (Medicine m : medicineService.fullTextSearch(text)){
            medicineTextFound += m + "\n";

        }
        return medicineTextFound;
    }
    public void btnCancelClick(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
