package Service;

import Domain.CardClient;
import Domain.Medicine;
import Domain.Transaction;
import Repository.IRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class TransactionService {
    private IRepository<Transaction> transactionRepository;
    private IRepository<Medicine> medicineRepository;
    private IRepository<CardClient> cardClientRepository;

    private Stack<UndoRedoOperation<Transaction>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<Transaction>> redoableeOperations = new Stack<>();

    public TransactionService(IRepository<Transaction> transactionRepository, IRepository<Medicine> medicineRepository, IRepository
            <CardClient> cardClientRepository) {
        this.transactionRepository = transactionRepository;
        this.medicineRepository = medicineRepository;
        this.cardClientRepository = cardClientRepository;
    }

    /**
     * adds a transactions
     *
     * @param id
     * @param idMedicine
     * @param idClientCard
     * @param numberOfItems
     * @param date
     * @param time
     * @return
     */

    public void add (String id, String idMedicine, String idClientCard, int numberOfItems,
                              LocalDate date, LocalTime time) {

        Medicine medicineSold = medicineRepository.getById(idMedicine);
        if (medicineSold == null) {
            throw new TransactionServiceException("There is no medicine with the given id!");
        }

        double basePrice = medicineSold.getPrice();
        double discount = 0;
        if (idClientCard != null && !medicineSold.isRecipe()) {
            discount = 0.1;
        }
        if (idClientCard != null && medicineSold.isRecipe()) {
            discount = 0.15;
        }

        Transaction transaction = new Transaction(id, idMedicine, idClientCard, numberOfItems, date,
                time,basePrice, discount);
        transactionRepository.insert(transaction);
        Medicine medicine = medicineRepository.getById(idMedicine);
        medicineRepository.update(medicine);

        CardClient card = cardClientRepository.getById(idClientCard);

        undoableOperations.add(new AddOperation<>(transactionRepository, transaction));
        redoableeOperations.clear();

    }
/**
    public Transaction add(String id, String idMedicine, String idClientCard, int numberOfItems,
                           LocalDate date, LocalTime time) {
    Transaction existing = transactionRepository.getById(id);
    if (existing != null) {
    idMedicine = existing.getIdMedicine();

    if (idClientCard.isEmpty()) {
    idClientCard = existing.getIdCardClient();
    }
    if (numberOfItems == 0) {
    numberOfItems = existing.getNumberOfItems();
    }

    }
    Medicine medicineSold = medicineRepository.getById(idMedicine);
    if (medicineSold == null) {
    throw new CExceptionTransactionService("There is no medicine with the given id!");
    }
    double basePrice = medicineSold.getPrice();
    double discount = 0;
    if (idClientCard != null && !medicineSold.isRecipe()) {
    discount = 0.1;
    }
    if (idClientCard != null && medicineSold.isRecipe()) {
    discount = 0.15;
    }
    Transaction transaction = new Transaction(id, idMedicine, idClientCard, numberOfItems, date, time, basePrice, discount);
    transactionRepository.insert(transaction);
    return transaction;
    }
**/
        /**
         *update a transaction
         *
         * @param id
         * @param idMedicine
         * @param idClientCard
         * @param numberOfItems
         * @param date
         * @param time
         * @param basePrice
         * @param discount
         */

    public void update (String id, String idMedicine, String idClientCard, int numberOfItems,
                        LocalDate date, LocalTime time, Double basePrice, Double discount){
        Transaction actualTransaction = transactionRepository.getById(id);
        Transaction updateTransaction = new Transaction(id, idMedicine, idClientCard,numberOfItems, date, time, basePrice, discount);
        transactionRepository.update(updateTransaction);
        undoableOperations.add(new UpdateOperation(medicineRepository, updateTransaction,actualTransaction));

        redoableeOperations.clear();
    }

    /**
     * removes a transaction by id
     *
     * @param id the id of the transaction we want to remove
     */
    public  void remove(String id){
        Transaction transaction = transactionRepository.getById(id);
        transactionRepository.remove(id);

        CardClient card = cardClientRepository.getById(transaction.getIdCardClient());
        Medicine medicine = medicineRepository.getById(transaction.getIdMedicine());

        cardClientRepository.update(card);
        undoableOperations.add(new DeleteTransaction<>(transactionRepository, transaction));
        redoableeOperations.clear();
    }

    public void undo() {
        if (!undoableOperations.empty()) {
            UndoRedoOperation<Transaction> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableeOperations.add(lastOperation);
        }
    }
    public void redo() {
        if (!redoableeOperations.empty()) {
            UndoRedoOperation<Transaction> lastOperation = redoableeOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }

    /**
     * list of all transactions
     * @return an ArrayList list wuth all bookings
     */
    public List<Transaction> getAll(){
        return transactionRepository.getAll();
    }

/**
 * searches a text in all transactions
 * @param text the text to find
 * @return a list with all transactions
 */
    public List<Transaction> fullTextSearch(String text){
        List<Transaction> found = new ArrayList<>();
        for (Transaction t : transactionRepository.getAll()){
            if((t.getId().contains(text) || t.getIdMedicine().contains(text) ||
                    t.getIdCardClient().contains(text) || Integer.toString(t.getNumberOfItems()).contains(text) ||
                    t.getDate().toString().contains(text) || t.getTime().toString().contains(text)) && ! found.contains(t)){
                found.add(t);
            }
        }
        return found;
    }

    /**
     * searches all transactions in a specified date period
     * @param begin the begining date
     * @param end the ending date
     * @return a lis with all transactions
     */
    public List<Transaction> transactionsByDays (LocalDate begin, LocalDate end){
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction t : transactionRepository.getAll()){
            if (t.getDate().isAfter(begin) && t.getDate().isBefore(end)){
                transactions.add(t);
            }
        }
        return transactions;
    }

    public List<Transaction> sortedCardsByDiscounts(){
        List<Transaction> cardsOrdered = transactionRepository.getAll();

        cardsOrdered.sort(Comparator.comparing(Transaction :: getDiscount).reversed());
        return cardsOrdered;
    }
    /**
     * removes transactions between 2 dates
     *
     * @param begin the beginind date
     * @param end the ending date
     */

    public void removeTransactionsByDays(LocalDate begin, LocalDate end){
        List<Transaction> deletedPeriod = new ArrayList<>();

        for(Transaction t : transactionRepository.getAll()){
            if(t.getDate().isAfter(begin) && t.getDate().isBefore(end)){
                remove(t.getId());
                deletedPeriod.add(t);
            }
        }
        undoableOperations.add(new DeleteAllTransactions<>(transactionRepository, deletedPeriod));
        redoableeOperations.clear();
    }
}

