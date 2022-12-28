package me.ghostdevelopment.kore.freeze.commands;

import me.ghostdevelopment.kore.Files.FreezeLocFile;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import me.ghostdevelopment.kore.freeze.FreezeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

import static me.ghostdevelopment.kore.freeze.FreezeManager.freezeManager;

public class CommandUnfreeze implements CommandExecutor {

    Kore plugin;
    public CommandUnfreeze(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if(!(plugin.getConfig().getBoolean("freeze.enable"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("&cThis function is disabled.")
                    .replace("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
            return false;
        }
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.freeze")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(FreezeLocFile.get().contains(FreezeManager.name))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("freeze.freezeLoc-not-set")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
            return false;
        }

        if(args.length==0){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("freeze.unfreeze.usage")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        } else if (args.length==1) {
            Player target = Bukkit.getPlayer(args[0]);

            if(target==player){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("freeze.unfreeze.no-self")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                return false;
            }
            if (target.hasPermission("kore.freeze")||target.hasPermission("kore.*")||target.hasPermission("*")){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("freeze.noPerms-player")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                return false;
            }

            if(!(freezeManager.contains(target))){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("freeze.unfreeze.already-unfrozen")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        .replaceAll("%player%", target.getName())

                ));
            }else{
                freezeManager.remove(target);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("freeze.unfreeze.player-unfrozen")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        .replaceAll("%player%", target.getName())

                ));
            }

        }
        return true;
    }
}
