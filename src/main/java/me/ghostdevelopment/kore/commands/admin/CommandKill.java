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

public class CommandKill implements CommandExecutor {

    Kore plugin;
    public CommandKill(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.god")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("kill.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0||args.length>1){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("kill.help").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        }else{
            Player target = Bukkit.getPlayer(args[0]);
            target.setHealth(0);
            player.sendMessage(Utils.Color(plugin.getConfig().getString("kill.killed").replaceAll("%player%", target.getName()).replace("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        }

        return true;
    }
}
