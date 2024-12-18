package me.ghostdevelopment.kore;

import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.files.StorageFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class Functions {

    private static final String SPAWN_PATH = "spawn.";
    private static final String WARPS_PATH = "warps.";
    private static final String HOMES_PATH = "homes.";
    private static final String HOLOGRAMS_PATH = "holograms.";

    public static void reloadFiles() {
        Bukkit.getScheduler().runTask(Kore.getInstance(), () -> {
            Bukkit.getPluginManager().disablePlugin(Kore.getInstance());
            Bukkit.getPluginManager().enablePlugin(Kore.getInstance());
        });
        SettingsFile.reload();
        LangFile.reload();
        StorageFile.reload();
    }

    public static void setSpawnLoc(Location loc) {
        setLocation(SPAWN_PATH, loc);
    }

    public static Location getSpawnLocation() {
        return getLocation(SPAWN_PATH);
    }

    public static void addWarp(Location location, String warpName) {
        setLocation(WARPS_PATH + "." + warpName.toLowerCase(), location);
    }

    public static Location getWarpLoc(String warpName) {
        return getLocation(WARPS_PATH + "." + warpName.toLowerCase());
    }

    public static void delWarp(String warpName) {
        deleteLocation(WARPS_PATH + "." + warpName.toLowerCase());
    }

    public static boolean checkWarp(String warpName) {
        return StorageFile.getFile().contains(WARPS_PATH + "." + warpName.toLowerCase());
    }

    public static void addHome(Player player) {
        setLocation(HOMES_PATH + "." + player.getName().toLowerCase(), player.getLocation());
    }

    public static void delHome(Player player) {
        deleteLocation(HOMES_PATH + "." + player.getName().toLowerCase());
    }

    public static Location getHomeLoc(Player player) {
        return getLocation(HOMES_PATH + "." + player.getName().toLowerCase());
    }

    public static boolean checkHome(Player player) {
        return StorageFile.getFile().contains(HOMES_PATH + "." + player.getName().toLowerCase());
    }

    public static void setSpeed(Player player, String type, float speed) {
        final float defaultSpeed = type=="fly"||type=="flight" ? 0.1f : 0.2f;
        float maxSpeed = 1f;

        final float ratio = ((speed - 1) / 9) * (maxSpeed - defaultSpeed);

        if ("walk".equalsIgnoreCase(type)) {
            player.setWalkSpeed(ratio+defaultSpeed);
        } else if ("fly".equalsIgnoreCase(type) || "flight".equalsIgnoreCase(type)) {
            player.setFlySpeed(ratio+defaultSpeed);
        }
    }

    public static void addHologram(String name, Location location) {
        String path = HOLOGRAMS_PATH + name + ".";
        setLocation(path, location);
        StorageFile.getFile().set(path + "lines", new ArrayList<String>());
        StorageFile.save();
    }

    public static void addLines(String name, List<String> lines) {
        String path = HOLOGRAMS_PATH + name + ".";
        List<String> currentLines = StorageFile.getFile().getStringList(path + "lines");
        currentLines.addAll(lines);
        StorageFile.getFile().set(path + "lines", currentLines);
        StorageFile.save();
    }

    public static void removeHolo(String name) {
        String path = HOLOGRAMS_PATH + name;
        StorageFile.getFile().set(path, null);
        StorageFile.save();
    }

    public static void removeLine(String name, int line) {
        String path = HOLOGRAMS_PATH + name + ".lines";
        List<String> lines = StorageFile.getFile().getStringList(path);
        if (line >= 0 && line < lines.size()) {
            lines.remove(line);
            StorageFile.getFile().set(path, lines);
            StorageFile.save();
        } else {
            System.out.println("Invalid line number. The line does not exist.");
        }
    }

    // Helper methods

    private static void setLocation(String path, Location loc) {
        StorageFile.getFile().set(path + "." + "world", loc.getWorld().getName());
        StorageFile.getFile().set(path + "." + "x", loc.getX());
        StorageFile.getFile().set(path + "." + "y", loc.getY());
        StorageFile.getFile().set(path + "." + "z", loc.getZ());
        StorageFile.getFile().set(path + "." + "pitch", loc.getPitch());
        StorageFile.getFile().set(path + "." + "yaw", loc.getYaw());
        StorageFile.save();
    }

    private static Location getLocation(String path) {
        return new Location(
                Bukkit.getWorld(StorageFile.getFile().getString(path + "." + "world")),
                StorageFile.getFile().getDouble(path + "." + "x"),
                StorageFile.getFile().getDouble(path + "." + "y"),
                StorageFile.getFile().getDouble(path + "." + "z"),
                (float) StorageFile.getFile().getDouble(path + "." + "yaw"),
                (float) StorageFile.getFile().getDouble(path + "." + "pitch")
        );
    }

    private static void deleteLocation(String path) {
        StorageFile.getFile().set(path, null);
        StorageFile.save();
    }
}
