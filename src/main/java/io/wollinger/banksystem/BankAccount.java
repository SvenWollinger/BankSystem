package io.wollinger.banksystem;

import io.wollinger.banksystem.utils.HashUtils;
import io.wollinger.banksystem.utils.Utils;

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

    public boolean checkPassword(String password) {
        return HashUtils.authenticate(password, pinHash);
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

    public String getBalanceString() {
        return Utils.formatMoney(balance);
    }

    @Override
    public String toString() {
        return username + " " + pinHash + " " + balance;
    }
}
