package me.ghostdevelopment.kore.freeze.listeners;

import me.ghostdevelopment.kore.Files.FreezeLocFile;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import static me.ghostdevelopment.kore.freeze.FreezeManager.freezeManager;
import static me.ghostdevelopment.kore.freeze.FreezeManager.name;

public class FreezeListeners implements Listener {

    Kore plugin;
    public FreezeListeners(Kore plugin){this.plugin=plugin;}
    @EventHandler
    public void onPlayerDamageEvent(EntityDamageEvent event){

        if(event.getEntity() instanceof Player){
            if(freezeManager.contains(((Player) event.getEntity()).getPlayer())){
                event.setCancelled(true);
            }

        }

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){

        Player player = event.getPlayer();

        if(freezeManager.contains(player)){
            Location frozenLoc;
            double x = FreezeLocFile.get().getDouble(name+".x");
            double y = FreezeLocFile.get().getDouble(name+".y");
            double z = FreezeLocFile.get().getDouble(name+".z");
            float yaw = player.getLocation().getYaw();
            float pitch = player.getLocation().getPitch();
            String world = FreezeLocFile.get().getString(name+".world");
            frozenLoc = new Location(Bukkit.getWorld(world), x,y,z,yaw,pitch);

            if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockY() != event.getTo().getBlockY() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {

                event.getPlayer().teleport(frozenLoc);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("freeze.freeze.frozen-message")
                        .replaceAll("%platform%", plugin.getConfig().getString("freeze.platform"))
                        .replaceAll("%platform-ip%", plugin.getConfig().getString("freeze.platform-ip"))
                ));
            }

        }

    }

    @EventHandler
    public void onPlayerChatEvent(PlayerChatEvent event){

        Player player = event.getPlayer();

        if(freezeManager.contains(player)){
            event.setCancelled(true);
        }

    }

}
