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
@CommandInfo(name = "god", permission = "kore.god", tabCompleter = true)
public class CommandGod extends KoreCommand {

    private static final Set<Player> godPlayers = new HashSet<>();

    public static Set<Player> getGodPlayers() {
        return godPlayers;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("godmode.enabled")) {
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
                toggleGodMode(player, player);
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    toggleGodMode(target, player);
                } else {
                    player.sendMessage(LangFile.getString("invalid-target"));
                }
            }
        } else {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    toggleGodMode(target, sender);
                } else {
                    sender.sendMessage(LangFile.getString("invalid-target"));
                }
            } else {
                sendUsageMessage(sender);
            }
        }
    }

    private void toggleGodMode(Player target, CommandSender notifier) {
        if (godPlayers.contains(target)) {
            godPlayers.remove(target);
            target.sendMessage(LangFile.getString("god.disabled"));
            if (!notifier.getName().equalsIgnoreCase(target.getName())) notifier.sendMessage(LangFile.getString("god.disabled-other") .replace("%player%", target.getName()));
        } else {
            godPlayers.add(target);
            target.sendMessage(LangFile.getString("god.enabled"));
            if (!notifier.getName().equalsIgnoreCase(target.getName())) notifier.sendMessage(LangFile.getString("god.enabled-other") .replace("%player%", target.getName()));
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(LangFile.getString("god.usage.player"));
        } else {
            sender.sendMessage(LangFile.getString("god.usage.console"));
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
