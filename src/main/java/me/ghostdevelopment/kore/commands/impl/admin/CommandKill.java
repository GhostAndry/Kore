package me.ghostdevelopment.kore.commands.impl.admin;

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

@SuppressWarnings("ALL")
@CommandInfo(name = "kill", permission = "kore.kill", tabCompleter = true)
public class CommandKill extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("kill.enabled")) {
            sender.sendMessage(LangFile.getString("command-disabled"));
            return;
        }

        switch (args.length) {
            case 0:
                handleSelfKill(sender);
                break;

            case 1:
                handleTargetKill(sender, args[0]);
                break;

            default:
                sendUsageMessage(sender);
                break;
        }
    }

    private void handleSelfKill(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.setHealth(0);
            player.sendMessage(LangFile.getString("kill.killed")
                    .replace("%player%", player.getName()));
        } else {
            sender.sendMessage(LangFile.getString("kill.usage.console"));
        }
    }

    private void handleTargetKill(CommandSender sender, String targetName) {
        Player target = Bukkit.getPlayer(targetName);

        if (target != null) {
            target.setHealth(0);
            sender.sendMessage(LangFile.getString("kill.killed")
                    .replace("%player%", target.getName()));
        } else {
            sender.sendMessage(LangFile.getString("invalid-target"));
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(LangFile.getString("kill.usage.player"));
        } else {
            sender.sendMessage(LangFile.getString("kill.usage.console"));
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
