package io.wollinger.banksystem;

import io.wollinger.banksystem.utils.HashUtils;
import io.wollinger.banksystem.utils.ScannerUtils;
import io.wollinger.banksystem.utils.Utils;

import java.math.BigDecimal;
import java.util.HashMap;

public class BankSystem {
    private BankAccount currentUser;
    private HashMap<String, BankAccount> users = new HashMap<>();

    private final static int MIN_PASS_LENGTH = 4;

    enum MenuPage { MAIN, MENU_LOGGEDIN, REGISTER, LOGIN, WITHDRAW, DEPOSIT}

    public BankSystem() {

    }

    public void showMenu(MenuPage page) {
        switch(page) {
            case MAIN: menuMain();
            case MENU_LOGGEDIN: menuLoggedIn();
            case REGISTER: menuRegister();
            case LOGIN: menuLogin();
        }
    }

    private void menuRegister() {
        Utils.clearConsole();
        println("Register\n");
        print("Username: ");
        String inputUsername = ScannerUtils.nextLine();
        if(users.containsKey(inputUsername)) {
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
        users.put(inputUsername, newAccount);
        currentUser = newAccount;
        showMenu(MenuPage.MENU_LOGGEDIN);
    }

    private void menuLogin() {
        Utils.clearConsole();
        println("Login\n");
        print("Username: ");
        String inputUsername = ScannerUtils.nextLine();
        print("Password: ");
        String inputPassword = ScannerUtils.nextLine();

        if(users.containsKey(inputUsername)) {
            if(HashUtils.authenticate(inputPassword, users.get(inputUsername).getPasswordHash())) {
                currentUser = users.get(inputUsername);
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
        println("Current balance: " + currentUser.getBalance());
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
        //TODO: Add code
    }

    private void println(String string) {
        System.out.println(string);
    }

    private void print(String string) {
        System.out.print(string);
    }
}
