package UI;

import Service.MedicineByTransactionSolds;
import Service.MedicineService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowMedicinesOrdoredByTransactionsController {
    public TableView tableViewMovies;
    public TableColumn tableColumnNameMedicine;
    public TableColumn tableColumnTransactionsNumber;
    public TableView tableViewMedicines;
    private MedicineService medicineService;


    private ObservableList<MedicineByTransactionSolds> medicines =
            FXCollections.observableArrayList();
    public void setService (MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @FXML
    private void initialize(){
        Platform.runLater(() -> {
            try {
                List<MedicineByTransactionSolds> ordoredMedicines = medicineService.sortByTransactions();
                medicines.addAll(ordoredMedicines);
                tableViewMedicines.setItems(medicines);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE,"Failed to create new Window", e);
            }
        });
    }
}
