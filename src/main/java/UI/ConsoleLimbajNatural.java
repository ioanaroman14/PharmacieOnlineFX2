package UI;

import Domain.CardClient;
import Domain.Medicine;
import Domain.Transaction;
import Service.CardClientService;
import Service.MedicineService;
import Service.TransactionService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class ConsoleLimbajNatural {

    private MedicineService medicineService;
    private CardClientService cardClientService;
    private TransactionService transactionService;

    private Scanner scanner;

    public ConsoleLimbajNatural(MedicineService medicineService, CardClientService cardClientService,
                                TransactionService transactionService) {
        this.medicineService = medicineService;
        this.cardClientService = cardClientService;
        this.transactionService = transactionService;

        this.scanner = new Scanner(System.in);

    }

    public void showMeniu() {
        System.out.print("addMed   ");
        System.out.print("addCardClient    ");
        System.out.print("addTrans");
        System.out.println();
        System.out.print("remMed   ");
        System.out.print("remCardClient    ");
        System.out.println("remTrans");
        //System.out.println();
        System.out.print("viewMed  ");
        System.out.print("viewCardClient  ");
        System.out.println(" viewTrans");
        System.out.println();
        System.out.println("x - Exit");
    }

    /**
     * public static Scanner input = new Scanner(System.in);
     *
     * public static void main(String[] args)
     * {
     *     System.out.print("Insert a number: ");
     *     int number = input.nextInt();
     *     System.out.print("Text1: ");
     *     String text1 = input.next();
     *     System.out.print("Text2: ");
     *     String text2 = input.next();
     * }
     */
   //public static Scanner input = new Scanner(System.in);

    public void run2() {
        //System.out.print("date: ioana,ada");
        //String date = input.next();
        System.out.println("Alegeti optiunea:");
        System.out.println();
        while (true) {

            showMeniu();
            System.out.println();
            //      addMed,5,nurofen,terapia,10,true
            //      addCardClient,1,ioana,roman,2801014125811,12.10.1990,15.10.2019
            //      addTrans,23,5,10.03.2019,15,12,45
            //      remMed,5
            //      remCardClient,1
            //      remTrans,23
            String op = scanner.nextLine();
            String[] option = op.split(",");
            String[] add = {"addMed","5","nurofen","terapia","10","true"};
            switch (option[0]) {
                case "addMed":
                    //System.out.println("Introduceti datele:");

                    medicineService.insert(option[1], option[2], option[3], Double.parseDouble(option[4]), Boolean.parseBoolean(option[5]));
                    System.out.println("Medicine added!");
                    break;
                case "addCardClient":
                    cardClientService.insert(option[1], option[2], option[3],
                            option[4], LocalDate.of(2101, 12,12),
                            LocalDate.of(2128, 12,12));
                    System.out.println("Card client added!");
                    break;
                case "addTrans":
                    transactionService.add(option[1], option[2],option[3],Integer.parseInt(option[4]),
                            LocalDate.of(2101, 12,12), LocalTime.of(1,2,0));
                    System.out.println("Transaction added!");
                    break;
                case "remMed":
                    medicineService.remove(option[1]);
                    System.out.println("Medicine removed!");
                    break;
                case "remCardClient":
                    cardClientService.remove(option[1]);
                    System.out.println("Card client removed!");
                    break;
                case "remTrans":
                    transactionService.remove(option[1]);
                    System.out.println("Transaction removed!");
                    break;
                case "viewMed":
                    for (Medicine medicine : medicineService.getAll()) {
                        System.out.println(medicine);
                    }
                    break;
                case "viewCardClient":
                    for (CardClient cardClient : cardClientService.getAll()) {
                        System.out.println(cardClient);
                    }
                    break;
                case "viewTrans":
                    for (Transaction transaction : transactionService.getAll()) {
                        System.out.println(transaction);
                    }
                    break;
                    case "x":
                        return;
                    default:
                        System.out.println("Invalid option!");
                        break;

                }

            }
    }
    }
