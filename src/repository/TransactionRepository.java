package repository;

import container.ComponentContainer;
import db.DataBase;
import dto.Profile;
import dto.Transaction;
import enums.GeneralStatus;
import enums.ProfileRole;
import enums.TransactionType;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class TransactionRepository {

    public int createTransaction(Transaction transaction) {
        try (Connection connection = DataBase.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into transaction(card_id,terminal_id,amount,type,created_date) " +
                            "values (?,?,?,?,?)");
            statement.setInt(1, transaction.getCardId());

            if (transaction.getTerminalId() != null) {
                statement.setInt(2, transaction.getTerminalId());
            } else {
                statement.setObject(2, null);
            }

            statement.setDouble(3, transaction.getAmount());
            statement.setString(4, transaction.getTransactionType().name());
            statement.setTimestamp(5, Timestamp.valueOf(transaction.getCreatedDate()));

            int resultSet = statement.executeUpdate();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;

    }

    public List<Transaction> getTransactionList() {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("select * from transaction order by created_date desc");
            return getTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public List<Transaction> getTodayTransactionList() {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("select * from transaction\n" +
                    "where created_date between current_date and current_timestamp\n" +
                    "order by created_date desc");
            return getTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public List<Transaction> transactionByDay(LocalDate localDate) {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();


            ResultSet resultSet = statement.executeQuery("Select * from transaction \n" +
                    "where created_date between (Select '" + localDate + "'::Timestamp)\n" +
                    "and (Select '" + localDate + "'::Timestamp + '24 hours')");
            return getTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public List<Transaction> transactionBetweenDays(LocalDate startDate, LocalDate endDate) {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("Select * from transaction \n" +
                    "where created_date between '" + startDate + "' and '" + endDate + "'\n" +
                    "order by created_date desc");

            return getTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public List<Transaction> transactionByCard(String cardNumber) {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("\n" +
                    "Select * from transaction \n" +
                    "where card_id = (Select id from card \n" +
                    "                 where card_number = '" + cardNumber + "')");

            return getTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public List<Transaction> transactionByTerminal(String terminalCode) {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("\n" +
                    "Select * from transaction \n" +
                    "where terminal_id = (Select id from terminal \n" +
                    "                     where code = '" + terminalCode + "');");

            return getTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    private List<Transaction> getTransactions(ResultSet resultSet) throws SQLException {
        List<Transaction> transactionList = new LinkedList<>();
        while (resultSet.next()) {
            Transaction transaction = new Transaction();
            transaction.setId(resultSet.getInt("id"));
            transaction.setCardId(resultSet.getInt("card_id"));
            transaction.setAmount(resultSet.getDouble("amount"));
            transaction.setTerminalId(resultSet.getInt("terminal_id"));
            transaction.setTransactionType(TransactionType.valueOf(resultSet.getString("type")));
            transaction.setCreatedDate(resultSet.getTimestamp("created_date").toLocalDateTime());
            transactionList.add(transaction);
        }
        return transactionList;
    }

    public List<Transaction> profileTransactionList() {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("Select * from transaction \n" +
                    "where card_id in (Select id from card\n" +
                    "                 where phone = '" + ComponentContainer.currentProfile.getPhone() + "');");

            return getTransactions(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    public void makePayment() {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();

            if (checkBalance() <= 1400) {
                System.out.println("Not enough money");
                return;
            }

            String query = String.format("Update card set balance = balance - 1400 " +
                                         "where phone = '%s'", ComponentContainer.currentProfile.getPhone());

//            int result = statement.executeUpdate("\n" +
//                    "update card set balance = balance - 1400\n" +
//                    "where phone = '" + ComponentContainer.currentProfile.getPhone() + "'");

            int result = statement.executeUpdate(query);

            if (result == 0) {
                System.out.println("ERROR");
                return;
            } else {
                System.out.println("SUCCESS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public double checkBalance() {
        try (Connection connection = DataBase.getConnection()) {
            Statement statement = connection.createStatement();

            String query = String.format("Select balance from card where phone = '%s'", ComponentContainer.currentProfile.getPhone());

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                return resultSet.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return 0;
    }
}
