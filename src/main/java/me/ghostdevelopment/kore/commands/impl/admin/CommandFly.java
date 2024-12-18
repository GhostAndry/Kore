package me.ghostdevelopment.kore.commands.impl.admin;

import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

@SuppressWarnings("ALL")
@CommandInfo(name = "fly", permission = "kore.fly", tabCompleter = true)
public class CommandFly extends KoreCommand {

    private static final Set<Player> flyingPlayers = new HashSet<>();

    public static Set<Player> getFlyingPlayers() {
        return flyingPlayers;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("fly.enabled")) {
            sender.sendMessage(LangFile.getString("command-disabled"));
            return;
        }

        if (args.length > 1) {
            sendUsageMessage(sender);
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                toggleFlyMode(player, player);
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    toggleFlyMode(target, player);
                } else {
                    player.sendMessage(LangFile.getString("invalid-target"));
                }
            }
        } else {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    toggleFlyMode(target, sender);
                } else {
                    sender.sendMessage(LangFile.getString("invalid-target"));
                }
            } else {
                sendUsageMessage(sender);
            }
        }
    }

    private void toggleFlyMode(Player target, CommandSender notifier) {
        if (flyingPlayers.contains(target)) {
            flyingPlayers.remove(target);
            target.setAllowFlight(false);

            if (!notifier.getName().equalsIgnoreCase(target.getName())) notifier.sendMessage(LangFile.getString("fly.disabled-other") .replace("%player%", target.getName()));
            target.sendMessage(LangFile.getString("fly.disabled"));
        } else {
            flyingPlayers.add(target);
            target.setAllowFlight(true);

            if (!notifier.getName().equalsIgnoreCase(target.getName())) notifier.sendMessage(LangFile.getString("fly.enabled-other").replace("%player%", target.getName()));
            target.sendMessage(LangFile.getString("fly.enabled"));
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(LangFile.getString("fly.usage.player"));
        } else {
            sender.sendMessage(LangFile.getString("fly.usage.console"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String partialName = args[0].toLowerCase();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                String playerName = onlinePlayer.getName();
                if (playerName.toLowerCase().startsWith(partialName)) {
                    completions.add(playerName);
                }
            }
        }

        return completions;
    }
}
