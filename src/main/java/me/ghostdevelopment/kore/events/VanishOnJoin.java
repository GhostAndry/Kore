package me.ghostdevelopment.kore.events;

import me.ghostdevelopment.kore.Kore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.ghostdevelopment.kore.commands.admin.CommandVanish.vanished;

public class VanishOnJoin implements Listener {

    Kore plugin;
    public VanishOnJoin(Kore plugin){this.plugin=plugin;}

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();

        for(int i = 0; i<vanished.size(); i++){
            if(!(player.hasPermission("kore.vanish")||player.hasPermission("kore.*")||player.hasPermission("*"))) {
                player.hidePlayer(vanished.get(i));
            }

        }

    }

}
