package io.wollinger.banksystem.utils;

import io.wollinger.banksystem.BankAccount;

import java.sql.*;
import java.util.ArrayList;

public class DBUtils {

    public static void init() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = getConnection();
            if(connection != null) {
                Statement stmntCreateTables = connection.createStatement();
                stmntCreateTables.executeUpdate("CREATE TABLE IF NOT EXISTS accounts (username VARCHAR(255) PRIMARY KEY UNIQUE, passwordHash VARCHAR(255), balance DECIMAL(130, 128));");
                connection.close();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public static ArrayList<BankAccount> getAccountsFromDB() {
        ArrayList<BankAccount> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM accounts;";
            Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                list.add(new BankAccount(rs.getString("username"), rs.getString("passwordHash"), rs.getBigDecimal("balance")));
            }
            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:data.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
