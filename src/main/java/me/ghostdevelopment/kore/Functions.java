package me.ghostdevelopment.kore;

import me.ghostdevelopment.kore.Files.FreezeLocFile;
import me.ghostdevelopment.kore.Files.HomesFile;
import me.ghostdevelopment.kore.Files.SpawnFile;
import me.ghostdevelopment.kore.Files.WarpsFile;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

@SuppressWarnings({"DataFlowIssue", "unused"})
public class Functions {

    private static Kore plugin;

    public static void sendMessage(Player player, String msg){
        player.sendMessage(Utils.Color(msg));
    }
    public static void sendConfigMessage(Player player, String path){
        player.sendMessage(Utils.Color(plugin.getConfig().getString(path)
                .replace("%prefix%", plugin.getConfig().getString("messages.prefix"))
        ));
    }

    public static void setHeal(Player player){
        player.setHealth(20);
        player.setFoodLevel(30);
        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());
    }
    public static void setSpeed(Player player, String type, Float speed){

        if(type.equals("walk")){
            player.setWalkSpeed(speed / 5.0f);
        } else if (type.equals("fly") || type.equals("flight")) {
            player.setFlySpeed(speed / 5.0f);
        }

    }

    public static void addWarp(String name, Location loc){
        name = name.toLowerCase();

        WarpsFile.get().set(name + ".world", loc.getWorld().getName());
        WarpsFile.get().set(name + ".x", loc.getX());
        WarpsFile.get().set(name + ".y", loc.getY());
        WarpsFile.get().set(name + ".z", loc.getZ());
        WarpsFile.get().set(name + ".yaw", loc.getYaw());
        WarpsFile.get().set(name + ".pitch", loc.getPitch());
        WarpsFile.save();
    }
    public static void delWarp(String name){
        name = name.toLowerCase();

        WarpsFile.get().set(name + ".world", null);
        WarpsFile.get().set(name + ".x", null);
        WarpsFile.get().set(name + ".y", null);
        WarpsFile.get().set(name + ".z", null);
        WarpsFile.get().set(name + ".yaw", null);
        WarpsFile.get().set(name + ".pitch", null);
        WarpsFile.get().set(name, null);
        WarpsFile.save();
    }

    public static void setHome(String name, Location loc){
        name = name.toLowerCase();

        HomesFile.get().set(name + ".world", loc.getWorld().getName());
        HomesFile.get().set(name + ".x", loc.getX());
        HomesFile.get().set(name + ".y", loc.getY());
        HomesFile.get().set(name + ".z", loc.getZ());
        HomesFile.get().set(name + ".yaw", loc.getYaw());
        HomesFile.get().set(name + ".pitch", loc.getPitch());
        HomesFile.save();
    }
    public static void delHome(String name){
        name = name.toLowerCase();

        HomesFile.get().set(name + ".world", null);
        HomesFile.get().set(name + ".x", null);
        HomesFile.get().set(name + ".y", null);
        HomesFile.get().set(name + ".z", null);
        HomesFile.get().set(name + ".yaw", null);
        HomesFile.get().set(name + ".pitch", null);
        HomesFile.get().set(name, null);
        HomesFile.save();
    }

    public static void setFreezeLoc(String name, Location loc){
        name = name.toLowerCase();

        FreezeLocFile.get().set(name + ".world", loc.getWorld().getName());
        FreezeLocFile.get().set(name + ".x", loc.getX());
        FreezeLocFile.get().set(name + ".y", loc.getY());
        FreezeLocFile.get().set(name + ".z", loc.getZ());
        FreezeLocFile.get().set(name + ".yaw", loc.getYaw());
        FreezeLocFile.get().set(name + ".pitch", loc.getPitch());
        FreezeLocFile.save();
    }

    public static void setSpawnLoc(Location loc){
        String name = "spawn";

        SpawnFile.get().set(name + ".world", loc.getWorld().getName());
        SpawnFile.get().set(name + ".x", loc.getX());
        SpawnFile.get().set(name + ".y", loc.getY());
        SpawnFile.get().set(name + ".z", loc.getZ());
        SpawnFile.get().set(name + ".yaw", loc.getYaw());
        SpawnFile.get().set(name + ".pitch", loc.getPitch());
        SpawnFile.save();

    }

}
