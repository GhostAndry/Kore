package me.ghostdevelopment.kore.commands.impl.admin;

import me.ghostdevelopment.kore.Functions;
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

@CommandInfo(name = "speed", permission = "kore.speed", tabCompleter = true)
public class CommandSpeed extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("speed.enabled")) {
            sendMessage(sender, "command-disabled");
            return;
        }

        if (args.length < 1 || args.length > 3) {
            sendMessage(sender, sender instanceof Player ? "speed.usage.player" : "speed.usage.console");
            return;
        }

        String speedStr = args[0];
        float speed;

        try {
            speed = Float.parseFloat(speedStr);
            if (speed < 0.0 || speed > 10.0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            sendMessage(sender, "speed.invalid-value");
            return;
        }

        if(speed > 1) sendMessage(sender, "speed.usage.player");

        switch (args.length) {
            case 1:
                if (sender instanceof Player) {
                    handleSpeedChange((Player) sender, speed);
                } else {
                    sendMessage(sender, "speed.usage.console");
                }
                break;

            case 2:
                if (sender instanceof Player) {
                    handleSpeedChange((Player) sender, speed, Bukkit.getPlayer(args[1]));
                } else {
                    handleSpeedChange(sender, speed, Bukkit.getPlayer(args[1]));
                }
                break;

            case 3:
                if (sender instanceof Player) {
                    handleSpeedChange((Player) sender, speed, Bukkit.getPlayer(args[1]), args[2]);
                } else {
                    handleSpeedChange(sender, speed, Bukkit.getPlayer(args[1]), args[2]);
                }
                break;

            default:
                sendMessage(sender, sender instanceof Player ? "speed.usage.player" : "speed.usage.console");
                break;
        }
    }

    private void handleSpeedChange(Player sender, float speed) {
        if (sender.isOnGround()) {
            Functions.setSpeed(sender, "walk", speed);
        } else {
            Functions.setSpeed(sender, "fly", speed);
        }
        sender.sendMessage(LangFile.getString("speed.set")
                .replace("%speed%", String.valueOf(speed))
        );
    }

    private void handleSpeedChange(CommandSender sender, float speed, Player target) {
        if (target == null) {
            sendMessage(sender, "invalid-target");
            return;
        }

        if (target.isOnGround()) {
            Functions.setSpeed(target, "walk", speed);
        } else {
            Functions.setSpeed(target, "fly", speed);
        }
        target.sendMessage(LangFile.getString("speed.set")
                .replace("%speed%", String.valueOf(speed))
        );
        sender.sendMessage(LangFile.getString("speed.set-other")
                .replace("%player%", target.getName())
                .replace("%speed%", String.valueOf(speed))
        );
    }

    private void handleSpeedChange(CommandSender sender, float speed, Player target, String type) {
        if (target == null) {
            sendMessage(sender, "invalid-target");
            return;
        }

        String speedType = type.equalsIgnoreCase("walk") || type.equalsIgnoreCase("walking") ? "walk" : "fly";
        if (!speedType.equals("walk") && !speedType.equals("fly")) {
            sendMessage(sender, "speed.invalid-type");
            return;
        }

        Functions.setSpeed(target, speedType, speed);
        target.sendMessage(LangFile.getString("speed.set")
                .replace("%speed%", String.valueOf(speed))
        );
        sender.sendMessage(LangFile.getString("speed.set-other")
                .replace("%player%", target.getName())
                .replace("%speed%", String.valueOf(speed))
        );
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        switch (args.length) {
            case 1:
                for (float i = 0; i <= 10.0; i += 0.1) {
                    completions.add(String.format("%.1f", i));
                }
                break;
            case 2:
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completions.add(player.getName());
                }
                break;
            case 3:
                completions.add("walk");
                completions.add("fly");
                break;
        }
        return completions;
    }
}
