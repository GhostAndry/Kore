package me.ghostdevelopment.kore.commands.admin;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

import java.util.ArrayList;

@SuppressWarnings({"NullableProblems", "DataFlowIssue", "DuplicatedCode", "ConstantValue"})
public class CommandGod implements CommandExecutor {

    Kore plugin;
    public CommandGod(Kore plugin){this.plugin=plugin;}
    public static ArrayList<Player> godmode = new ArrayList<>();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.god")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("god.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0){

            if(!(godmode.contains(player))){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("god.toggled-on").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                godmode.add(player);
            }else{
                player.sendMessage(Utils.Color(plugin.getConfig().getString("god.toggled-off").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                godmode.remove(player);
            }

        } else if (args.length==1) {
            Player target = Bukkit.getPlayer(args[0]);

            if(!(godmode.contains(target))){
                target.sendMessage(Utils.Color(plugin.getConfig().getString("god.toggled-on")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                player.sendMessage(Utils.Color(plugin.getConfig().getString("god.enabled-other")
                        .replaceAll("%player%", target.getName())
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                    ));
                godmode.add(target);
            }else{
                target.sendMessage(Utils.Color(plugin.getConfig().getString("god.toggled-off")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                player.sendMessage(Utils.Color(plugin.getConfig().getString("god.disabled-other")
                        .replaceAll("%player%", target.getName())
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                godmode.add(target);
            }

        } else if (args.length>1) {
            player.sendMessage(Utils.Color(plugin.getConfig().getString("god.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        }

        return true;
    }
}
