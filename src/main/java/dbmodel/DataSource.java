package dbmodel;

import accounts.Account;

import java.sql.*;
import java.util.Scanner;

public class DataSource {
    public static final String DB_NAME = "password_database.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\Users\\Zahari Mikov\\IdeaProjects\\passwordManager(Console)\\" + DB_NAME;

    public static final String TABLE_ACCOUNTS = "accounts";
    public static final String COLUMN_ACCOUNTS_ID = "id";
    public static final String COLUMN_ACCOUNTS_ACCOUNT = "account";
    public static final String COLUMN_ACCOUNTS_USERNAME = "username";
    public static final String COLUMN_ACCOUNTS_PASSWORD = "password";
    public static final String COLUMN_ACCOUNTS_ADDITIONAL_INFORMATION = "additional_information";
    public static final int INDEX_ACCOUNTS_ID = 1;
    public static final int INDEX_ACCOUNTS_ACCOUNT = 2;
    public static final int INDEX_ACCOUNTS_USERNAME = 3;
    public static final int INDEX_ACCOUNTS_PASSWORD = 4;
    public static final int INDEX_ACCOUNTS_ADDITIONAL_INFORMATION = 5;

    // SELECT * FROM accounts
    public static final String QUERY_ACCOUNTS = "SELECT * FROM " + TABLE_ACCOUNTS;

    // SELECT id FROM accounts
    public static final String QUERY_ACCOUNTS_IDS = "SELECT " + COLUMN_ACCOUNTS_ID + " FROM " + TABLE_ACCOUNTS;

    // SELECT account FROM accounts
    public static final String QUERY_ACCOUNTS_ACCOUNTS = "SELECT " + COLUMN_ACCOUNTS_ACCOUNT + " FROM " + TABLE_ACCOUNTS;

    // SELECT username FROM accounts
    public static final String QUERY_ACCOUNTS_USERNAMES = "SELECT " + COLUMN_ACCOUNTS_USERNAME + " FROM " + TABLE_ACCOUNTS;

    // SELECT password FROM accounts
    public static final String QUERY_ACCOUNTS_PASSWORDS = "SELECT " + COLUMN_ACCOUNTS_PASSWORD + " FROM " + TABLE_ACCOUNTS;

    // SELECT additional_information FROM accounts
    public static final String QUERY_ACCOUNTS_ADDITIONAL_INFORMATION = "SELECT " + COLUMN_ACCOUNTS_ADDITIONAL_INFORMATION + " FROM " + TABLE_ACCOUNTS;

    // SELECT * FROM accounts
    // WHERE account=?;
    public static final String QUERY_RECORDS_BY_ACCOUNT = "SELECT * FROM " + TABLE_ACCOUNTS +
            " WHERE " + COLUMN_ACCOUNTS_ACCOUNT + "=?";

    // SELECT * FROM accounts
    // WHERE account='Master Account';
    public static final String QUERY_MASTER_ACCOUNT = "SELECT * " + " FROM " + TABLE_ACCOUNTS +
            " WHERE " + COLUMN_ACCOUNTS_ACCOUNT + "=?";

    // INSERT INTO accounts (account, username, password, additional_information)
    // VALUES (?, ?, ?, ?);
    public static final String INSERT_INTO_ACCOUNTS = "INSERT INTO " + TABLE_ACCOUNTS + " (" +
            COLUMN_ACCOUNTS_ACCOUNT + ", " + COLUMN_ACCOUNTS_USERNAME + ", " + COLUMN_ACCOUNTS_PASSWORD + ", " + COLUMN_ACCOUNTS_ADDITIONAL_INFORMATION + ")" +
            " VALUES " + "(?, ?, ?, ?)";

    // DELETE FROM accounts
    // WHERE account=?
    public static final String DELETE_FROM_ACCOUNTS_BY_ACCOUNT = "DELETE FROM " + TABLE_ACCOUNTS +
            " WHERE " + COLUMN_ACCOUNTS_ACCOUNT + "=?";

    // UPDATE accounts
    // SET password = '123456'
    // WHERE account = 'Master Account';
    public static final String UPDATE_MASTER_ACCOUNT_PASSWORD = "UPDATE " + TABLE_ACCOUNTS +
            " SET " + COLUMN_ACCOUNTS_PASSWORD + " = ? " +
            " WHERE " + COLUMN_ACCOUNTS_ACCOUNT + " = ?";

    public static final int defaultIndentation = 2;

    private Connection conn;
    private PreparedStatement query_accounts;
    private PreparedStatement query_accounts_again;
    private PreparedStatement query_accounts_ids;
    private PreparedStatement query_accounts_accounts;
    private PreparedStatement query_accounts_usernames;
    private PreparedStatement query_accounts_passwords;
    private PreparedStatement query_accounts_additional_information;
    private PreparedStatement query_records_by_account;
    private PreparedStatement query_master_account;
    private PreparedStatement insert_into_accounts;
    private PreparedStatement delete_from_accounts_by_account;
    private PreparedStatement update_master_account_password;

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            query_accounts = conn.prepareStatement(QUERY_ACCOUNTS);
            query_accounts_again = conn.prepareStatement(QUERY_ACCOUNTS);
            query_accounts_ids = conn.prepareStatement(QUERY_ACCOUNTS_IDS);
            query_accounts_accounts = conn.prepareStatement(QUERY_ACCOUNTS_ACCOUNTS);
            query_accounts_usernames = conn.prepareStatement(QUERY_ACCOUNTS_USERNAMES);
            query_accounts_passwords = conn.prepareStatement(QUERY_ACCOUNTS_PASSWORDS);
            query_accounts_additional_information = conn.prepareStatement(QUERY_ACCOUNTS_ADDITIONAL_INFORMATION);
            query_records_by_account = conn.prepareStatement(QUERY_RECORDS_BY_ACCOUNT);
            query_master_account = conn.prepareStatement(QUERY_MASTER_ACCOUNT);
            insert_into_accounts = conn.prepareStatement(INSERT_INTO_ACCOUNTS);
            delete_from_accounts_by_account = conn.prepareStatement(DELETE_FROM_ACCOUNTS_BY_ACCOUNT);
            update_master_account_password = conn.prepareStatement(UPDATE_MASTER_ACCOUNT_PASSWORD);
            return true;
        } catch (SQLException e) {
            System.out.println("There is a problem: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        closeQuery(query_accounts);
        closeQuery(query_accounts_again);
        closeQuery(query_accounts_ids);
        closeQuery(query_accounts_accounts);
        closeQuery(query_accounts_usernames);
        closeQuery(query_accounts_passwords);
        closeQuery(query_accounts_additional_information);
        closeQuery(query_records_by_account);
        closeQuery(query_master_account);
        closeQuery(insert_into_accounts);
        closeQuery(delete_from_accounts_by_account);
        closeQuery(update_master_account_password);
        closeConnection(conn);
    }

    private void closeQuery(PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void retrieveAccounts() {
        try (ResultSet results = query_accounts.executeQuery();) {
            String halfIdIndent = indent(getMaxFieldLength(INDEX_ACCOUNTS_ID) % 2 == 0 ?
                    getMaxFieldLength(INDEX_ACCOUNTS_ID) / 2 : getMaxFieldLength(INDEX_ACCOUNTS_ID) / 2 + 1);

            String halfAccountIndent = indent(getMaxFieldLength(INDEX_ACCOUNTS_ACCOUNT) % 2 == 0 ?
                    getMaxFieldLength(INDEX_ACCOUNTS_ACCOUNT) / 2 : getMaxFieldLength(INDEX_ACCOUNTS_ACCOUNT) / 2 + 1);

            String halfUsernameIndent = indent(getMaxFieldLength(INDEX_ACCOUNTS_USERNAME) % 2 == 0 ?
                    getMaxFieldLength(INDEX_ACCOUNTS_USERNAME) / 2 : getMaxFieldLength(INDEX_ACCOUNTS_USERNAME) / 2 + 1);

            String halfPasswordIndent = indent(getMaxFieldLength(INDEX_ACCOUNTS_PASSWORD) % 2 == 0 ?
                    getMaxFieldLength(INDEX_ACCOUNTS_PASSWORD) / 2 : getMaxFieldLength(INDEX_ACCOUNTS_PASSWORD) / 2 + 1);

            String halfAdditionalInformation = indent(getMaxFieldLength(INDEX_ACCOUNTS_ADDITIONAL_INFORMATION) % 2 == 0 ?
                    getMaxFieldLength(INDEX_ACCOUNTS_ADDITIONAL_INFORMATION) / 2 : getMaxFieldLength(INDEX_ACCOUNTS_ADDITIONAL_INFORMATION) / 2 + 1);

            int idFieldLength = COLUMN_ACCOUNTS_ID.length();
            int accountFieldLength = COLUMN_ACCOUNTS_ACCOUNT.length();
            int usernameFieldLength = COLUMN_ACCOUNTS_USERNAME.length();
            int passwordFieldLength = COLUMN_ACCOUNTS_PASSWORD.length();
            int additionalInformationFieldLength = COLUMN_ACCOUNTS_ADDITIONAL_INFORMATION.length();

            System.out.println(halfIdIndent + "id" + halfIdIndent + "|" +
                    halfAccountIndent + "account" + halfAccountIndent + "|" +
                    halfUsernameIndent + "username" + halfUsernameIndent + "|" +
                    halfPasswordIndent + "password" + halfPasswordIndent + "|" +
                    halfAdditionalInformation + "additional_information" + halfAdditionalInformation);

            while (results.next()) {
                String id = String.valueOf(results.getInt(INDEX_ACCOUNTS_ID));
                String account = results.getString(INDEX_ACCOUNTS_ACCOUNT);
                String username = results.getString(INDEX_ACCOUNTS_USERNAME);
                String password = results.getString(INDEX_ACCOUNTS_PASSWORD);
                String additionalInformation = results.getString(INDEX_ACCOUNTS_ADDITIONAL_INFORMATION);

                if (additionalInformation == null || additionalInformation.isEmpty()) {
                    additionalInformation = "None";
                }

                String idIndent = getFieldIndent(idFieldLength, id.length(), INDEX_ACCOUNTS_ID);
                String accountIndent = getFieldIndent(accountFieldLength, account.length(), INDEX_ACCOUNTS_ACCOUNT);
                String usernameIndent = getFieldIndent(usernameFieldLength, username.length(), INDEX_ACCOUNTS_USERNAME);
                String passwordIndent = getFieldIndent(passwordFieldLength, password.length(), INDEX_ACCOUNTS_PASSWORD);
                String additionalInformationIndent = getFieldIndent(additionalInformationFieldLength, additionalInformation.length(), INDEX_ACCOUNTS_ADDITIONAL_INFORMATION);

                System.out.print(indent(defaultIndentation) + id + idIndent + "|");
                System.out.print(indent(defaultIndentation) + account + accountIndent + "|");
                System.out.print(indent(defaultIndentation) + username + usernameIndent + "|");
                System.out.print(indent(defaultIndentation) + password + passwordIndent + "|");
                System.out.print(indent(defaultIndentation) + additionalInformation + additionalInformationIndent);
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("Query 'query_accounts' failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getFieldIndent(int columnNameLength, int fieldLength, int columnIndex) {
        String fieldIndent;
        if (getMaxFieldLength(columnIndex) % 2 == 0) {
            fieldIndent = indent(getMaxFieldLength(columnIndex) + columnNameLength - fieldLength - defaultIndentation);
        } else {
            fieldIndent = indent(getMaxFieldLength(columnIndex) + columnNameLength - fieldLength + 1 - defaultIndentation);
        }
        return fieldIndent;
    }

    private String indent(int timesIndent) {
        return " ".repeat(Math.max(0, timesIndent));
    }

    private int getMaxFieldLength(int columnIndex) {
        int max = 0;
        try (ResultSet result = query_accounts_again.executeQuery();) {
            while (result.next()) {
                String currentField = result.getString(columnIndex);
                if (max < String.valueOf(currentField).length()) {
                    max = String.valueOf(currentField).length();
                }
            }
        } catch (SQLException e) {
            System.out.println("Query 'query_accounts' failed: " + e.getMessage());
        }
        return max;
    }

    public void insertIntoAccounts() {
        try {
            Account newAccount = generateNewAccount();

            String account = newAccount.getAccount();
            String username = newAccount.getUsername();
            String password = newAccount.getPassword();
            String additionalInformation = newAccount.getAdditionalInformation();

            insert_into_accounts.setString(1, account);
            insert_into_accounts.setString(2, username);
            insert_into_accounts.setString(3, password);
            insert_into_accounts.setString(4, additionalInformation);

            insert_into_accounts.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Query 'insert_into_accounts' failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Account generateNewAccount() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter account: ");
        String account = scanner.nextLine();

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter additional information: ");
        String additionalInformation = scanner.nextLine();

        return new Account(account, username, password, additionalInformation);
    }

    public void deleteAccountByAccount() {
        try {
            String account = getAccount();
            delete_from_accounts_by_account.setString(1, account);
            int rowDeleted = delete_from_accounts_by_account.executeUpdate();

            if (rowDeleted != 1) {
                System.out.println("There is no such account");
                return;
            }

            System.out.println("Successfully deleted " + account);
        } catch (SQLException e) {
            System.out.println("Query 'delete_from_accounts_by_account' failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getAccount() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter account to be deleted: ");
        return scanner.nextLine();
    }

    public void getAccountDetails() {
        Account chosenAccount = new Account();
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter chosen account: ");
            String enteredAccount = scanner.nextLine();

            query_records_by_account.setString(1, enteredAccount);
            ResultSet result = query_records_by_account.executeQuery();

            while (result.next()) {
                String accountField = result.getString(INDEX_ACCOUNTS_ACCOUNT);
                String usernameField = result.getString(INDEX_ACCOUNTS_USERNAME);
                String passwordField = result.getString(INDEX_ACCOUNTS_PASSWORD);
                String additionalInformationField = result.getString(INDEX_ACCOUNTS_ADDITIONAL_INFORMATION);

                chosenAccount.setAccount(accountField);
                chosenAccount.setUsername(usernameField);
                chosenAccount.setPassword(passwordField);
                chosenAccount.setAdditionalInformation(additionalInformationField);

                printAccountDetails(chosenAccount);
            }
            result.close();
        } catch (SQLException e) {
            System.out.println("Query 'query_records_by_account' failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertMasterAccount(Account master) {
        try {
            insert_into_accounts.setString(1, "Master Account");
            insert_into_accounts.setString(2, master.getUsername());
            insert_into_accounts.setString(3, master.getPassword());
            insert_into_accounts.setString(4, master.getAdditionalInformation());

            insert_into_accounts.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insertion 'insert_into_accounts' failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void changeMasterPassword(String newPassword) {
        try {
            update_master_account_password.setString(1, newPassword);
            update_master_account_password.setString(2, "Master Account");
            update_master_account_password.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update 'update_master_account_password' failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Account getMasterAccount() {
        Account master = null;
        try {
            query_master_account.setString(1, "Master Account");
            ResultSet result = query_master_account.executeQuery();
            if (result.next()) {
                String account = result.getString(2);
                String username = result.getString(3);
                String password = result.getString(4);
                String additionalInformation = result.getString(5);
                master = new Account(account, username, password, additionalInformation);
            } else {
                return createMasterAccount();
            }
            result.close();
        } catch (SQLException e) {
            System.out.println("Query 'query_master_account' failed: " + e.getMessage());
            e.printStackTrace();
        }
        return master;
    }

    public boolean checkForMasterAccount() {
        try {
            query_master_account.setString(1, "Master Account");
            ResultSet result = query_master_account.executeQuery();
            if (result.next()) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Query 'query_master_account' failed: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public Account createMasterAccount() {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password;
        String repeatedPassword;
        do {
            System.out.print("Enter username: ");
            username = scanner.nextLine();

            System.out.print("Enter password: ");
            password = scanner.nextLine();

            System.out.print("Repeat password: ");
            repeatedPassword = scanner.nextLine();
            if (!password.equals(repeatedPassword)) {
                System.out.println("Incorrect repeated password");
            }
        } while (!password.equals(repeatedPassword));

        return new Account("Master Account", username, password, "This account is used to login to database.");
    }

    public void printAccountDetails(Account account) {
        System.out.println("account: " + account.getAccount());
        System.out.println("username: " + account.getUsername());
        System.out.println("password: " + account.getPassword());
        System.out.println("additional information: " + account.getAdditionalInformation());
    }
}
