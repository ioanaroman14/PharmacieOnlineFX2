package Domain;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction extends Entity {
    private String idMedicine, idCardClient;
    private LocalDate date;
    private LocalTime time;
    private int numberOfItems;
    private double basePrice, discount;

    public Transaction(String id, String idMedicine, String idCardClient, int numberOfItems, LocalDate date,
                       LocalTime time,  double basePrice, double discount) {
        super(id);
        this.idMedicine = idMedicine;
        this.idCardClient = idCardClient;
        this.numberOfItems = numberOfItems;
        this.date = date;
        this.time = time;
        this.basePrice = basePrice;
        this.discount = discount;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                ", idMedicine = '" + idMedicine + '\'' +
                ", idCardClient = '" + idCardClient + '\'' +
                ", numberOfItems = " + numberOfItems +
                ", date = '" + date + '\'' +
                ", time = '" + time + '\'' +
                ", basePrice = " + basePrice +
                ", discount = " + discount +
                '}';
    }

    /**
     * Custom getter for the discounted price.
     * @return the price after applying the discount.
     */
    public double getDiscountPrice(){
        return basePrice * numberOfItems - discount * basePrice * numberOfItems;
    }

    public String getIdMedicine() {
        return idMedicine;
    }

    public void setIdMedicine(String idMedicine) {
        this.idMedicine = idMedicine;
    }

    public String getIdCardClient() {
        return idCardClient;
    }

    public void setIdCardClient(String idCardClient) {
        this.idCardClient = idCardClient;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}



