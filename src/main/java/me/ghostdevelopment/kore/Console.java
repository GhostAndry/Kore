package me.ghostdevelopment.kore;

import org.bukkit.Bukkit;

public class Console {

    public static void info(String msg){Bukkit.getLogger().info(Utils.Color(msg));}
    public static void warning(String msg){Bukkit.getLogger().warning(Utils.Color(msg));}


}
