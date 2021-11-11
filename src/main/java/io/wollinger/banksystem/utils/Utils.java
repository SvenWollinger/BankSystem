package io.wollinger.banksystem.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Utils {
    private Utils() { }

    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void pause() {
        try {
            new ProcessBuilder("cmd", "/c", "pause>nul").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static String formatMoney(BigDecimal money) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(money);
    }
}
