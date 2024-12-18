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
import java.util.List;

@SuppressWarnings("ALL")
@CommandInfo(name = "gmc", permission = "kore.gamemode.creative", permission2 = "kore.gamemode.*", permission3 = "kore.gamemode", tabCompleter = true)
public class CommandGMC extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("gamemode.enabled")) {
            sender.sendMessage(LangFile.getString("command-disabled"));
            return;
        }

        if (args.length > 1) {
            sender.sendMessage(LangFile.getString("gamemode.usage.console"));
            return;
        }

        Player target = (args.length == 1) ? Bukkit.getPlayer(args[0]) : (sender instanceof Player ? (Player) sender : null);

        if (target == null) {
            sender.sendMessage(LangFile.getString("invalid-target"));
            return;
        }

        setGameMode(target, sender);
    }

    private void setGameMode(Player target, CommandSender sender) {
        target.setGameMode(GameMode.CREATIVE);
        String messageKey = (sender instanceof Player && target != sender) ? "gamemode.changed-other" : "gamemode.changed";
        String message = LangFile.getString(messageKey)
                .replace("%gamemode%", target.getGameMode().name().toUpperCase())
                .replace("%player%", target.getName());

        sender.sendMessage(message);

        if (target != sender) {
            target.sendMessage(LangFile.getString("gamemode.changed")
                    .replace("%gamemode%", target.getGameMode().name().toUpperCase()));
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
