package fr.amgone.permadeath.utils;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GameFileConfig {
    private static final File configFile = new File("/opt/permadeath/config.bin");
    private static RandomAccessFile file;

    public static long startTime = System.currentTimeMillis();

    public static void readFile() throws IOException {
        if(configFile.createNewFile()) {
            Bukkit.getLogger().warning("The config file has been created.");
            file = new RandomAccessFile(configFile, "rw");
            return;
        } else {
            file = new RandomAccessFile(configFile, "rw");
        }

        startTime = System.currentTimeMillis() - file.readLong();
    }

    public static void saveFile() throws IOException {
        configFile.delete();
        configFile.createNewFile();
        file = new RandomAccessFile(configFile, "rw");

        file.writeLong(System.currentTimeMillis() - startTime);

        file.close();
    }
}
