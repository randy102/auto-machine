package com.utils;

public class Screen {
    public static void clear() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
            write("\033[H\033[2J");
            System.out.flush();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeln(String message) {
        System.out.println(message);
    }

    public static void write(String message) {
        System.out.print(message);
    }
}
