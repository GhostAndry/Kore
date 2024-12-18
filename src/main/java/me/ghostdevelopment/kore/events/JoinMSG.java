package me.ghostdevelopment.kore.events;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.utils.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinMSG implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerJoin(PlayerJoinEvent event) {

        if (Kore.getInstance().getConfig().getString("server.join-msg").isEmpty() 
        || Kore.getInstance().getConfig().getString("server.join-msg") == null){
            event.setJoinMessage(null);
            return;
        }

        event.setJoinMessage(Color.Color(SettingsFile.getFile().getString("server.join-msg")
                .replaceAll("%player%", event.getPlayer().getName())
        ));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {

        if (Kore.getInstance().getConfig().getString("server.join-msg").isEmpty() 
        || Kore.getInstance().getConfig().getString("server.join-msg") == null){
            event.setQuitMessage(null);
            return;
        }

        event.setQuitMessage(Color.Color(SettingsFile.getFile().getString("server.leave-msg")
                .replaceAll("%player%", event.getPlayer().getName())
        ));
    }

}
