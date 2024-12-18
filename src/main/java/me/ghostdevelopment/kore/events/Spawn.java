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

    private static final Logger logger = Bukkit.getLogger();
    private static final int defaultY = Kore.calculateY();

    @Getter @Setter
    private static boolean blacklist;
    @Getter
    private static final List<World> worlds = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        logger.info("[Spawn] Handling PlayerJoinEvent for player: " + event.getPlayer().getName());

        if (blacklist) {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                logger.info("[Spawn] World is blacklisted, skipping.");
                return;
            }
        } else {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                logger.info("[Spawn] Player is in a managed world. Checking conditions...");
                if (isSpawnConfigured() && SettingsFile.getFile().getBoolean("spawn.enabled")) {
                    if (SettingsFile.getFile().getBoolean("spawn.on-join")) {
                        logger.info("[Spawn] Teleporting player to spawn.");
                        event.getPlayer().teleport(Functions.getSpawnLocation());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerRespawnEvent event) {
        logger.info("[Spawn] Handling PlayerRespawnEvent for player: " + event.getPlayer().getName());

        if (blacklist) {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                logger.info("[Spawn] World is blacklisted, skipping.");
                return;
            }
        } else {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                logger.info("[Spawn] Player is in a managed world. Checking conditions...");
                if (isSpawnConfigured() && SettingsFile.getFile().getBoolean("spawn.enabled")) {
                    if (SettingsFile.getFile().getBoolean("spawn.on-death")) {
                        logger.info("[Spawn] Teleporting player to spawn on death.");
                        event.getPlayer().teleport(Functions.getSpawnLocation());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerVoid(PlayerMoveEvent event) {
        logger.info("[Spawn] Handling PlayerMoveEvent for player: " + event.getPlayer().getName());

        if (blacklist) {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                logger.info("[Spawn] World is blacklisted, skipping.");
                return;
            }
        } else {
            if (getWorlds().contains(event.getPlayer().getWorld())) {
                logger.info("[Spawn] Player is in a managed world. Checking conditions...");
                if (isSpawnConfigured() && SettingsFile.getFile().getBoolean("spawn.enabled")) {
                    if (SettingsFile.getFile().getBoolean("spawn.on-void")) {
                        if (event.getPlayer().getLocation().getY() < defaultY) {
                            logger.info("[Spawn] Teleporting player to spawn due to void.");
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

        logger.info("[Spawn] Spawn configuration status: " + configured);
        return configured;
    }
}
