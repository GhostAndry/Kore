package me.ghostdevelopment.kore.commands.admin;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandFly implements CommandExecutor {

    Kore plugin;
    public CommandFly(Kore plugin){this.plugin=plugin;}

    public static ArrayList<Player> flyList = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.fly")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("fly.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length>1){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("fly.help").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        } else if (args.length==0) {

            if(!(flyList.contains(player))){
                flyList.add(player);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("fly.toggled-on").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                player.setAllowFlight(true);
            }else{
                flyList.remove(player);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("fly.toggled-off").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                player.setAllowFlight(false);
            }

        } else if (args.length==1) {

            Player target = Bukkit.getPlayer(args[0]);

            if(!(flyList.contains(target))){
                flyList.add(target);
                target.sendMessage(Utils.Color(plugin.getConfig().getString("fly.toggled-on")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                target.setAllowFlight(true);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("fly.on-other")
                        .replaceAll("%player%", target.getName()
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                )));
            }else{
                flyList.remove(target);
                target.sendMessage(Utils.Color(plugin.getConfig().getString("fly.toggled-off")
                        .replaceAll("prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                target.setAllowFlight(false);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("fly.off-other")
                        .replaceAll("%player%", target.getName()
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        )));

            }

        }


        return true;
    }
}
