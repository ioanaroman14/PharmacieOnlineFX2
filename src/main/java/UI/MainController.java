package UI;

import Domain.CardClient;
import Domain.Medicine;
import Domain.Transaction;
import Service.CardClientService;
import Service.MedicineService;
import Service.TransactionService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MainController {
    public TableView tblMedicines;
    public TableColumn colIdMedine;
    public TableColumn colNameMedicine;
    public TableColumn colProducerMedicine;
    public TableColumn colPriceMedicine;
    public Button btnAddMedicine;
    public TableColumn colRecipe;
    public TableView tblCardClients;
    public TableColumn colIdCardClient;
    public TableColumn colLastNameClient;
    public TableColumn colFirstNameClient;
    public TableColumn colCNPCardClient;
    public TableColumn colDateOFBirth;
    public TableColumn colDateOfRegistration;
    public TableView tblTransaction;
    public TableColumn colTransactionId;
    public TableColumn colTransIdMedicine;
    public TableColumn colIdCardClientTrans;
    public TableColumn colDateTransaction;
    public TableColumn colTimeTransaction;
    public TableColumn colNumberOfItems;
    public TableColumn colbasePrice;
    public TableColumn colDiscount;
    public Button btnRemoveMedicine;
    public Button btnCancel;
    public Button btnMedicineUndo;
    public Button btnMedicineRedo;
    public Button btnClientUndo;
    public Button btnClientRedo;
    public Button btnTransactionUndo;
    public Button btnTransactionRedo;
    public Button btnUpdateMedicine;


    private MedicineService medicineService;
    private CardClientService cardClientService;
    private TransactionService transactionService;

    private ObservableList<Medicine> medicines = FXCollections.observableArrayList();
    private ObservableList<CardClient> clients = FXCollections.observableArrayList();
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();


    public void setServices(MedicineService medicineService, CardClientService cardClientService, TransactionService transactionService) {
        this.medicineService = medicineService;
        this.cardClientService = cardClientService;
        this.transactionService = transactionService;
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            //colProducerMedicine.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
            medicines.addAll(medicineService.getAll());
            tblMedicines.setItems(medicines);
            clients.addAll(cardClientService.getAll());
            tblCardClients.setItems(clients);
            transactions.addAll(transactionService.getAll());
            tblTransaction.setItems(transactions);

        });
    }

    public void btnAddMedicineClick(ActionEvent actionEvent) {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/medicineAdd.fxml"));
            upsertMedicine(fxmlLoader, "Medicine add");

    }
    public void btnUpdateMedicineClick (ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/medicineUpdate.fxml"));
        upsertMedicine(fxmlLoader, "Medicine update");
    }
    public void upsertMedicine (FXMLLoader fxmlLoader, String title){
        try{
            Scene scene = new Scene(fxmlLoader.load(),600, 200);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            MedicineController controller = fxmlLoader.getController();
            controller.setService (medicineService);
            stage.showAndWait();
            medicines.clear();
            medicines.addAll(medicineService.getAll());
        }catch (IOException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Errors to create new window.", e);
        }
    }
    public void btnRemoveMedicineClick(ActionEvent actionEvent) {
        Medicine selected = (Medicine)
                tblMedicines.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                medicineService.remove(selected.getId());
                medicines.clear();
                medicines.addAll(medicineService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }



    public void btnAddClientClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/clientCardAdd.fxml"));
        upsertClientCard (fxmlLoader, "Client card add");
    }

    public void btnUpdateClientClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/clientCardUpdate.fxml"));
        upsertClientCard (fxmlLoader, "Client card update");
    }

    public void upsertClientCard (FXMLLoader fxmlLoader, String title) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 200);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ClientCardController controller = fxmlLoader.getController();
            controller.setServices(cardClientService);
            stage.showAndWait();
            clients.clear();
            clients.addAll(cardClientService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Card Clients add.", e);
        }
    }

    public void btnRemoveCardClientClick(ActionEvent actionEvent) {
        CardClient selected = (CardClient)
                tblCardClients.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                cardClientService.remove(selected.getId());
                clients.clear();
                clients.addAll(cardClientService.getAll());
            } catch (RuntimeException rex) {
                Common.showValidationError(rex.getMessage());
            }
        }
    }

    public void btnAddTransactionClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/transactionAdd.fxml"));
        upsertTransaction (fxmlLoader,"Transaction Add");
    }

    public void btnUpdateTransactionClick(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/transactionUpdate.fxml"));
        upsertTransaction (fxmlLoader,"Transaction Update");
    }
    public void upsertTransaction (FXMLLoader fxmlLoader, String title) {
        try {
            Scene scene = new Scene(fxmlLoader.load(), 600, 300);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            TransactionController controller = fxmlLoader.getController();
            controller.setServices(transactionService);
            stage.showAndWait();
            transactions.clear();
            transactions.addAll(transactionService.getAll());

            clients.clear();
            clients.addAll(cardClientService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window: Transaction.", e);
        }
    }

    public void btnRemoveTransactionClick(ActionEvent actionEvent) {
        Transaction selected = (Transaction)
                tblTransaction.getSelectionModel().getSelectedItem();
        if(selected != null){
            try {
                transactionService.remove(selected.getId());
                transactions.clear();
                transactions.addAll(transactionService.getAll());

                clients.clear();
                clients.addAll(cardClientService.getAll());
            } catch (RuntimeException rex){
                Common.showValidationError(rex.getMessage());
            }
        }
    }

    public void searchClick (ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/searchResultsController.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Text Search");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            SearchResultsController controller = fxmlLoader.getController();
            controller.setService (medicineService, cardClientService, transactionService);
            stage.showAndWait();
        } catch (IOException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window", e);
        }
    }

    public void medicinesByTransaction (ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/showMedicinesOrdoredByTransactions.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Medicines ordonored by transactions");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ShowMedicinesOrdoredByTransactionsController controller = fxmlLoader.getController();
            controller.setService (medicineService);
            stage.showAndWait();
        } catch (IOException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window", e);
        }
    }
    public void sortedCardsByDiscounts (ActionEvent actionEvent){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/showCardsOrdoredByDiscounts.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Client cards ordored by disocunts");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ShowCardsOrdoredByDiscountsController controller = fxmlLoader.getController();
            controller.setService (cardClientService);
            stage.showAndWait();
        } catch (IOException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window", e);
        }
    }

    public void removeTransactionPeriod (ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/removeTransactionPeriodController.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
            Stage stage = new Stage();
            stage.setTitle("Remove transaction period");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            RemoveTransactionPeriodController controller = fxmlLoader.getController();
            controller.setService(transactionService);
            stage.showAndWait();
            transactions.clear();
            transactions.addAll(transactionService.getAll());
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new window", e);
        }
    }
    public void btnMedicineUndoClick(ActionEvent actionEvent) {
        medicineService.undo();
        medicines.clear();
        medicines.addAll(medicineService.getAll());
    }

    public void btnMedicineRedoClick(ActionEvent actionEvent) {
        medicineService.redo();
        medicines.clear();
        medicines.addAll(medicineService.getAll());
    }

    public void btnClientUndoClick(ActionEvent actionEvent) {
        cardClientService.undo();
        clients.clear();
        clients.addAll(cardClientService.getAll());
    }

    public void btnClientRedoClick(ActionEvent actionEvent) {
        cardClientService.redo();
        clients.clear();
        clients.addAll(cardClientService.getAll());
    }
    public void btnTransactionUndoClick(ActionEvent actionEvent) {
        transactionService.undo();
        transactions.clear();
        transactions.addAll(transactionService.getAll());
    }


    public void btnTransactionRedoClick(ActionEvent actionEvent) {
        transactionService.redo();
        transactions.clear();
        transactions.addAll(transactionService.getAll());
    }

    public void increasingByAnProcentage(ActionEvent actionEvent) {
    }
    public void editMedicineName(TableColumn.CellEditEvent cellEditEvent){
        Medicine editedMedicine = (Medicine) cellEditEvent.getRowValue();
        try  {
            String newName = (String)cellEditEvent.getNewValue();
            medicineService.insert(editedMedicine.getId(), newName, editedMedicine.getProducer(), editedMedicine.getPrice(), editedMedicine.isRecipe());
            editedMedicine.setName(newName);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
        tblMedicines.refresh();
    }
    public void editMedicineProducer(TableColumn.CellEditEvent cellEditEvent) {
        Medicine editedMedicine = (Medicine) cellEditEvent.getRowValue();
        try {
            String newProducers = (String)cellEditEvent.getNewValue();
            medicineService.insert(editedMedicine.getId(), editedMedicine.getName(), newProducers, editedMedicine.getPrice(), editedMedicine.isRecipe());
            editedMedicine.setProducer(newProducers);
        } catch (RuntimeException rex) {
            Common.showValidationError(rex.getMessage());
        }
        tblMedicines.refresh();
    }

    public void transactionsByDays(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/showTransactionsPeriod.fxml"));

            Scene scene = new Scene(fxmlLoader.load(), 700, 600);
            Stage stage = new Stage();
            stage.setTitle("Transaction in a period");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            ShowTransactionsPeriodController controller = fxmlLoader.getController();
            controller.setService(transactionService);
            stage.showAndWait();
        } catch (IOException e){
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Failed to create new Window" , e);
        }
    }
}




