package me.ghostdevelopment.kore.commands.impl.admin;

import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.files.StorageFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CommandInfo(name = "warp", permission = "kore.warp", tabCompleter = true)
public class CommandWarp extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!SettingsFile.getFile().getBoolean("warp.enabled")) {
            sendMessage(sender, "command-disabled");
            return;
        }

        if (args.length < 1 || args.length > 2) {
            sendMessage(sender, sender instanceof Player ? "warp.usage.player" : "warp.usage.console");
            return;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "add":
                handleAddWarp(sender, args);
                break;
            case "remove":
                handleRemoveWarp(sender, args);
                break;
            default:
                handleWarp(sender, args);
                break;
        }
    }

    private void handleAddWarp(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "warp.usage.console");
            return;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("kore.warp.admin")) {
            sendMessage(player, "no-permissions");
            return;
        }

        if (args.length != 2) {
            sendMessage(player, "warp.usage.admin");
            return;
        }

        String warpName = args[1];
        if (Functions.checkWarp(warpName)) {
            player.sendMessage(LangFile.getString("warp.admin.already-exists")
                    .replace("%warp%", warpName)
            );
            return;
        }

        Functions.addWarp(player.getLocation(), warpName);
        player.sendMessage(LangFile.getString("warp.admin.added")
                .replace("%warp%", warpName)
        );
    }

    private void handleRemoveWarp(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, "warp.usage.console");
            return;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("kore.warp.admin") && !player.isOp()) {
            sendMessage(player, "no-permissions");
            return;
        }

        if (args.length != 2) {
            sendMessage(player, "warp.usage.admin");
            return;
        }

        String warpName = args[1];
        if (!Functions.checkWarp(warpName)) {
            sendMessage(player, "warp.not-found");
            return;
        }

        Functions.delWarp(warpName);
        player.sendMessage(LangFile.getString("warp.admin.removed")
                .replace("%warp%", warpName)
        );
    }

    private void handleWarp(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length != 2) {
                sendMessage(sender, "warp.usage.console");
                return;
            }

            String warpName = args[0];
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sendMessage(sender, "invalid-target");
                return;
            }

            teleportToWarp(sender, target, warpName);
            return;
        }

        Player player = (Player) sender;
        if (args.length == 1) {
            String warpName = args[0];
            teleportToWarp(player, player, warpName);
        } else if (args.length == 2) {
            String warpName = args[0];
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sendMessage(player, "invalid-target");
                return;
            }

            teleportToWarp(player, target, warpName);
        } else {
            sendMessage(player, "warp.usage.player");
        }
    }

    private void teleportToWarp(CommandSender sender, Player target, String warpName) {
        if (!Functions.checkWarp(warpName)) {
            sendMessage(sender, "warp.not-found");
            return;
        }

        target.teleport(Functions.getWarpLoc(warpName));
        target.sendMessage(LangFile.getString("warp.warped")
                .replace("%warp%", warpName)
        );
        if (sender instanceof Player) {
            sender.sendMessage(LangFile.getString("warp.warped-other")
                    .replaceAll("%warp%", warpName)
                    .replace("%player%", target.getName())
            );
        } else {
            sender.sendMessage(LangFile.getString("warp.warped-other")
                    .replaceAll("%warp%", warpName)
                    .replace("%player%", target.getName())
            );
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        ConfigurationSection warpsSection = StorageFile.getFile().getConfigurationSection("warps");
        if (warpsSection == null) {
            if (sender.hasPermission("kore.warp.admin") || sender.hasPermission("kore.warp.*") || sender.hasPermission("kore.*") || sender.hasPermission("*") || sender.isOp()) {
                completions.add("add");
                completions.add("remove");
            }
            return completions;
        }

        Set<String> warps = warpsSection.getKeys(false);

        if (args.length == 1) {
            String partialName = args[0].toLowerCase();

            if (sender.hasPermission("kore.warp.admin") || sender.hasPermission("kore.warp.*") || sender.hasPermission("kore.*") || sender.hasPermission("*") || sender.isOp()) {
                completions.add("add");
                completions.add("remove");
            }

            for (String warp : warps) {
                if (warp.toLowerCase().startsWith(partialName)) {
                    completions.add(warp);
                }
            }
        } else if (args.length == 2) {
            String subCommand = args[0].toLowerCase();
            String partialName = args[1].toLowerCase();

            if ((subCommand.equals("add") || subCommand.equals("remove")) &&
                    (sender.hasPermission("kore.warp.admin") || sender.hasPermission("kore.warp.*") || sender.hasPermission("kore.*") || sender.hasPermission("*") || sender.isOp())) {

                if (subCommand.equals("add")) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getName().toLowerCase().startsWith(partialName)) {
                            completions.add(player.getName());
                        }
                    }
                } else {
                    for (String warp : warps) {
                        if (warp.toLowerCase().startsWith(partialName)) {
                            completions.add(warp);
                        }
                    }
                }
            }
        }

        return completions;
    }
}
