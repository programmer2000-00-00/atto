package controller;

import container.ComponentContainer;
import dto.Profile;
import dto.Terminal;
import repository.CardRepository;
import service.CardService;
import service.ProfileService;
import service.TerminalService;
import service.TransactionService;
import util.ScannerUtil;

import java.util.Scanner;


public class AdminController {
    private CardService cardService = new CardService();
    private ProfileService profileService = new ProfileService();
    private TerminalService terminalService = new TerminalService();

    private TransactionService transactionService = new TransactionService();

    public void start(Profile profile) {
        boolean b = true;

        while (b) {
            menu();
            int operation = ScannerUtil.getAction();
            switch (operation) {
                case 1 -> addCard();
                case 2 -> cardList();
                case 3 -> updateCard();
                case 4 -> changeCardStatus();
                case 5 -> deleteCard();
                case 6 -> createTerminal();
                case 7 -> terminalList();
                case 8 -> updateTerminal();
                case 9 -> changeTerminalStatus();
                case 10 -> deleteTerminal();
                case 11 -> profileList();
                case 12 -> changeProfileStatus();
                case 13 -> transactionList();
                case 14 -> cardCompany();
                case 15 -> todayTransactionList();
                case 16 -> transactionByDay();
                case 17 -> transactionBetweenDays();
                case 18 -> totalBalance();
                case 19 -> transactionByTerminal();
                case 20 -> transactionByCard();
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
        // (Card)
        System.out.println("1. Create Card");
        System.out.println("2. Card List ");
        System.out.println("3. Update Card ");
        System.out.println("4. Card Change Status");
        System.out.println("5. Delete Card");
        // (Terminal)
        System.out.println("6. Create Terminal");
        System.out.println("7. Terminal List");
        System.out.println("8. Update Terminal");
        System.out.println("9. Change Terminal Status");
        System.out.println("10. Delete Terminal");
        //  (Profile)
        System.out.println("11. Profile List");
        System.out.println("12. Change Profile Status");
        // (Transaction)
        System.out.println("13. Transaction List");
        System.out.println("14. Company Card Balance");
        //  (Statistic)
        System.out.println("15. Bugungi to'lovlar");
        System.out.println("16. Kunlik to'lovlar");
        System.out.println("17. Oraliq to'lovlar");
        System.out.println("18. Umumiy balance");
        System.out.println("19. Transaction by Terminal");
        System.out.println("20. Transaction By Card");


        System.out.println("0. Log out");
    }

    /**
     * Card
     */


    private void addCard() {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        System.out.print("Enter card expired date (yyyy.MM.dd): ");
        String expiredDate = scanner.nextLine();

        cardService.adminCreateCard(cardNumber, expiredDate);
    }

    private void cardList() {
        cardService.cardList();
    }

    private void deleteCard() {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        cardService.adminDeleteCard(cardNumber);
    }

    private void changeCardStatus() {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        cardService.adminChangeStatus(cardNumber);
    }

    private void updateCard() {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        System.out.print("Enter card expired date (yyyy.MM.dd): ");
        String expiredDate = scanner.nextLine();

        cardService.adminUpdateCard(cardNumber, expiredDate);
    }


    /**
     * Terminal
     */

    private void createTerminal() {
        System.out.print("Enter  code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Terminal terminal = new Terminal();
        terminal.setCode(code);
        terminal.setAddress(address);

        terminalService.addTerminal(terminal);
    }

    private void terminalList() {
        terminalService.terminalList();
    }

    private void updateTerminal() {
        System.out.print("Enter code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Terminal terminal = new Terminal();
        terminal.setCode(code);
        terminal.setAddress(address);

        terminalService.updateTerminal(terminal);
    }

    private void changeTerminalStatus() {
        System.out.print("Enter code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        terminalService.changeTerminalStatus(code);
    }

    private void deleteTerminal() {
        System.out.print("Enter code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        terminalService.deleteTerminal(code);
    }

    /**
     * Profile
     */

    private void profileList() {
        profileService.profileList();
    }

    private void changeProfileStatus() {
        System.out.print("Enter profile phone: ");
        Scanner scanner = new Scanner(System.in);
        String phone = scanner.nextLine();

        profileService.changeProfileStatus(phone);
    }


    /**
     * Transaction
     */

    private void transactionList() {
        transactionService.transactionList();
    }

    private void cardCompany() {

    }

    /**
     * Statistic
     */

    private void todayTransactionList() {
        transactionService.todayTransactionList();
    }

    private void transactionByDay() {
        System.out.print("Enter Date (yyyy-MM-dd): ");
        Scanner scanner = new Scanner(System.in);
        String transactionByDay = scanner.nextLine();

        transactionService.transactionByDay(transactionByDay);
    }

    private void transactionBetweenDays() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter FromDate (yyyy-MM-dd): ");
        String fromDate = scanner.nextLine();

        System.out.print("Enter toDate (yyyy-MM-dd): ");
        String toDate = scanner.nextLine();

        transactionService.transactionBetweenDays(fromDate, toDate);

    }

    private void totalBalance() {

    }

    private void transactionByTerminal() {
        System.out.print("Enter terminal code: ");
        Scanner scanner = new Scanner(System.in);
        String terminalCode = scanner.nextLine();

        transactionService.transactionByTerminal(terminalCode);
    }

    private void transactionByCard() {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        transactionService.transactionByCard(cardNumber);
    }


}
