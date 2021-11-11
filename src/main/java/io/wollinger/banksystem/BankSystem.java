package io.wollinger.banksystem;

import io.wollinger.banksystem.utils.HashUtils;
import io.wollinger.banksystem.utils.ScannerUtils;
import io.wollinger.banksystem.utils.Utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

public class BankSystem {
    private BankAccount currentUser;
    private final HashMap<String, BankAccount> users = new HashMap<>();

    private final static int MIN_PASS_LENGTH = 4;
    private final static String CURRENCY_SYMBOL = "$";

    private final char[] SPECIAL_CHARS = {'!', '"', '$', '%', '&', '/', '(', ')', '=', '?'};

    enum MenuPage { MAIN, MENU_LOGGEDIN, REGISTER, LOGIN, WITHDRAW, DEPOSIT, TRANSFER, PASSWD_CHANGE}

    public BankSystem() {
        if(Main.DEBUG) {
            users.put("sven", new BankAccount("Sven", HashUtils.hash("test!"), new BigDecimal("100")));
            users.put("jess", new BankAccount("jess", HashUtils.hash("test%"), new BigDecimal("42.50")));
        }
    }

    public void showMenu(MenuPage page) {
        switch(page) {
            case MAIN: menuMain();
            case MENU_LOGGEDIN: menuLoggedIn();
            case REGISTER: menuRegister();
            case LOGIN: menuLogin();
            case WITHDRAW: menuWithdraw();
            case DEPOSIT: menuDeposit();
            case TRANSFER: menuTransfer();
            case PASSWD_CHANGE: menuPasswordChange();
        }
    }

    private void menuTransfer() {
        if(currentUser == null)
            showMenu(MenuPage.MAIN);
        Utils.clearConsole();

        println("Transfer\n");
        println("Available balance: " + currentUser.getBalance() + CURRENCY_SYMBOL + "\n");
        print("Receiver username: ");
        String receiver = ScannerUtils.nextLine();
        if(!users.containsKey(receiver.toLowerCase())) {
            println("\nUser does not exist! Press any key to try again.");
            Utils.pause();
            menuTransfer();
        }
        BankAccount receiverAccount = users.get(receiver);
        println("Amount to send: ");
        BigDecimal amount = ScannerUtils.nextBigDecimal();
        if(currentUser.removeBalance(amount) && receiverAccount != null) {
            receiverAccount.addBalance(amount);
            println("\nSuccess! " + amount + CURRENCY_SYMBOL + " have beent sent to " + receiver + "!");
            println("Press any key to continue.");
        } else {
            println("\nNot enough balance!\nPress any key to continue.");
        }
        Utils.pause();
        showMenu(MenuPage.MENU_LOGGEDIN);
    }

    private void menuPasswordChange() {
        if(currentUser == null)
            showMenu(MenuPage.MAIN);
        Utils.clearConsole();

        println("Password change\n");
        print("Old password: ");
        String inputOldPassword = ScannerUtils.nextLine();
        if(!currentUser.checkPassword(inputOldPassword)) {
            println("\nWrong password! Press any key to continue...");
            Utils.pause();
            showMenu(MenuPage.MENU_LOGGEDIN);
        }
        print("New password: ");
        String newPassword = ScannerUtils.nextLine();

        if (!isValidPassword(newPassword)) {
            println("\n" + invalidPasswordMessage());
            println("Press any key to continue.");
            Utils.pause();
            showMenu(MenuPage.MENU_LOGGEDIN);
        }

        currentUser.setPasswordHash(HashUtils.hash(newPassword));
        println("\nDone! Press any key to continue.");
        Utils.pause();
        showMenu(MenuPage.MENU_LOGGEDIN);
    }

    private void menuWithdraw() {
        if(currentUser == null)
            showMenu(MenuPage.MAIN);
        Utils.clearConsole();
        println("Current balance: " + currentUser.getBalanceString() + CURRENCY_SYMBOL + "\n");
        print("Withdraw amount: ");
        BigDecimal input = ScannerUtils.nextBigDecimal();
        if(currentUser.removeBalance(input))
            println("\n" + input + CURRENCY_SYMBOL + " have been taken from your account!\nRemaining balance: " + currentUser.getBalanceString() + CURRENCY_SYMBOL + ".\nPress any key to continue.");
        else
            System.out.println("\nNot enough balance in your account!\nPress any key to continue.");
        Utils.pause();
        showMenu(MenuPage.MENU_LOGGEDIN);
    }

    private void menuDeposit() {
        if(currentUser == null)
            showMenu(MenuPage.MAIN);
        Utils.clearConsole();
        println("Current balance: " + currentUser.getBalanceString() + CURRENCY_SYMBOL + "\n");
        print("Deposit amount: ");
        BigDecimal input = ScannerUtils.nextBigDecimal();
        currentUser.addBalance(input);
        println("\n" + Utils.formatMoney(input) + CURRENCY_SYMBOL + " have been added to your account.\nPress any key to continue.");
        Utils.pause();
        showMenu(MenuPage.MENU_LOGGEDIN);
    }

    private void menuRegister() {
        Utils.clearConsole();
        println("Register (Write !q to return)\n");
        print("Username: ");
        String inputUsername = ScannerUtils.nextLine();
        if(inputUsername.equals("!q"))
            showMenu(MenuPage.MAIN);
        if(users.containsKey(inputUsername.toLowerCase())) {
            println("Username already taken! Please choose another one! Press any key to restart.");
            Utils.pause();
            menuRegister();
        }

        print("Password: ");
        String inputPassword = ScannerUtils.nextLine();
        if(!isValidPassword(inputPassword)) {
            println("\n" + invalidPasswordMessage());
            println("Press any key to continue.");
            Utils.pause();
            menuRegister();
        }

        BankAccount newAccount = new BankAccount(inputUsername, HashUtils.hash(inputPassword), new BigDecimal(0));
        users.put(inputUsername.toLowerCase(), newAccount);
        currentUser = newAccount;
        showMenu(MenuPage.MENU_LOGGEDIN);
    }

    private void menuLogin() {
        Utils.clearConsole();
        println("Login (Write !q to return)\n");
        print("Username: ");
        String inputUsername = ScannerUtils.nextLine();
        if(inputUsername.equals("!q"))
            showMenu(MenuPage.MAIN);
        print("Password: ");
        String inputPassword = ScannerUtils.nextLine();

        String idUsername = inputUsername.toLowerCase();
        if(users.containsKey(idUsername)) {
            BankAccount user = users.get(idUsername);
            if(user.checkPassword(inputPassword)) {
                currentUser = user;
                showMenu(MenuPage.MENU_LOGGEDIN);
            }
        }
        println("Wrong username or password! Press any key to continue!");
        Utils.pause();
        showMenu(MenuPage.MAIN);
    }

    private void menuMain() {
        if(currentUser != null)
            showMenu(MenuPage.MENU_LOGGEDIN);

        Utils.clearConsole();
        println("BankSystem by SvenWollinger\n");
        println("1] Register");
        println("2] Login");
        println("3] Exit");
        final int input = ScannerUtils.nextInt();
        switch(input) {
            case 1: showMenu(MenuPage.REGISTER); break;
            case 2: showMenu(MenuPage.LOGIN); break;
            case 3: System.exit(0);
            default: menuMain();
        }
    }

    private void menuLoggedIn() {
        if(currentUser == null)
            showMenu(MenuPage.MAIN);

        Utils.clearConsole();
        println("Welcome " + currentUser.getUsername() + "!\n");
        println("Current balance: " + currentUser.getBalanceString() + CURRENCY_SYMBOL);
        println("1] Withdraw");
        println("2] Deposit");
        println("3] Transfer");
        println("4] Change password");
        println("5] Logout");
        println("6] Delete Account");
        final int input = ScannerUtils.nextInt();
        switch(input) {
            case 1: showMenu(MenuPage.WITHDRAW); break;
            case 2: showMenu(MenuPage.DEPOSIT); break;
            case 3: showMenu(MenuPage.TRANSFER); break;
            case 4: showMenu(MenuPage.PASSWD_CHANGE); break;
            case 5: logout(); break;
            case 6: delete(); break;
            default: menuLoggedIn();
        }
    }

    private void logout() {
        currentUser = null;
        showMenu(MenuPage.MAIN);
    }

    private void delete() {
        if(currentUser != null) {
            users.remove(currentUser.getUsername().toLowerCase());
            currentUser = null;
        }
        showMenu(MenuPage.MAIN);
    }

    private String invalidPasswordMessage() {
        return "Password needs to be atleast " + MIN_PASS_LENGTH + " characters and contain atleast one of these special characters: " + Arrays.toString(SPECIAL_CHARS) + "!";
    }

    private boolean isValidPassword(String password) {
        if(password.length() < MIN_PASS_LENGTH)
            return false;
        boolean containsSpecialChar = false;
        for(char c : SPECIAL_CHARS) {
            if(password.contains(c + "")) {
                containsSpecialChar = true;
                break;
            }
        }
        return containsSpecialChar;
    }

    private void println(String string) {
        System.out.println(string);
    }

    private void print(String string) {
        System.out.print(string);
    }
}
