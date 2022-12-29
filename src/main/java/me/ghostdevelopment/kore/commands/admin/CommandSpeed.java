package me.ghostdevelopment.kore.commands.admin;

import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

public class CommandSpeed implements CommandExecutor {

    Kore plugin;
    public CommandSpeed(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.speed")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("speed.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("speed.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        } else if (args.length==1) {
            Float speed = null;

            try {
                speed = Float.parseFloat(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage(Utils.Color(this.plugin.getConfig().getString("speed.invalid-value")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                return false;
            }
            if(speed>5.0||speed<0.0){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("speed.invalid-value")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                return false;
            }

            if(player.isOnGround()){
                Functions.setSpeed(player, "walk", speed);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("speed.set")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        .replaceAll("%speed%", speed.toString())
                ));
            }else{
                Functions.setSpeed(player, "fly", speed);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("speed.set")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        .replaceAll("%speed%", speed.toString())
                ));
            }
        } else {
            player.sendMessage(Utils.Color(plugin.getConfig().getString("speed.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        }


        return true;
    }
}
