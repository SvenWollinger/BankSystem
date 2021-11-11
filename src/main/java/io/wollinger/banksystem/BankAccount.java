package io.wollinger.banksystem;

import java.math.BigDecimal;

public class BankAccount {
    private String username;
    private String pinHash;
    private BigDecimal money;

    public BankAccount (String username, String pinHash, BigDecimal money) {
        this.username = username;
        this.pinHash = pinHash;
        this.money = money;
    }

    @Override
    public String toString() {
        return username + " " + pinHash + " " + money;
    }
}
