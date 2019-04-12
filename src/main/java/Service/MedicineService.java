package Service;

import Domain.Medicine;
import Domain.Transaction;
import Repository.IRepository;

import java.util.*;

public class MedicineService {

    private IRepository<Medicine> medicineRepository;
    private IRepository<Transaction> transactionRepository;

    private Stack<UndoRedoOperation<Medicine>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<Medicine>> redoableeOperations = new Stack<>();

    public MedicineService(IRepository<Medicine> medicineRepository, IRepository<Transaction> transactionRepository) {
        this.medicineRepository = medicineRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * adds a medicine
     *
     * @param id
     * @param name
     * @param producer
     * @param price
     * @param recipe
     */
    public void insert(String id, String name, String producer, double price, boolean recipe) {
        Medicine medicine = new Medicine(id, name, producer, price, recipe);
        medicineRepository.insert(medicine);
        undoableOperations.add(new AddOperation<>(medicineRepository, medicine));
        redoableeOperations.clear();
    }

    /**
     * updates a medicine
     *
     * @param id       the medicine id to updates
     * @param name
     * @param producer
     * @param price
     * @param recipe
     */
    public void update(String id, String name, String producer, Double price, Boolean recipe) {
        Medicine actualMedicine = medicineRepository.getById(id);
        Medicine medicineUpdate = new Medicine(id, name, producer, price, recipe);
        medicineRepository.update(medicineUpdate);

        undoableOperations.add(new UpdateOperation(medicineRepository, medicineUpdate, actualMedicine));

        redoableeOperations.clear();
    }

    /**
     * removes a medicine
     *
     * @param id the id on the medicine to remove
     */
    public void remove(String id) {
        Medicine medicine = medicineRepository.getById(id);
        medicineRepository.remove(id);
        undoableOperations.add(new DeleteTransaction<>(medicineRepository, medicine));
        redoableeOperations.clear();
    }

    /**
     * list of all medicines
     *
     * @return a list with all medicines
     */
    public List<Medicine> getAll() {
        return medicineRepository.getAll();
    }


    /**
     * search a text to find
     *
     * @return a list with all medicines where the text was found
     */
    public List<Medicine> fullTextSearch(String text) {
        List<Medicine> found = new ArrayList<>();
        for (Medicine med : medicineRepository.getAll()) {
            if ((med.getId().contains(text) || med.getName().contains(text) || med.getProducer().contains(text) ||
                    Double.toString(med.getPrice()).contains(text) ||
                    Boolean.toString(med.isRecipe()).contains(text)) && !found.contains(med)) {
                found.add(med);
            }
        }
        return found;
    }


    public void undo() {
        if (!undoableOperations.empty()) {
            UndoRedoOperation<Medicine> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableeOperations.add(lastOperation);
        }
    }

    public void redo() {
        if (!redoableeOperations.empty()) {
            UndoRedoOperation<Medicine> lastOperation = redoableeOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }
    public List<MedicineByTransactionSolds> sortByTransactions(){
        Map<String,Integer> frequences = new HashMap<>();
        for (Transaction trans: transactionRepository.getAll()){
            String medicineId = trans.getIdMedicine();
            if (frequences.containsKey(medicineId)) {
                frequences.put(medicineId, frequences.get(medicineId) + 1);
            }else
                frequences.put(medicineId,1);
            }

        List<MedicineByTransactionSolds> orderedMedicines = new ArrayList<>();
            for(String medicineId : frequences.keySet()){
                Medicine medicine = medicineRepository.getById(medicineId);
                orderedMedicines.add(new MedicineByTransactionSolds(medicine,frequences.get(medicineId)));
    }
            orderedMedicines.sort((m1,m2) -> Integer.compare(m2.getTransactions(), m1.getTransactions()));
            return orderedMedicines;
}
}