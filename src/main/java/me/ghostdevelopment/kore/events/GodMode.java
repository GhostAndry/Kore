package me.ghostdevelopment.kore.events;

import me.ghostdevelopment.kore.commands.impl.admin.CommandGod;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodMode implements Listener {

    @EventHandler
    public void PlayerInGodModeDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (CommandGod.getGodPlayers().contains(((Player) event.getEntity()).getPlayer())) {
                event.setCancelled(true);
            }
        }
    }
}
