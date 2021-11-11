package io.wollinger.banksystem;

import io.wollinger.banksystem.utils.ScannerUtils;
import io.wollinger.banksystem.utils.Utils;

import java.util.ArrayList;

public class BankSystem {
    private BankAccount currentUser;
    private ArrayList<BankAccount> users = new ArrayList<>();

    enum MenuPage { MAIN, MENU_LOGGEDIN, REGISTER, LOGIN, WITHDRAW, DEPOSIT}

    public BankSystem() {

    }

    public void showMenu(MenuPage page) {
        switch(page) {
            case MAIN: menuMain();
            case MENU_LOGGEDIN: menuLoggedIn();
        }
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
        println("Welcome " + currentUser.getUsername() + "\n");
        println("1] Withdraw");
        println("2] Deposit");
        println("3] Logout");
        println("4] Delete Account");
        final int input = ScannerUtils.nextInt();
        switch(input) {
            case 1: showMenu(MenuPage.WITHDRAW); break;
            case 2: showMenu(MenuPage.DEPOSIT); break;
            case 3: currentUser = null; showMenu(MenuPage.MAIN);
            case 4: break; //TODO: Delete
            default: menuLoggedIn();
        }
    }

    private void println(String string) {
        System.out.println(string);
    }

    private void print(String string) {
        System.out.print(string);
    }
}
