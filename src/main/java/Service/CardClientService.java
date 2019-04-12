package Service;

import Domain.CardClient;
import Domain.Medicine;
import Domain.Transaction;
import Repository.IRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

public class CardClientService {
    private IRepository<CardClient> repository;

    private Stack<UndoRedoOperation<CardClient>> undoableOperations = new Stack<>();
    private Stack<UndoRedoOperation<CardClient>> redoableeOperations = new Stack<>();

    public CardClientService(IRepository<CardClient> repository) {
        this.repository = repository;
    }

    /**
     * adds a card client
     *
     * @param id
     * @param lastName
     * @param firstName
     * @param CNP
     * @param dateOfBirth
     * @param dateOfRegistration
     * @throws CardClientServiceException if a card with this CNP exists
     */
    public void insert(String id, String lastName, String firstName, String CNP, LocalDate dateOfBirth,
                       LocalDate dateOfRegistration) {
        CardClient card = new CardClient(id, lastName, firstName, CNP, dateOfBirth, dateOfRegistration);
        List<CardClient> all = new ArrayList<>(repository.getAll());
        for (CardClient existingCNP : all) {
            if (CNP.equals(existingCNP.getCNP())) {
                throw new CardClientServiceException(String.format("The %s CNP already exists", CNP));

            }
        }
        repository.insert(card);
        undoableOperations.add(new AddOperation<>(repository, card));
        redoableeOperations.clear();
    }

    /**
     * updates a card client
     *
     * @param id
     * @param lastName
     * @param firstName
     * @param CNP
     * @param dateOfBirth
     * @param dateOfRegistration
     * @throws CardClientServiceException if a card with this CNP exists and isn't the current card CNP
     */
    public void update(String id, String lastName, String firstName, String CNP,
                       LocalDate dateOfBirth, LocalDate dateOfRegistration) {
        CardClient actualCard = repository.getById(id);
        CardClient updateCard = new CardClient(id, lastName, firstName, CNP, dateOfBirth, dateOfRegistration);
        List<CardClient> all = new ArrayList<>(repository.getAll());
        for (CardClient existingCNP : all) {
            if (CNP.equals(existingCNP.getCNP()) && !CNP.equals(updateCard.getCNP())) {
                throw new CardClientServiceException(String.format("This %s CNP already exosts", CNP));
            }
        }
        repository.update(updateCard);

        undoableOperations.add(new UpdateOperation(repository, updateCard, actualCard));

        redoableeOperations.clear();
    }

    /**
     * Searches clients whose field contain a given text.
     *
     * @return a list of clients whose fields contain text.
     */
    public List<CardClient> fullTextSearch(String text) {
        List<CardClient> found = new ArrayList<>();
        for (CardClient c : repository.getAll()) {

            if ((c.getId().contains(text) || c.getLastName().contains(text) ||
                    c.getFirstName().contains(text) || c.getCNP().contains(text) ||
                    c.getDateOfBirth().toString().contains(text) || c.getDateOfRegistration().toString().contains(text)) &&
                    !found.contains(c)) {
                found.add(c);
            }
        }
        return found;
    }

    public void undo() {
        if (!undoableOperations.empty()) {
            UndoRedoOperation<CardClient> lastOperation = undoableOperations.pop();
            lastOperation.doUndo();
            redoableeOperations.add(lastOperation);
        }
    }

    public void redo() {
        if (!redoableeOperations.empty()) {
            UndoRedoOperation<CardClient> lastOperation = redoableeOperations.pop();
            lastOperation.doRedo();
            undoableOperations.add(lastOperation);
        }
    }

    /**
     * removes a client card
     *
     * @param id the id of the client card to removes
     */
    public void remove(String id) {
        CardClient card = repository.getById(id);
        repository.remove(id);
        undoableOperations.add(new DeleteTransaction<>(repository, card));
        redoableeOperations.clear();
    }


    /**
     * list of all client cards
     *
     * @return an ArrayList with all client cards
     */
    public List<CardClient> getAll() {
        return repository.getAll();
    }

    public IRepository<CardClient> getRepository() {
        return repository;
    }
}


