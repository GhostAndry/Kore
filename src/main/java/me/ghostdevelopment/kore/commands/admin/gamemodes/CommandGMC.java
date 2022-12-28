package me.ghostdevelopment.kore.commands.admin.gamemodes;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

public class CommandGMC implements CommandExecutor {

    Kore plugin;
    public CommandGMC(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.gamemode")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("gamemode.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.creative-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        } else if (args.length==1) {
            Player target = Bukkit.getPlayer(args[0]);
            target.setGameMode(GameMode.CREATIVE);
            target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.creative-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                    .replaceAll("%player%", target.getName())
                    .replaceAll("%gamemode%", target.getGameMode().name())
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        } else if (args.length>1) {
            player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.invalid-gamemode").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        }


        return true;
    }
}
