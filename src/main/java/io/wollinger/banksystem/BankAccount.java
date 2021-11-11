package io.wollinger.banksystem;

import java.math.BigDecimal;

public class BankAccount {
    private String username;
    private String pinHash;
    private BigDecimal balance;

    public BankAccount (String username, String pinHash, BigDecimal balance) {
        this.username = username;
        this.pinHash = pinHash;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return username + " " + pinHash + " " + balance;
    }
}
