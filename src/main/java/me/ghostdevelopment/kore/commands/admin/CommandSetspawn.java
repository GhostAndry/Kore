package me.ghostdevelopment.kore.commands.admin;

import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@SuppressWarnings({"NullableProblems", "DataFlowIssue", "DuplicatedCode"})
public class CommandSetspawn implements CommandExecutor {

    Kore plugin;
    public CommandSetspawn(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.setspawn")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }

        Location loc = player.getLocation();
        Functions.setSpawnLoc(loc);
        player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.setspawn.set")
                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
        ));



        return true;
    }
}
