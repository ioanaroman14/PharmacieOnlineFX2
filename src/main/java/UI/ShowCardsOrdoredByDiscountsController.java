package UI;

import Domain.CardClient;
import Domain.Transaction;
import Service.CardClientService;
import Service.TransactionService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.AbstractList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowCardsOrdoredByDiscountsController {
    public TableView tableViewOrderedCards;
    public TableColumn tableColumnIdCard;
    public TableColumn tableColumnLastNameCard;
    public TableColumn tableColumnFirstNameCard;
    public TableColumn tableColumnCNPCard;
    public TableColumn tableColumnDateOfBirthCard;
    public TableColumn tableColumnDateOfRegistrationCard;
    private CardClientService cardClientService;
    private TransactionService transactionService;
    private TransactionService transactions;

    private ObservableList<CardClient> cardClients = FXCollections.observableArrayList();




    public void setService (CardClientService cardClientService){
        this.cardClientService = cardClientService;
    }


/**
    @FXML
    private void intialize(){
        Platform.runLater(() -> {
            try {
                List<CardClient> cardsOrdered =

                List<Transaction> get = transactionService.

                tableViewOrderedCards.setItems(cardClients);
            } catch (RuntimeException e) {
                Logger logger = Logger.getLogger(getClass().getName());
                logger.log(Level.SEVERE, "Failed to create new Window", e);

                }

        });
    }
    **/

}
