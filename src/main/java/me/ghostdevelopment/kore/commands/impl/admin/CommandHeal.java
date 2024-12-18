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

@SuppressWarnings("all")
@CommandInfo(name = "heal", permission = "kore.heal", tabCompleter = true)
public class CommandHeal extends KoreCommand {

    private static final double FULL_HEALTH = 20.0;
    private static final int FULL_FOOD_LEVEL = 40;

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!SettingsFile.getFile().getBoolean("heal.enabled")) {
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
                healPlayer(player);
                player.sendMessage(LangFile.getString("heal.healed"));
            } else {
                handleTargetHeal(sender, args[0]);
            }
        } else {
            if (args.length == 1) {
                handleTargetHeal(sender, args[0]);
            } else {
                sendUsageMessage(sender);
            }
        }
    }

    private void healPlayer(Player player) {
        player.setHealth(FULL_HEALTH);
        player.setFoodLevel(FULL_FOOD_LEVEL);
    }

    private void handleTargetHeal(CommandSender sender, String targetName) {
        Player target = Bukkit.getPlayer(targetName);

        if (target != null) {
            healPlayer(target);
            sender.sendMessage(LangFile.getString("heal.healed-other")
                    .replace("%player%", target.getName()));
        } else {
            sender.sendMessage(LangFile.getString("invalid-target"));
        }
    }

    private void sendUsageMessage(CommandSender sender) {
        if (sender instanceof Player) {
            sender.sendMessage(LangFile.getString("heal.usage.player"));
        } else {
            sender.sendMessage(LangFile.getString("heal.usage.console"));
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
