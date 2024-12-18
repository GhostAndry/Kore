package me.ghostdevelopment.kore.files;

import me.ghostdevelopment.kore.Kore;
import org.bukkit.configuration.file.FileConfiguration;

public class SettingsFile {

    private static final Kore main = Kore.getInstance();

    public static void setUp() {
        main.getConfig().options().copyDefaults(true);
        save();
    }

    public static FileConfiguration getFile() {
        return main.getConfig();
    }

    public static void reload() {
        main.reloadConfig();
    }

    public static void save() {
        main.saveDefaultConfig();
    }
}
