package consoleapp;

import accounts.Account;
import dbmodel.DataSource;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DataSource dataSource = new DataSource();

        if (!dataSource.open()) {
            System.out.println("Can't open database!");
            return;
        }

        System.out.print("");

        System.out.println("MENU DEMO VERSION 1.0");
        System.out.println();
        System.out.println("Created by Mr. Zahari Mikov");

        Scanner scanner = new Scanner(System.in);

        System.out.print("Do you have master account: ");
        String answer = scanner.nextLine();

        Account master = null;

        if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
            if (!dataSource.checkForMasterAccount()) {
                System.out.println("You don't have a master account");
                System.out.println("\n          Register          ");
                dataSource.insertMasterAccount(dataSource.getMasterAccount());
            }
        } else {
            if (dataSource.checkForMasterAccount()) {
                System.out.println("You have master account.\nDo you want to change your password? ");
                System.out.print("Answer: ");
                answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y")) {
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    System.out.print("Repeat new password: ");
                    String repeatNewPassword = scanner.nextLine();
                    if (newPassword.equals(repeatNewPassword)) {
                        dataSource.changeMasterPassword(newPassword);
                    }
                }
            } else {
                System.out.println("\n          Register          ");
                dataSource.insertMasterAccount(dataSource.getMasterAccount());
            }
        }

        master = dataSource.getMasterAccount();

        System.out.println("\n          LOGIN          ");

        int loginAttemptCounter = 0;
        boolean loginFlag = true;

        while (loginFlag) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (username.equals(master.getUsername()) && password.equals(master.getPassword())) {
                System.out.println("Successfully logged in.");
                sleep(750);
                loginFlag = false;
            } else {
                System.out.println("Incorrect login credentials!");
            }

            loginAttemptCounter++;

            if (loginAttemptCounter == 3) {
                System.out.println("Too many attempts for login.");
                sleep(3000);
            }
        }

        while (true) {
            System.out.println();
            System.out.println("1. Retrieve all accounts");
            System.out.println("2. Retrieve chosen account");
            System.out.println("3. Insert new account");
            System.out.println("4. Delete account");
            System.out.println("5. Exit");

            int choice = validateInput();

            System.out.println();

            switch (choice) {
                case 1:
                    dataSource.retrieveAccounts();
                    sleep(2000);
                    break;
                case 2:
                    dataSource.getAccountDetails();
                    sleep(2000);
                    break;
                case 3:
                    dataSource.insertIntoAccounts();
                    sleep(1000);
                    break;
                case 4:
                    dataSource.deleteAccountByAccount();
                    sleep(1000);
                    break;
                case 5:
                    exitProgram();
                    sleep(1000);
                    dataSource.close();
                    return;
                default:
                    System.out.println("There is no such option");
                    break;
            }
        }
    }

    private static int validateInput() {
        Scanner scanner = new Scanner(System.in);

        int choice;

        try {
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
        } catch (InputMismatchException ime) {
            System.out.println("Error");
            return 0;
        }
        return choice;
    }

    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ie) {
            System.out.println(ie.getMessage());
        }
    }

    public static void exitProgram() {
        System.out.println("Have a nice day!");
        System.exit(1);
    }
}
