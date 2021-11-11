package io.wollinger.banksystem.utils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class ScannerUtils {
    private static Scanner scanner = null;

    private static void ensureScanner() {
        if(scanner == null)
            scanner = new Scanner(System.in);
    }

    public static void setStream(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    public static String nextLine() {
        ensureScanner();
        return scanner.nextLine();
    }

    public static String next() {
        ensureScanner();
        return scanner.next();
    }

    public static int nextInt() {
        ensureScanner();
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    public static short nextShort() {
        ensureScanner();
        short input = scanner.nextShort();
        scanner.nextLine();
        return input;
    }

    public static boolean nextBoolean() {
        ensureScanner();
        boolean input = scanner.nextBoolean();
        scanner.nextLine();
        return input;
    }

    public static float nextFloat() {
        ensureScanner();
        float input = scanner.nextFloat();
        scanner.nextLine();
        return input;
    }

    public static double nextDouble() {
        ensureScanner();
        double input = scanner.nextDouble();
        scanner.nextLine();
        return input;
    }

    public static long nextLong() {
        ensureScanner();
        long input = scanner.nextLong();
        scanner.nextLine();
        return input;
    }

    public static BigDecimal nextBigDecimal() {
        ensureScanner();
        BigDecimal input = scanner.nextBigDecimal();
        scanner.nextLine();
        return input;
    }

    public static BigInteger nextBigInteger() {
        ensureScanner();
        BigInteger input = scanner.nextBigInteger();
        scanner.nextLine();
        return input;
    }

    public static byte nextByte() {
        ensureScanner();
        byte input = scanner.nextByte();
        scanner.nextLine();
        return input;
    }
}
