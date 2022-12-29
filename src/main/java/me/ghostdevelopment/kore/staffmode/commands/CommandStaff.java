package me.ghostdevelopment.kore.staffmode.commands;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import me.ghostdevelopment.kore.staffmode.StaffFiles;
import me.ghostdevelopment.kore.staffmode.StaffFunctions;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStaff implements CommandExecutor {

    Kore plugin;
    public CommandStaff(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.staffmode")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("staff.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(!(StaffFiles.get().contains(player.getName()))){
            StaffFunctions.setStaffMode(player, true);
        }else {
            if (StaffFiles.get().getBoolean(player.getName())) {
                StaffFunctions.setStaffMode(player, false);
            } else {
                StaffFunctions.setStaffMode(player, true);
            }
        }


        return true;
    }
}
