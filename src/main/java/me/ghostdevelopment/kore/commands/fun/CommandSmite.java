package me.ghostdevelopment.kore.commands.fun;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.NotNull;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

public class CommandSmite implements CommandExecutor {

    Kore plugin;
    public CommandSmite(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.smite")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("smite.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("smite.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        } else if (args.length==1) {
            try {
                Player target = Bukkit.getPlayer(args[0]);

                World world = target.getWorld();
                Location loc = target.getLocation();

                world.strikeLightningEffect(loc);

                player.sendMessage(Utils.Color(plugin.getConfig().getString("smite.smite-player")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        .replaceAll("%player%", target.getName())
                ));
            }catch (Exception e){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("smite.help")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
            }
        } else if (args.length>1) {
            player.sendMessage(Utils.Color(plugin.getConfig().getString("smite.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        }


        return false;
    }
}
