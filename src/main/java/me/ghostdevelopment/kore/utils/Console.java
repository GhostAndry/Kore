package me.ghostdevelopment.kore.utils;

import org.bukkit.Bukkit;

@SuppressWarnings({"ALL"})
public class Console {

    public static void info(String msg) {
        Bukkit.getLogger().info(Color.Color(msg));
    }

    public static void warning(String msg) {
        Bukkit.getLogger().warning(Color.Color(msg));
    }


}
