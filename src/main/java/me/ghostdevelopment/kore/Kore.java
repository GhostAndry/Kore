package me.ghostdevelopment.kore;

import lombok.Getter;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.commands.impl.fun.CommandSpawnmob;
import me.ghostdevelopment.kore.events.*;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.files.StorageFile;
import me.ghostdevelopment.kore.utils.Console;
import me.ghostdevelopment.kore.utils.Metrics;
import me.ghostdevelopment.kore.utils.RegisterPlaceholders;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public final class Kore extends JavaPlugin {

    @Getter
    private static Kore instance;

    public static int calculateY() {

        String serverVersion = Bukkit.getBukkitVersion();

        String[] versionParts = serverVersion.split("\\.");

        try {
            int majorVersion = Integer.parseInt(versionParts[0]);
            int minorVersion = Integer.parseInt(versionParts[1]);

            if (majorVersion == 1 && minorVersion >= 18) {
                return -64;
            } else {
                return 0;
            }
        } catch (NumberFormatException e) {
            Console.warning(e.getMessage());
            return 0;
        }
    }


    @Override
    public void onEnable() {
        instance = this;

        printBanner();

        Metrics metrics = new Metrics(this, 18653);
        metrics.addCustomChart(new Metrics.SimplePie("language", () -> SettingsFile.getFile().getString("messages")));

        try{
            Class.forName("me.clip.placeholderapi.expansion.PlaceholderExpansion");
            if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
                new RegisterPlaceholders().register();
            } else {
                Console.warning("PlaceholderAPI is absent in minecraft server. Placeholders won't work without it!");
            }
        } catch (ClassNotFoundException e) {
            Console.warning("PlaceholderAPI is absent in minecraft server. Placeholders won't work without it!");
        }

        setupFiles();
        addEntities();

        try {
            registerCommands();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        registerEvents();
    }

    private void printBanner() {
        System.out.println("\n\n" +
                " _  __              \n" +
                "| |/ /              \n" +
                "| ' / ___  _ __ ___ \n" +
                "|  < / _ \\| '__/ _ \\\n" +
                "| . \\ (_) | | |  __/\n" +
                "|_|\\_\\___/|_|  \\___|\n" +
                "\n");
    }

    private void addEntities() {
        for (EntityType entityType : EntityType.values()) {
            if (entityType.isAlive() && !entityType.name().equals("PLAYER")) {
                CommandSpawnmob.getEntities().add(entityType);
            }
        }
    }

    private void registerCommands() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String packageName = getClass().getPackage().getName();
        Reflections reflections = new Reflections(packageName + ".commands.impl");

        for (Class<? extends KoreCommand> clazz : reflections.getSubTypesOf(KoreCommand.class)) {
            KoreCommand command = clazz.getDeclaredConstructor().newInstance();
            if (getCommand(command.getCommandInfo().name()) != null) {
                getCommand(command.getCommandInfo().name()).setExecutor(command);
                if (command.getCommandInfo().tabCompleter()) {
                    getCommand(command.getCommandInfo().name()).setTabCompleter(command);
                }
            }
        }
    }

    private void registerEvents() {
        List<Listener> events = new ArrayList<>();
        if (SettingsFile.getFile().getBoolean("godmode.enabled")) events.add(new GodMode());
        if (SettingsFile.getFile().getBoolean("vanish.enabled")) events.add(new VanishPlayer());
        if (SettingsFile.getFile().getBoolean("spawn.enabled") && SettingsFile.getFile().getBoolean("spawn.on-join")) events.add(new Spawn());
        if (SettingsFile.getFile().getBoolean("chat.enabled")) events.add(new ChatManager());
        if (SettingsFile.getFile().getBoolean("world-manipulator.enable")) events.add(new WorldManipulator());
        if (getConfig().getString("server.join-msg") != null && !getConfig().getString("server.join-msg").isEmpty()) events.add(new JoinMSG());

        events.forEach(event -> getServer().getPluginManager().registerEvents(event, this));
    }

    private void setupFiles() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        LangFile.setUp();
        SettingsFile.setUp();
        StorageFile.setUp();

        Spawn.setBlacklist(getConfig().getBoolean("spawn.worlds.blacklist"));
        WorldManipulator.setBlacklist(getConfig().getBoolean("spawn.worlds.blacklist"));

        for (String world : getConfig().getStringList("spawn.worlds.list")) {
            Spawn.getWorlds().add(Bukkit.getWorld(world));
        }

        for (String world : getConfig().getStringList("world-manipulator.worlds.list")) {
            WorldManipulator.getWorlds().add(Bukkit.getWorld(world));
        }

    }
}