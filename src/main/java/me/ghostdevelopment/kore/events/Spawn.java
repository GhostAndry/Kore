package me.ghostdevelopment.kore.events;

import lombok.Getter;
import lombok.Setter;
import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.files.StorageFile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Spawn implements Listener {

    private static final int defaultY = Kore.calculateY();

    @Getter @Setter
    private static boolean blacklist;
    @Getter
    private static final List<World> worlds = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (blacklist) {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                return;
            }
        } else {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                if (isSpawnConfigured() && SettingsFile.getFile().getBoolean("spawn.enabled")) {
                    if (SettingsFile.getFile().getBoolean("spawn.on-join")) {
                        event.getPlayer().teleport(Functions.getSpawnLocation());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerRespawnEvent event) {

        if (blacklist) {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                return;
            }
        } else {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                if (isSpawnConfigured() && SettingsFile.getFile().getBoolean("spawn.enabled")) {
                    if (SettingsFile.getFile().getBoolean("spawn.on-death")) {
                        event.getPlayer().teleport(Functions.getSpawnLocation());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerVoid(PlayerMoveEvent event) {

        if (blacklist) {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                return;
            }
        } else {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                if (isSpawnConfigured() && SettingsFile.getFile().getBoolean("spawn.enabled")) {
                    if (SettingsFile.getFile().getBoolean("spawn.on-void")) {
                        if (event.getPlayer().getLocation().getY() < defaultY) {
                            event.getPlayer().teleport(Functions.getSpawnLocation());
                        }
                    }
                }
            }
        }
    }

    private boolean isSpawnConfigured() {
        boolean configured = StorageFile.getFile().contains("spawn.world")
                && StorageFile.getFile().contains("spawn.x")
                && StorageFile.getFile().contains("spawn.y")
                && StorageFile.getFile().contains("spawn.z")
                && StorageFile.getFile().contains("spawn.yaw")
                && StorageFile.getFile().contains("spawn.pitch");

        return configured;
    }
}
