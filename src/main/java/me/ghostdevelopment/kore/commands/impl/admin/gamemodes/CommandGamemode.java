package me.ghostdevelopment.kore.commands.impl.admin.gamemodes;

import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("ALL")
@CommandInfo(name = "gamemode", permission = "kore.gamemode.*", permission2 = "kore.gamemode", tabCompleter = true)
public class CommandGamemode extends KoreCommand {

    private static final Map<String, GameMode> gameModes = new HashMap<>();

    static {
        gameModes.put("0", GameMode.SURVIVAL);
        gameModes.put("1", GameMode.CREATIVE);
        gameModes.put("2", GameMode.ADVENTURE);
        gameModes.put("3", GameMode.SPECTATOR);
        gameModes.put("survival", GameMode.SURVIVAL);
        gameModes.put("creative", GameMode.CREATIVE);
        gameModes.put("adventure", GameMode.ADVENTURE);
        gameModes.put("spectator", GameMode.SPECTATOR);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("gamemode.enabled")) {
            sender.sendMessage(LangFile.getString("command-disabled"));
            return;
        }

        if (args.length == 0) {
            sendUsageMessage(sender);
            return;
        }

        String type = args[0];
        Player target = (args.length == 2) ? Bukkit.getPlayer(args[1]) : (sender instanceof Player ? (Player) sender : null);

        if (target == null && args.length == 2) {
            sender.sendMessage(LangFile.getString("invalid-target"));
            return;
        }

        GameMode gameMode = gameModes.get(type.toLowerCase());

        if (gameMode == null) {
            sendUsageMessage(sender);
            return;
        }

        try {
            target.setGameMode(gameMode);
            String messageKey = (sender instanceof Player && args.length == 1) ? "gamemode.changed" : "gamemode.changed-other";
            String message = LangFile.getString(messageKey);

            sender.sendMessage(message.replaceAll("%gamemode%", gameMode.name().toUpperCase())
                    .replaceAll("%player%", target.getName()));
            if (target != sender) {
                target.sendMessage(LangFile.getString("gamemode.changed").replaceAll("%gamemode%", gameMode.name().toUpperCase()));
            }
        } catch (Exception e) {
            sender.sendMessage(LangFile.getString("invalid-target"));
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(LangFile.getString("gamemode.usage.player"));
        } else {
            sender.sendMessage(LangFile.getString("gamemode.usage.console"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String partialName = args[0].toLowerCase();
            gameModes.keySet().forEach(gameMode -> {
                if (gameMode.startsWith(partialName)) {
                    completions.add(gameMode);
                }
            });
        } else if (args.length == 2) {
            String partialName = args[1].toLowerCase();
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
