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

@SuppressWarnings({"NullableProblems", "DataFlowIssue", "unused", "DuplicatedCode"})
public class CommandGamemode implements CommandExecutor {

    Kore plugin;
    public CommandGamemode(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

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

        if(args.length==0){
            player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.help").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
        } else if (args.length==1) {

            try {
                if (args[0].equalsIgnoreCase("1")) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.creative-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                } else if (args[0].equalsIgnoreCase("2")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.adventure-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                } else if (args[0].equalsIgnoreCase("0")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.survival-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                } else if (args[0].equalsIgnoreCase("3")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.spectator-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                }else if (args[0].equalsIgnoreCase("creative")) {
                    player.setGameMode(GameMode.CREATIVE);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.creative-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                } else if (args[0].equalsIgnoreCase("adventure")) {
                    player.setGameMode(GameMode.ADVENTURE);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.adventure-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                } else if (args[0].equalsIgnoreCase("survival")) {
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.survival-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                } else if (args[0].equalsIgnoreCase("spectator")) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.spectator-changed").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                }
            }catch (Exception e){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.invalid-gamemode").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            }

        } else if (args.length==2) {

            Player target = Bukkit.getPlayer(args[1]);

            try {
                try {
                    if (args[0].equalsIgnoreCase("1")) {
                        target.setGameMode(GameMode.CREATIVE);
                        target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.creative-changed")));
                        player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", target.getGameMode().name())
                                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        ));
                    } else if (args[0].equalsIgnoreCase("2")) {
                        target.setGameMode(GameMode.ADVENTURE);
                        target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.adventure-changed")));
                        player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", target.getGameMode().name())
                                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        ));
                    } else if (args[0].equalsIgnoreCase("0")) {
                        target.setGameMode(GameMode.SURVIVAL);
                        target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.survival-changed")));
                        player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", target.getGameMode().name())
                                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        ));
                    } else if (args[0].equalsIgnoreCase("3")) {
                        target.setGameMode(GameMode.SPECTATOR);
                        target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.spectator-changed")));
                        player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", target.getGameMode().name())
                                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        ));
                    } else if (args[0].equalsIgnoreCase("creative")) {
                        target.setGameMode(GameMode.CREATIVE);
                        target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.creative-changed")));
                        player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", target.getGameMode().name())
                                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        ));
                    } else if (args[0].equalsIgnoreCase("adventure")) {
                        target.setGameMode(GameMode.ADVENTURE);
                        target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.adventure-changed")));
                        player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", target.getGameMode().name())
                                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        ));
                    } else if (args[0].equalsIgnoreCase("survival")) {
                        target.setGameMode(GameMode.SURVIVAL);
                        target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.survival-changed")));
                        player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", target.getGameMode().name())
                                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        ));
                    } else if (args[0].equalsIgnoreCase("spectator")) {
                        target.setGameMode(GameMode.SPECTATOR);
                        target.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.spectator-changed")));
                        player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.changed-other")
                                .replaceAll("%player%", target.getName())
                                .replaceAll("%gamemode%", target.getGameMode().name())
                                .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                        ));
                    }
                }catch (Exception e){
                    player.sendMessage(Utils.Color(plugin.getConfig().getString("messages.invalid-player-target").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
                }
            }catch (Exception e){
                player.sendMessage(Utils.Color(plugin.getConfig().getString("gamemode.invalid-gamemode").replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            }

        }

        return true;
    }
}
