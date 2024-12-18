package me.ghostdevelopment.kore.events;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.commands.impl.admin.CommandVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@SuppressWarnings("ALL")
public class VanishPlayer implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(Kore.getInstance(), () -> {
            Player player = event.getPlayer();

            for (int i = 0; i < CommandVanish.getVanished().size(); i++) {
                if (!(player.hasPermission("kore.vanish") || player.hasPermission("kore.*") || player.hasPermission("*"))) {
                    player.hidePlayer(CommandVanish.getVanished().get(i));
                }
            }
        });
    }
}
