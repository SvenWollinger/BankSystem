package io.wollinger.banksystem;

public class Main {
    public static final boolean DEBUG = false;
    public static void main(String[] args) {
        new BankSystem().showMenu(BankSystem.MenuPage.MAIN);
    }
}
