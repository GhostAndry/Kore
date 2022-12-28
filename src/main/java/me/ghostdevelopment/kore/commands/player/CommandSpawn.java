package me.ghostdevelopment.kore.commands.player;

import me.ghostdevelopment.kore.Files.SpawnFile;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {

    Kore plugin;
    public CommandSpawn(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.spawn")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("spawn.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(!SpawnFile.get().contains("spawn")){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.spawn.not-set")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
            return false;
        }
        

        if(args.length==0){
            Location loc;
            String name = "spawn";

            double X = SpawnFile.get().getDouble(name + ".x");
            double Y = SpawnFile.get().getDouble(name + ".y");
            double Z = SpawnFile.get().getDouble(name + ".z");
            float Yaw = (float) SpawnFile.get().getDouble(name + ".yaw");
            float Pitch = (float) SpawnFile.get().getDouble(name + ".pitch");
            String world = SpawnFile.get().getString(name + ".world");
            loc = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);

            player.teleport(loc);
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.spawn.teleport")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));

        }else if (args.length==1) {
            Player target = Bukkit.getPlayer(args[0]);
            Location loc;
            String name = "spawn";

            double X = SpawnFile.get().getDouble(name + ".x");
            double Y = SpawnFile.get().getDouble(name + ".y");
            double Z = SpawnFile.get().getDouble(name + ".z");
            float Yaw = (float) SpawnFile.get().getDouble(name + ".yaw");
            float Pitch = (float) SpawnFile.get().getDouble(name + ".pitch");
            String world = SpawnFile.get().getString(name + ".world");
            loc = new Location(Bukkit.getWorld(world), X, Y, Z, Yaw, Pitch);

            target.teleport(loc);
            target.sendMessage(Utils.Color(plugin.getConfig().getString("messages.spawn.teleport")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.spawn.teleport")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.spawn.teleport-other"))
            ));
        }else{
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.spawn.help")
                    .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
            ));
        }


        return true;
    }
}
