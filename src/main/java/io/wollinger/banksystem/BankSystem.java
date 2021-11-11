package io.wollinger.banksystem;

import io.wollinger.banksystem.utils.HashUtils;
import io.wollinger.banksystem.utils.ScannerUtils;
import io.wollinger.banksystem.utils.Utils;

import java.math.BigDecimal;
import java.util.HashMap;

public class BankSystem {
    private BankAccount currentUser;
    private final HashMap<String, BankAccount> users = new HashMap<>();

    private final static int MIN_PASS_LENGTH = 4;
    private final static String CURRENCY_SYMBOL = "$";

    enum MenuPage { MAIN, MENU_LOGGEDIN, REGISTER, LOGIN, WITHDRAW, DEPOSIT}

    public BankSystem() {

    }

    public void showMenu(MenuPage page) {
        switch(page) {
            case MAIN: menuMain();
            case MENU_LOGGEDIN: menuLoggedIn();
            case REGISTER: menuRegister();
            case LOGIN: menuLogin();
            case WITHDRAW: menuWithdraw();
            case DEPOSIT: menuDeposit();
        }
    }

    private void menuWithdraw() {
        if(currentUser == null)
            showMenu(MenuPage.MAIN);
        Utils.clearConsole();
        println("Current balance: " + currentUser.getBalance() + CURRENCY_SYMBOL + "\n");
        print("Withdraw amount: ");
        BigDecimal input = ScannerUtils.nextBigDecimal();
        switch(currentUser.getBalance().compareTo(input)) {
            case 0:
            case 1:
                currentUser.removeBalance(input);
                println("\n" + input + CURRENCY_SYMBOL + " have been taken from your account!\nRemaining balance: " + currentUser.getBalance() + CURRENCY_SYMBOL + ".\nPress any key to continue.");
                break;
            case -1:
            case 3:
                System.out.println("\nNot enough balance in your account!\nPress any key to continue.");
                break;
        }
        Utils.pause();
        showMenu(MenuPage.MENU_LOGGEDIN);
    }

    private void menuDeposit() {
        if(currentUser == null)
            showMenu(MenuPage.MAIN);
        Utils.clearConsole();
        println("Current balance: " + currentUser.getBalance() + CURRENCY_SYMBOL + "\n");
        print("Deposit amount: ");
        BigDecimal input = ScannerUtils.nextBigDecimal();
        currentUser.addBalance(input);
        println("\n" + input + CURRENCY_SYMBOL + " have been added to your account.\nPress any key to continue.");
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
        if(inputPassword.length() < MIN_PASS_LENGTH) {
            println("Password needs to be atleast " + MIN_PASS_LENGTH + " characters! Press any key to restart.");
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
            if(HashUtils.authenticate(inputPassword, user.getPasswordHash())) {
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
        final int input = ScannerUtils.nextInt();
        switch(input) {
            case 1: showMenu(MenuPage.REGISTER); break;
            case 2: showMenu(MenuPage.LOGIN); break;
            default: menuMain();
        }
    }

    private void menuLoggedIn() {
        if(currentUser == null)
            showMenu(MenuPage.MAIN);

        Utils.clearConsole();
        println("Welcome " + currentUser.getUsername() + "!\n");
        println("Current balance: " + currentUser.getBalance() + CURRENCY_SYMBOL);
        println("1] Withdraw");
        println("2] Deposit");
        println("3] Logout");
        println("4] Delete Account");
        final int input = ScannerUtils.nextInt();
        switch(input) {
            case 1: showMenu(MenuPage.WITHDRAW); break;
            case 2: showMenu(MenuPage.DEPOSIT); break;
            case 3: logout(); break;
            case 4: delete(); break;
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

    private void println(String string) {
        System.out.println(string);
    }

    private void print(String string) {
        System.out.print(string);
    }
}
