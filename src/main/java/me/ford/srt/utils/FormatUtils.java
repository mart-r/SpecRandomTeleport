package me.ford.srt.utils;

public final class FormatUtils {

    private FormatUtils() {
        throw new IllegalStateException("Utility classes should not be instantiated");
    }

    public static String formatDouble(double d) {
        return String.format("%5.2f", d);
    }

}