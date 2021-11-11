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

    public String getPasswordHash() {
        return pinHash;
    }

    public void addBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void removeBalance(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return username + " " + pinHash + " " + balance;
    }
}
