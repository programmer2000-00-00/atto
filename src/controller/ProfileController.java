package controller;

import container.ComponentContainer;
import dto.Profile;
import enums.TransactionType;
import service.CardService;
import service.ProfileService;
import service.TransactionService;
import util.ScannerUtil;

import java.util.Scanner;

public class ProfileController {
    private CardService cardService = new CardService();

    private TransactionService transactionService = new TransactionService();

    public void start(Profile profile) {
        boolean b = true;

        while (b) {
            menu();
            int operation = ScannerUtil.getAction();
            switch (operation) {
                case 1 -> addCard(profile);
                case 2 -> cardList(profile);
                case 3 -> changeCardStatus(profile);
                case 4 -> deleteCard(profile);
                case 5 -> refill(profile);
                case 6 -> transactionList();
                case 7 -> payment();
                case 0 -> {
                    b = false;
                    ComponentContainer.currentProfile=null;
                }
                default -> {
                    b = false;
                    System.out.println("Wrong operation number");
                }
            }
        }
    }


    public void menu() {
        System.out.println("1. Add Card");
        System.out.println("2. Card List ");
        System.out.println("3. Card Change Status");
        System.out.println("4. Delete Card");
        System.out.println("5. ReFill ");
        System.out.println("6. Transaction List");
        System.out.println("7. Make Payment");
        System.out.println("0. Log out");
    }

    /**
     * Card
     */

    private void addCard(Profile profile) {
        System.out.print("Enter card number: ");

        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        cardService.addCardToProfile(profile.getPhone(), cardNumber);
    }

    private void cardList(Profile profile) {
        System.out.println("--- Card List ---");
        cardService.profileCardList(profile.getPhone());
    }

    private void changeCardStatus(Profile profile) {
        System.out.print("Enter card number: ");

        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();
        cardService.userChangeCardStatus(profile.getPhone(), cardNumber);
    }

    private void deleteCard(Profile profile) {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        cardService.userDeleteCard(profile.getPhone(), cardNumber);
    }

    private void refill(Profile profile) {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        System.out.print("Enter amount: ");
        Double amount = scanner.nextDouble();
        cardService.userRefillCard(profile.getPhone(), cardNumber, amount);
    }


    /**
     * Transaction
     */
    private void transactionList() {
        System.out.println("\t\tTransaction List\t\t");
        transactionService.profileTransactionList();
    }

    private void payment() {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        System.out.print("Enter terminal number: ");
        String terminalCode = scanner.nextLine();

        transactionService.payment(cardNumber, terminalCode);
    }


}
