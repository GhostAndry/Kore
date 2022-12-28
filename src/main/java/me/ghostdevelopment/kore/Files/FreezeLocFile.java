package me.ghostdevelopment.kore.Files;

import me.ghostdevelopment.kore.Console;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FreezeLocFile {

    private static File file;
    private static FileConfiguration freezeLocFile;

    public static void setUp(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Kore").getDataFolder(), "freezeLoc.yml");
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
            Console.warning("&cCould not write on warps.yml file.");
        }
    }

}
