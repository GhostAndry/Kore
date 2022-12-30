package me.ghostdevelopment.kore.commands.admin;

import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import me.ghostdevelopment.kore.Files.WarpsFile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

@SuppressWarnings({"NullableProblems", "DataFlowIssue", "DuplicatedCode", "ExcessiveRangeCheck"})
public class CommandWarps implements CommandExecutor {

    Kore plugin;
    public CommandWarps(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.warps")||player.hasPermission("kore.*")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("warp.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0||args.length==1||args.length>2){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.warps.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
            return false;
        }
        if(args[0].equalsIgnoreCase("add")||args[0].equalsIgnoreCase("create")){
            String name = args[1];
            if(WarpsFile.get().contains(name)) {
                player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.warps.already-exist")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
            }else{
                Functions.addWarp(name, player.getLocation());
                player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.warps.added")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        .replaceAll("%name%", name)
                ));
            }
        }else if(args[0].equalsIgnoreCase("remove")||args[0].equalsIgnoreCase("rem")||args[0].equalsIgnoreCase("delete")||args[0].equalsIgnoreCase("del")){
            String name = args[1];
            if(WarpsFile.get().contains(name)) {
                Functions.delWarp(name);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.warps.removed")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        .replaceAll("%name%", name)
                ));
            }else{
                player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.warps.invalid")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        .replaceAll("%name%", name)
                ));
            }
        }else{player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.warps.help").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));}


        return true;
    }
}
