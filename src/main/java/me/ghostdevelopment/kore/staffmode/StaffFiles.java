package me.ghostdevelopment.kore.staffmode;

import me.ghostdevelopment.kore.Console;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StaffFiles {
    private static File file;
    private static FileConfiguration freezeLocFile;

    public static void setUp(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Kore").getDataFolder(), "staffMode.yml");
        if(!(file.exists())){
            try {
                file.createNewFile();
            }catch (IOException e){
            }
        }
        freezeLocFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){return freezeLocFile;}
    public static void reload(){freezeLocFile=YamlConfiguration.loadConfiguration(file);}
    public static void save(){
        try {
            freezeLocFile.save(file);
        }catch (Exception e){
            Console.warning("&cCould not write on staffMode.yml file.");
        }
    }
}
