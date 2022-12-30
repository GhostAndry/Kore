package me.ghostdevelopment.kore.events;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.commands.admin.CommandGod;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodMode implements Listener {

    Kore plugin;
    public GodMode(Kore plugin){this.plugin=plugin;}

    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent event){

        if (event.getEntity() instanceof Player) {

            if(CommandGod.godmode.contains(((Player) event.getEntity()).getPlayer())){
                event.setCancelled(true);
            }

        }

    }

}
