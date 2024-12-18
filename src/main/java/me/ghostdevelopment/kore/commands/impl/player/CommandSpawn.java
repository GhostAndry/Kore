package me.ghostdevelopment.kore.commands.impl.player;

import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.files.StorageFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@CommandInfo(name = "spawn", permission = "kore.spawn", permission2 = "kore.spawn.other", tabCompleter = true)
public class CommandSpawn extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("spawn.enabled")) {
            sendMessage(sender, "command-disabled");
            return;
        }

        if (!(sender instanceof Player) && args.length != 1) {
            sendMessage(sender, "spawn.usage.console");
            return;
        }

        if (!StorageFile.getFile().contains("spawn.world")
                || !StorageFile.getFile().contains("spawn.x")
                || !StorageFile.getFile().contains("spawn.y")
                || !StorageFile.getFile().contains("spawn.z")
                || !StorageFile.getFile().contains("spawn.yaw")
                || !StorageFile.getFile().contains("spawn.pitch")) {
            sendMessage(sender, "spawn.nonexistent");
            return;
        }

        Location spawn = Functions.getSpawnLocation();

        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                player.teleport(spawn);
                sendMessage(player, "spawn.teleported");
            } else {
                sendMessage(sender, "spawn.usage.console");
            }
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sendMessage(sender, "invalid-target");
                return;
            }

            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!player.hasPermission("kore.spawn.other")) {
                    sendMessage(player, "no-permissions");
                    return;
                }
            }

            target.teleport(spawn);
            sender.sendMessage(LangFile.getString("spawn.teleported-other")
                    .replaceAll("%player%", target.getName())
            );
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
