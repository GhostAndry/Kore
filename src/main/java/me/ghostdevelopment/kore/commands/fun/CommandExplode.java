package me.ghostdevelopment.kore.commands.fun;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExplode implements CommandExecutor {

    Kore plugin;
    public CommandExplode(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return false;
        }
        if(!(player.hasPermission("kore.explode")||player.hasPermission("kore.*")||player.hasPermission("*"))){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.noPermission").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            return false;
        }
        if(!(plugin.getConfig().getBoolean("explode.enabled"))){
            player.sendMessage(Utils.Color("&cThis command is disabled"));
            return false;
        }

        if(args.length==0){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("explode.help").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        } else if (args.length==1) {
            player.sendMessage(Utils.Color(plugin.getConfig().getString("explode.help").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        }else if (args.length==2){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("explode.help").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        } else if (args.length==3) {
            Player target = Bukkit.getPlayer(args[0]);
            Integer pw = Integer.valueOf(args[1]);
            Boolean fire = Boolean.valueOf(args[2]);
            try{

                if(pw>plugin.getConfig().getInt("explode.max-power")){
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("explode.power-limit")
                            .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                            .replaceAll("%max-power%", plugin.getConfig().getString("explode.max-power"))
                    ));
                    return false;
                }

                World w = target.getWorld();
                w.createExplosion(target.getLocation(), pw, fire);
                player.sendMessage(Utils.Color(plugin.getConfig().getString("explode.exploded-player")
                        .replaceAll("%player%", target.getName())
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));

            }catch (Exception e){player.sendMessage(Utils.Color(plugin.getConfig().getString("explode.help").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));}
        }


        return true;
    }
}
