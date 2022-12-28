package me.ghostdevelopment.kore.events;

import me.ghostdevelopment.kore.Files.SpawnFile;
import me.ghostdevelopment.kore.Kore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawOnJoin implements Listener {

    Kore plugin;
    public SpawOnJoin(Kore plugin){this.plugin=plugin;}

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){

        if(plugin.getConfig().getBoolean("spawn.on-join")){

            Player player = event.getPlayer();

            Location loc;
            String name = "spawn";

            double X = SpawnFile.get().getDouble(name + ".x");
            double Y = SpawnFile.get().getDouble(name + ".y");
            double Z = SpawnFile.get().getDouble(name + ".z");
            float Yaw = (float) SpawnFile.get().getDouble(name + ".yaw");
            float Pitch = (float) SpawnFile.get().getDouble(name + ".pitch");
            String world = SpawnFile.get().getString(name + ".world");
            loc = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);

            player.teleport(loc);

        }

    }

}
