package io.wollinger.banksystem.utils;

import java.io.IOException;

public class Utils {
    private Utils() { }

    public static void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
