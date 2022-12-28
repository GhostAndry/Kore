package me.ghostdevelopment.kore.commands.admin;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

import java.util.ArrayList;

public class CommandVanish implements CommandExecutor {

    Kore plugin;
    public CommandVanish(Kore plugin){this.plugin=plugin;}
    public static ArrayList<Player> vanished = new ArrayList<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.vanish")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("vanish.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }


        if(args.length==0){
            if(!(vanished.contains(player))){
                for(Player people : Bukkit.getOnlinePlayers()) {
                    if(!(people.hasPermission("kore.vanish")||people.hasPermission("kore.*")||people.hasPermission("*"))) {
                        people.hidePlayer(player);
                        people.sendMessage(Utils.Color(plugin.getConfig().getString("vanish.leave-message")
                                .replaceAll("%player%", player.getName())
                        ));
                    }
                }
                player.sendMessage(Utils.Color(plugin.getConfig().getString("vanish.toggled-on").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                vanished.add(player);
            }else{
                for(Player people : Bukkit.getOnlinePlayers()) {
                    if(!(people.hasPermission("kore.vanish")||people.hasPermission("kore.*")||people.hasPermission("*"))) {
                        people.showPlayer(player);
                        people.sendMessage(Utils.Color(plugin.getConfig().getString("vanish.join-message"))
                                .replaceAll("%player%", player.getName())
                        );
                    }
                }
                player.sendMessage(Utils.Color(plugin.getConfig().getString("vanish.toggled-off").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                vanished.remove(player);
            }

        }else{
            player.sendMessage(Utils.Color(plugin.getConfig().getString("vanish.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        }

        return true;
    }
}
