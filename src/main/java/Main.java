
import Domain.*;
import Repository.IRepository;
import Repository.JsonFileRepository;
import Service.CardClientService;
import Service.MedicineService;
import Service.TransactionService;
import UI.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import UI.Console;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("controller.fxml"));
        Parent root = fxmlLoader.load();

        MainController mainController = fxmlLoader.getController();

        IValidator<Medicine> medicineValidator = new MedicineValidator();
        IValidator<CardClient> cardClientValidator = new CardClientValidator();
        IValidator<Transaction> transactionValidator = new TransactionValidator();

        IRepository<Medicine> medicinesRepository = new JsonFileRepository<>
                (medicineValidator, "Medicines.json", Medicine.class);
        IRepository<CardClient> cardsRepository = new JsonFileRepository<>
                (cardClientValidator, "CardClient.json", CardClient.class);
        IRepository<Transaction> transactionsRepository = new JsonFileRepository<>
                (transactionValidator, "Transactions.json", Transaction.class);

        MedicineService medicineService = new MedicineService(medicinesRepository, transactionsRepository);
        CardClientService cardClientService = new CardClientService(cardsRepository);
        TransactionService transactionService = new TransactionService(transactionsRepository, medicinesRepository, cardsRepository);

        mainController.setServices(medicineService, cardClientService, transactionService);

        primaryStage.setTitle("Online Pharmacy");
        primaryStage.setScene(new Scene(root, 1200, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

