package me.ghostdevelopment.kore.commands.player.home;

import me.ghostdevelopment.kore.Files.HomesFile;
import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

public class CommandHome implements CommandExecutor {

    Kore plugin;
    public CommandHome(Kore plugin){this.plugin=plugin;}


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.home")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("home.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0||args.length>1){
            Location loc;
            String name = sender.getName().toLowerCase();

            double x = HomesFile.get().getDouble(name + ".x");
            double y = HomesFile.get().getDouble(name + ".y");
            double z = HomesFile.get().getDouble(name + ".z");
            float pitch = (float) HomesFile.get().getDouble(name + ".pitch");
            float yaw = (float) HomesFile.get().getDouble(name + ".yaw");
            String world = HomesFile.get().getString(name + ".world");

            loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);

            player.teleport(loc);

            player.sendMessage(Utils.Color(plugin.getConfig().getString("home.teleport")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        } else if (args.length==1) {

            if(args[0].equalsIgnoreCase("set")){
                if(!(HomesFile.get().contains(player.getName()))){
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("home.added")
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                    ));
                    Functions.setHome(player.getName(), player.getLocation());
                }else{
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("home.add-fail")
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                    ));
                }
            } else if (args[0].equalsIgnoreCase("remove")) {
                if(!(HomesFile.get().contains(player.getName()))){
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("home.removed")
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                    ));
                    Functions.delHome(plugin.getName());
                }else {
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("home.remove-fail")
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                    ));
                }

            }else{
                player.sendMessage(Utils.Color(plugin.getConfig().getString("home.help")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
            }

        }


        return true;
    }
}
