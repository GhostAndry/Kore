package me.ghostdevelopment.kore.commands.player;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import me.ghostdevelopment.kore.Files.WarpsFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

@SuppressWarnings({"NullableProblems", "DataFlowIssue", "DuplicatedCode", "ExcessiveRangeCheck"})
public class CommandWarp implements CommandExecutor {

    Kore plugin;
    public CommandWarp(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.warp")||player.hasPermission("kore.*")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("warp.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0||args.length>=2){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        }else {

            if (args[0].equalsIgnoreCase("list")){
                player.sendMessage(Utils.Color("&cList function is in maintenance"));
            }else{
                String name = args[0];

                if(WarpsFile.get().contains(name)) {
                    Location loc;

                    double x = WarpsFile.get().getDouble(name + ".x");
                    double y = WarpsFile.get().getDouble(name + ".y");
                    double z = WarpsFile.get().getDouble(name + ".z");
                    float pitch = (float) WarpsFile.get().getDouble(name + ".pitch");
                    float yaw = (float) WarpsFile.get().getDouble(name + ".yaw");
                    String world = WarpsFile.get().getString(name + ".world");

                    loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

                    player.teleport(loc);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.warped")
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                            .replaceAll("%name%", name)
                    ));
                }else{
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("warp.invalid")
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                            .replaceAll("%name%", name)
                    ));
                }
            }

        }

        return true;
    }
}
