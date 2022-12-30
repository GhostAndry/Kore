package me.ghostdevelopment.kore.commands.fun;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.NotNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

@SuppressWarnings({"NullableProblems", "DataFlowIssue", "DuplicatedCode"})
public class CommandCannon implements CommandExecutor {

    Kore plugin;
    public CommandCannon(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        /*
        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.cannon")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("cannon.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0){

        }

         */



        return true;
    }
}
