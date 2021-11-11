package io.wollinger.banksystem;

import java.math.BigDecimal;

public class BankAccount {
    private final String username;
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

    public void setPasswordHash(String hash) {
        pinHash = hash;
    }

    public String getPasswordHash() {
        return pinHash;
    }

    public void addBalance(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public boolean removeBalance(BigDecimal amount) {
        switch(balance.compareTo(amount)) {
            case 0:
            case 1: balance = balance.subtract(amount); return true;
        }
        return false;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return username + " " + pinHash + " " + balance;
    }
}
