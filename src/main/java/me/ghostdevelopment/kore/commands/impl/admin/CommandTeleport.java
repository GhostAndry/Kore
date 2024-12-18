package me.ghostdevelopment.kore.commands.impl.admin;

import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@CommandInfo(name = "teleport", permission = "kore.teleport", permission2 = "kore.tp", tabCompleter = true)
public class CommandTeleport extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!SettingsFile.getFile().getBoolean("teleport.enabled")) {
            sendMessage(sender, "command-disabled");
            return;
        }

        if (args.length == 0) {
            sendMessage(sender, sender instanceof Player ? "teleport.usage.player" : "teleport.usage.console");
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;
            switch (args.length) {
                case 1:
                    handleTeleportPlayer(player, args[0]);
                    break;
                case 2:
                    handleTeleportPlayerToPlayer(player, args[0], args[1]);
                    break;
                case 3:
                    handleTeleportToLocation(player, args[0], args[1], args[2]);
                    break;
                case 4:
                    handleTeleportPlayerToLocation(player, args[0], args[1], args[2], args[3]);
                    break;
                default:
                    sendMessage(player, "teleport.usage.player");
            }
        } else {
            if (args.length == 2) {
                handleTeleportPlayerToPlayerConsole(sender, args[0], args[1]);
            } else {
                sendMessage(sender, "teleport.usage.console");
            }
        }
    }

    private void handleTeleportPlayer(Player sender, String targetName) {
        Player target = Bukkit.getPlayer(targetName);
        if (target != null) {
            sender.teleport(target.getLocation());
            sender.sendMessage(LangFile.getString("teleport.teleported")
                    .replaceAll("%loc%", target.getName())
            );
        } else {
            sendMessage(sender, "invalid-target");
        }
    }

    private void handleTeleportPlayerToPlayer(Player sender, String targetName, String target2Name) {
        Player target = Bukkit.getPlayer(targetName);
        Player target2 = Bukkit.getPlayer(target2Name);
        if (target != null && target2 != null) {
            target.teleport(target2.getLocation());
            sender.sendMessage(LangFile.getString("teleport.teleported-other")
                    .replaceAll("%player%", target.getName())
                    .replaceAll("%loc%", target2.getName())
            );
        } else {
            sendMessage(sender, "invalid-target");
        }
    }

    private void handleTeleportToLocation(Player sender, String xStr, String yStr, String zStr) {
        try {
            double x = Double.parseDouble(xStr);
            double y = Double.parseDouble(yStr);
            double z = Double.parseDouble(zStr);
            Location loc = new Location(sender.getWorld(), x, y, z, sender.getLocation().getYaw(), sender.getLocation().getPitch());
            sender.teleport(loc);
            sender.sendMessage(LangFile.getString("teleport.teleported")
                    .replaceAll("%player%", sender.getName())
                    .replaceAll("%loc%", x + " " + y + " " + z)
            );
        } catch (NumberFormatException e) {
            sendMessage(sender, "invalid-number");
        }
    }

    private void handleTeleportPlayerToLocation(Player sender, String targetName, String xStr, String yStr, String zStr) {
        try {
            Player target = Bukkit.getPlayer(targetName);
            if (target != null) {
                double x = Double.parseDouble(xStr);
                double y = Double.parseDouble(yStr);
                double z = Double.parseDouble(zStr);
                Location loc = new Location(target.getWorld(), x, y, z, target.getLocation().getYaw(), target.getLocation().getPitch());
                target.teleport(loc);
                sender.sendMessage(LangFile.getString("teleport.teleported-other")
                        .replaceAll("%player%", target.getName())
                        .replaceAll("%loc%", x + " " + y + " " + z)
                );
            } else {
                sendMessage(sender, "invalid-target");
            }
        } catch (NumberFormatException e) {
            sendMessage(sender, "invalid-number");
        }
    }

    private void handleTeleportPlayerToPlayerConsole(CommandSender sender, String targetName, String target2Name) {
        Player target = Bukkit.getPlayer(targetName);
        Player target2 = Bukkit.getPlayer(target2Name);
        if (target != null && target2 != null) {
            target.teleport(target2);
            sender.sendMessage(LangFile.getString("teleport.teleported-other")
                    .replaceAll("%player%", target.getName())
                    .replaceAll("%loc%", target2.getName())
            );
        } else {
            sendMessage(sender, "invalid-target");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        switch (args.length) {
            case 1:
                String partialName1 = args[0].toLowerCase();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().toLowerCase().startsWith(partialName1)) {
                        completions.add(player.getName());
                    }
                }
                break;
            case 2:
                String partialName2 = args[1].toLowerCase();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getName().toLowerCase().startsWith(partialName2)) {
                        completions.add(player.getName());
                    }
                }
                break;
            case 3:
            case 4:
                completions.add("<number>");
                break;
            default:
                break;
        }
        return completions;
    }
}
