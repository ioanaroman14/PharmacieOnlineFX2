package Service;

import Domain.Medicine;

public class MedicineByTransactionSolds {
    private Medicine medicine;
    private int transactions;

    public MedicineByTransactionSolds(Medicine medicine, int transactions){
        this.medicine = medicine;
        this.transactions = transactions;
    }

    public Medicine getMedicine(){
        return medicine;
    }
    public int getTransactions(){
        return transactions;
    }
}
