package fr.amgone.permadeath.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class DaysListener extends BukkitRunnable {
    private int lastDay = Utils.getDaysPassed();

    @Override
    public void run() {
        if(Utils.hasDaysPassed(7) && lastDay < 7) {
            Bukkit.resetRecipes();
        }
    }
}
