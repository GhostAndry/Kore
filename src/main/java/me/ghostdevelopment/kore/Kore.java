package me.ghostdevelopment.kore;

import me.ghostdevelopment.kore.Files.FreezeLocFile;
import me.ghostdevelopment.kore.Files.HomesFile;
import me.ghostdevelopment.kore.Files.SpawnFile;
import me.ghostdevelopment.kore.Files.WarpsFile;
import me.ghostdevelopment.kore.commands.player.CommandSpawn;
import me.ghostdevelopment.kore.commands.player.CommandTrash;
import me.ghostdevelopment.kore.events.GodMode;
import me.ghostdevelopment.kore.commands.*;
import me.ghostdevelopment.kore.commands.admin.*;
import me.ghostdevelopment.kore.commands.fun.CommandExplode;
import me.ghostdevelopment.kore.commands.fun.CommandSmite;
import me.ghostdevelopment.kore.commands.admin.gamemodes.*;
import me.ghostdevelopment.kore.commands.player.home.CommandHome;
import me.ghostdevelopment.kore.commands.player.CommandWarp;
import me.ghostdevelopment.kore.commands.admin.CommandWarps;
import me.ghostdevelopment.kore.events.SpawOnJoin;
import me.ghostdevelopment.kore.events.VanishOnJoin;
import me.ghostdevelopment.kore.freeze.commands.CommandFreeze;
import me.ghostdevelopment.kore.freeze.commands.CommandUnfreeze;
import me.ghostdevelopment.kore.freeze.listeners.FreezeListeners;
import me.ghostdevelopment.kore.staffmode.StaffFiles;
import me.ghostdevelopment.kore.staffmode.commands.CommandStaff;
import org.bukkit.plugin.java.JavaPlugin;

public final class Kore extends JavaPlugin {

    public static Kore instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        getLogger().info("Enabled.");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        registerCommands();
        registerListeners();

        SpawnFile.setUp();
        SpawnFile.get().options().copyDefaults(true);
        SpawnFile.save();
        WarpsFile.setUp();
        WarpsFile.get().options().copyDefaults(true);
        WarpsFile.save();
        HomesFile.setUp();
        HomesFile.get().options().copyDefaults(true);
        HomesFile.save();
        FreezeLocFile.setUp();
        FreezeLocFile.get().options().copyDefaults(true);
        FreezeLocFile.save();
        StaffFiles.setUp();
        StaffFiles.get().options().copyDefaults(true);
        StaffFiles.save();

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Disabled");
        instance = null;
    }

    public void registerCommands(){

        getCommand("kore").setExecutor(new CommandKore(this));

        getCommand("gamemode").setExecutor(new CommandGamemode(this));
        getCommand("gmc").setExecutor(new CommandGMC(this));
        getCommand("gms").setExecutor(new CommandGMS(this));
        getCommand("gma").setExecutor(new CommandGMA(this));
        getCommand("gmsp").setExecutor(new CommandGMSP(this));

        getCommand("freeze").setExecutor(new CommandFreeze(this));
        getCommand("unfreeze").setExecutor(new CommandUnfreeze(this));

        getCommand("warps").setExecutor(new CommandWarps(this));
        getCommand("warp").setExecutor(new CommandWarp(this));

        getCommand("setspawn").setExecutor(new CommandSetspawn(this));
        getCommand("spawn").setExecutor(new CommandSpawn(this));

        //getCommand("staff").setExecutor(new CommandStaff(this));
        getCommand("vanish").setExecutor(new CommandVanish(this));

        getCommand("teleport").setExecutor(new CommandTeleport(this));
        getCommand("fly").setExecutor(new CommandFly(this));
        getCommand("explode").setExecutor(new CommandExplode(this));
        getCommand("heal").setExecutor(new CommandHeal(this));
        getCommand("smite").setExecutor(new CommandSmite(this));
        getCommand("god").setExecutor(new CommandGod(this));
        getCommand("kill").setExecutor(new CommandKill(this));
        getCommand("speed").setExecutor(new CommandSpeed(this));
        getCommand("home").setExecutor(new CommandHome(this));
        getCommand("trash").setExecutor(new CommandTrash(this));


        /*
        TODO: Staff Mode
        TODO: Command Give
        */

    }

    public void registerListeners(){
        // Freeze listener
        getServer().getPluginManager().registerEvents(new FreezeListeners(this), this);

        // GodMode listener
        getServer().getPluginManager().registerEvents(new GodMode(this), this);

        // Spawn on join
        getServer().getPluginManager().registerEvents(new SpawOnJoin(this), this);

        // Vanish
        getServer().getPluginManager().registerEvents(new VanishOnJoin(this), this);

    }

    public static Kore getInstance() {
        return instance;
    }
}
