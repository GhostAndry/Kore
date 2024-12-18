package me.ghostdevelopment.kore.events;

import me.ghostdevelopment.kore.files.SettingsFile;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import java.util.logging.Logger;

public class WorldManipulator implements Listener {

    private static final Logger logger = Bukkit.getLogger();

    @Getter @Setter
    private static boolean blacklist;
    @Getter
    private static final List<World> worlds = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerHunger(FoodLevelChangeEvent event) {
        logger.info("[WorldManipulator] Handling FoodLevelChangeEvent for world: " + event.getEntity().getWorld().getName());
        if (blacklist) {
            if (worlds.contains(event.getEntity().getWorld())) {
                logger.info("[WorldManipulator] World is blacklisted, skipping.");
                return;
            }
        } else {
            if (worlds.contains(event.getEntity().getWorld())) {
                if (SettingsFile.getFile().getBoolean("world-manipulator.enable")) {
                    if (SettingsFile.getFile().getBoolean("world-manipulator.rules.anti-hunger")) {
                        if (event.getFoodLevel() < 20) {
                            event.setFoodLevel(20);
                            logger.info("[WorldManipulator] Anti-hunger applied. Food level set to 20.");
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onWeatherChange(WeatherChangeEvent event) {
        logger.info("[WorldManipulator] Handling WeatherChangeEvent for world: " + event.getWorld().getName());
        if (blacklist) {
            if (worlds.contains(event.getWorld())) {
                logger.info("[WorldManipulator] World is blacklisted, skipping.");
                return;
            }
        } else {
            if (worlds.contains(event.getWorld())) {
                if (SettingsFile.getFile().getBoolean("world-manipulator.enable")) {
                    if (SettingsFile.getFile().getBoolean("world-manipulator.rules.anti-weather")) {
                        event.setCancelled(true);
                        logger.info("[WorldManipulator] Weather change cancelled.");
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        logger.info("[WorldManipulator] Handling PlayerJoinEvent for player: " + event.getPlayer().getName());
        if (blacklist) {
            if (worlds.contains(event.getPlayer().getWorld())) {
                logger.info("[WorldManipulator] World is blacklisted, skipping.");
                return;
            }
        } else {
            if (worlds.contains(event.getPlayer().getWorld())) {
                if (SettingsFile.getFile().getBoolean("world-manipulator.enable")) {
                    if (SettingsFile.getFile().getBoolean("world-manipulator.rules.anti-join-message")) {
                        event.setJoinMessage(null);
                        logger.info("[WorldManipulator] Join message cleared.");
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        logger.info("[WorldManipulator] Handling PlayerQuitEvent for player: " + event.getPlayer().getName());
        if (blacklist) {
            if (worlds.contains(event.getPlayer().getWorld())) {
                logger.info("[WorldManipulator] World is blacklisted, skipping.");
                return;
            }
        } else {
            if (worlds.contains(event.getPlayer().getWorld())) {
                if (SettingsFile.getFile().getBoolean("world-manipulator.enable")) {
                    if (SettingsFile.getFile().getBoolean("world-manipulator.rules.anti-join-message")) {
                        event.setQuitMessage(null);
                        logger.info("[WorldManipulator] Quit message cleared.");
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobSpawn(EntitySpawnEvent event) {
        logger.info("[WorldManipulator] Handling EntitySpawnEvent for entity: " + event.getEntityType() + " in world: " + event.getEntity().getWorld().getName());
        if (blacklist) {
            if (worlds.contains(event.getEntity().getWorld())) {
                logger.info("[WorldManipulator] World is blacklisted, skipping.");
                return;
            }
        } else {
            if (worlds.contains(event.getEntity().getWorld())) {
                if (SettingsFile.getFile().getBoolean("world-manipulator.enable")) {
                    if (SettingsFile.getFile().getBoolean("world-manipulator.rules.anti-mob-spawn")) {
                        if (event.getEntityType().equals(EntityType.FALLING_BLOCK)
                                || event.getEntityType().equals(EntityType.ITEM_FRAME)
                                || event.getEntityType().equals(EntityType.ARMOR_STAND)
                                || event.getEntityType().equals(EntityType.ARROW)
                                || event.getEntityType().equals(EntityType.SNOWBALL)
                                || event.getEntityType().equals(EntityType.EGG)
                                || event.getEntityType().equals(EntityType.FIREBALL)
                                || event.getEntityType().equals(EntityType.SMALL_FIREBALL)
                                || event.getEntityType().equals(EntityType.ENDER_PEARL)
                                || event.getEntityType().equals(EntityType.WITHER_SKULL)
                                || event.getEntityType().equals(EntityType.MINECART)) {
                            logger.info("[WorldManipulator] Entity type is exempt, skipping.");
                            return;
                        }
                        event.setCancelled(true);
                        logger.info("[WorldManipulator] Mob spawn cancelled.");
                    }
                }
            }
        }
    }
}
