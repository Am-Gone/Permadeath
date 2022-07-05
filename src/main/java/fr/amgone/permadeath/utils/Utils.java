package fr.amgone.permadeath.utils;

public class Utils {
    public static boolean hasDaysPassed(int daysAmount) {
        return (System.currentTimeMillis() - GameFileConfig.startTime) >= (long) daysAmount * 24 * 60 * 60 * 1000;
    }

    public static int getDaysPassed() {
        return (int) ((System.currentTimeMillis() - GameFileConfig.startTime) / 1000 / 60 / 60 / 24);
    }
}
