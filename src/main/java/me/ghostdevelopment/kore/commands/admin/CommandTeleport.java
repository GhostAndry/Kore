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

@SuppressWarnings({"NullableProblems", "DataFlowIssue", "DuplicatedCode"})
public class CommandTeleport implements CommandExecutor {

    Kore plugin;
    public CommandTeleport(Kore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.teleport")||player.hasPermission("kore.tp")||player.hasPermission("kore.*")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("teleport.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }


        if(args.length==0){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("teleport.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        } else if (args.length==1) {
            try{
                Player target = Bukkit.getPlayer(args[0]);
                player.teleport(target.getLocation());
            }catch(Exception e){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.invalid-player-target")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            }

        } else if (args.length==2) {

            try{
                Player target = Bukkit.getPlayer(args[0]);
                Player target2 = Bukkit.getPlayer(args[1]);
                try{
                    target.teleport(target2.getLocation());
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("teleport.teleported-other")
                            .replaceAll("%player%", target.getName())
                            .replaceAll("%target%", target2.getName())
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                    target.sendMessage(Utils.Color(plugin.getConfig().getString("teleport.teleported-player")
                            .replaceAll("%player%", target2.getName())
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                }catch (Exception e){
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.invalid-player-target")
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                }
            }catch (Exception e){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.invalid-player-target")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            }
        }
        return true;
    }
}
