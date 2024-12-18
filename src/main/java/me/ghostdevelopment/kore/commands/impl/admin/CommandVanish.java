package me.ghostdevelopment.kore.commands.impl.admin;

import lombok.Getter;
import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "vanish", permission = "kore.vanish", tabCompleter = true)
public class CommandVanish extends KoreCommand {

    @Getter
    private static final List<Player> vanished = new ArrayList<>();

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!SettingsFile.getFile().getBoolean("vanish.enabled")) {
            sendMessage(sender, "command-disabled");
            return;
        }

        if (args.length > 1) {
            sendMessage(sender, "vanish.usage.player");
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                toggleVanish(player);
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sendMessage(player, "invalid-target");
                    return;
                }
                toggleVanish(target);

                if(getVanished().contains(target)) {
                    player.sendMessage(LangFile.getString("vanish.disabled-other")
                            .replaceAll("%player%", target.getName())
                    );
                }else{
                    player.sendMessage(LangFile.getString("vanish.enabled-other")
                           .replaceAll("%player%", target.getName())
                    );
                }

            }
        } else {
            if (args.length == 1) {
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sendMessage(sender, "invalid-target");
                    return;
                }
                toggleVanish(target);
                if(getVanished().contains(target)) {
                    sender.sendMessage(LangFile.getString("vanish.disabled-other")
                            .replaceAll("%player%", target.getName())
                    );
                }else{
                    sender.sendMessage(LangFile.getString("vanish.enabled-other")
                            .replaceAll("%player%", target.getName())
                    );
                }
            } else {
                sendMessage(sender, "vanish.usage.player");
            }
        }
    }

    private void toggleVanish(Player player) {
        if (vanished.contains(player)) {
            vanished.remove(player);
            showPlayerToEveryone(player);
            sendMessage(player, "vanish.disabled");
        } else {
            vanished.add(player);
            hidePlayerFromEveryone(player);
            sendMessage(player, "vanish.enabled");
        }
    }

    private void showPlayerToEveryone(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("kore.vanish") && !onlinePlayer.hasPermission("kore.*") && !onlinePlayer.hasPermission("*") && !onlinePlayer.isOp()) {
                onlinePlayer.showPlayer(player);
            }
        }
    }

    private void hidePlayerFromEveryone(Player player) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!onlinePlayer.hasPermission("kore.vanish") && !onlinePlayer.hasPermission("kore.*") && !onlinePlayer.hasPermission("*") && !onlinePlayer.isOp()) {
                onlinePlayer.hidePlayer(player);
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String partialName = args[0].toLowerCase();
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (onlinePlayer.getName().toLowerCase().startsWith(partialName)) {
                    completions.add(onlinePlayer.getName());
                }
            }
        }

        return completions;
    }
}
