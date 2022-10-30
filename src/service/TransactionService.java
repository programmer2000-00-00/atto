package service;

import dto.Card;
import dto.Profile;
import dto.Terminal;
import dto.Transaction;
import enums.TransactionType;
import repository.CardRepository;
import repository.ProfileRepository;
import repository.TerminalRepository;
import repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionService {
    private TransactionRepository transactionRepository = new TransactionRepository();

    private ProfileRepository profileRepository = new ProfileRepository();
    private TerminalRepository terminalRepository = new TerminalRepository();
    private CardRepository cardRepository = new CardRepository();

    public void createTransaction(Integer cardId, Integer terminalId, Double amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setTerminalId(terminalId);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setCreatedDate(LocalDateTime.now());

        transactionRepository.createTransaction(transaction);
    }

    public void transactionList() {
        List<Transaction> transactionList = transactionRepository.getTransactionList();
        if (transactionList.isEmpty()){
            System.out.println("Transaction not exist");
            return;
        }

        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }
    }

    public void todayTransactionList() {
        List<Transaction> transactionList = transactionRepository.getTodayTransactionList();
        if (transactionList.isEmpty()){
            System.out.println("Transaction not exist");
            return;
        }

        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }

    }

    public void transactionByDay(String transactionByDay) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(transactionByDay, timeFormatter);

        List<Transaction> transactionList =  transactionRepository.transactionByDay(localDate);
        if (transactionList.isEmpty()){
            System.out.println("Transaction not exist");
            return;
        }

        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }
    }

    public void transactionBetweenDays(String fromDate, String toDate) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(fromDate, timeFormatter);
        LocalDate endDate = LocalDate.parse(toDate, timeFormatter);


        List<Transaction> transactionList =  transactionRepository.transactionBetweenDays(startDate, endDate);
        if (transactionList.isEmpty()){
            System.out.println("Transaction not exist");
            return;
        }

        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }



    }

    public void transactionByTerminal(String terminalCode) {
        Terminal terminal = terminalRepository.getTerminalByCode(terminalCode);
        if(terminal==null){
            System.out.println("Terminal not found");
            return;
        }

        List<Transaction> transactionList = transactionRepository.transactionByTerminal(terminalCode);
        if (transactionList.isEmpty()){
            System.out.println("Transaction not exist");
            return;
        }

        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }
    }

    public void transactionByCard(String cardNumber) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if(card==null){
            System.out.println("Card not found");
            return;
        }

        List<Transaction> transactionList = transactionRepository.transactionByCard(cardNumber);
        if (transactionList.isEmpty()){
            System.out.println("Transaction not exist");
            return;
        }

        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }
    }

    public void profileTransactionList() {
        List<Transaction> transactionList = transactionRepository.profileTransactionList();
        if (transactionList.isEmpty()){
            System.out.println("Transaction not exist");
            return;
        }

        for (Transaction transaction : transactionList) {
            System.out.println(transaction);
        }
    }

    public void payment(String cardNumber, String terminalCode) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        Terminal terminal = terminalRepository.getTerminalByCode(terminalCode);
        if(card==null || terminal==null){
            System.out.println("Card or Terminal not found");
            return;
        }

        transactionRepository.makePayment();
        Transaction transaction = new Transaction();

        transaction.setCardId(card.getId());
        transaction.setAmount(1400D);
        transaction.setTerminalId(terminal.getId());
        transaction.setTransactionType(TransactionType.Payment);
        transaction.setCreatedDate(LocalDateTime.now());
        transactionRepository.createTransaction(transaction);


    }
}
