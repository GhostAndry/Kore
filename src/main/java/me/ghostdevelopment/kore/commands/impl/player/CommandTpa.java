package me.ghostdevelopment.kore.commands.impl.player;

import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

@CommandInfo(name = "tpa", permission = "kore.tpa", tabCompleter = true)
public class CommandTpa extends KoreCommand {

    private final Map<UUID, UUID> pendingRequests = new HashMap<>();
    private final Map<UUID, Long> requestExpirationTimes = new HashMap<>();

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!SettingsFile.getFile().getBoolean("tpa.enabled")) {
            sendMessage(sender, "command-disabled");
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(LangFile.getString("only-players"));
            return;
        }

        Player player = (Player) sender;

        if (args.length != 1) {
            sender.sendMessage(LangFile.getString("tpa.usage"));
            return;
        }

        String argument = args[0].toLowerCase();

        switch (argument) {
            case "accept":
                acceptRequest(player);
                break;

            case "deny":
                denyRequest(player);
                break;

            default:
                sendTpaRequest(player, argument);
                break;
        }
    }

    private void sendTpaRequest(Player sender, String targetName) {
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sender.sendMessage(LangFile.getString("tpa.player-not-found"));
            return;
        }

        if (target.equals(sender)) {
            sender.sendMessage(LangFile.getString("tpa.self-tpa-request"));
            return;
        }

        if (pendingRequests.containsKey(target.getUniqueId()) && pendingRequests.get(target.getUniqueId()).equals(sender.getUniqueId())) {
            sender.sendMessage(LangFile.getString("tpa.already-requested"));
            return;
        }

        int expirationTime = SettingsFile.getFile().getInt("tpa.expiration");

        pendingRequests.put(target.getUniqueId(), sender.getUniqueId());
        requestExpirationTimes.put(target.getUniqueId(), System.currentTimeMillis() + expirationTime * 1000L);

        sender.sendMessage(LangFile.getString("tpa.request")
                .replaceAll("%sender%", sender.getName())
                .replaceAll("%player%", target.getName()));

        target.sendMessage(LangFile.getString("tpa.request")
                .replaceAll("%sender%", sender.getName())
                .replaceAll("%player%", target.getName()));

        Bukkit.getScheduler().runTaskLater(Bukkit.getPluginManager().getPlugin("Kore"), () -> {
            if (pendingRequests.containsKey(target.getUniqueId()) && pendingRequests.get(target.getUniqueId()).equals(sender.getUniqueId())) {
                pendingRequests.remove(target.getUniqueId());
                requestExpirationTimes.remove(target.getUniqueId());

                sender.sendMessage(LangFile.getString("tpa.expired.from")
                        .replaceAll("%player%", target.getName())
                );

                target.sendMessage(LangFile.getString("tpa.expired.to")
                        .replaceAll("%player%", sender.getName())
                );
            }
        }, expirationTime * 20L);
    }

    private void acceptRequest(Player target) {
        UUID targetUUID = target.getUniqueId();
        if (!pendingRequests.containsKey(targetUUID)) {
            target.sendMessage(LangFile.getString("tpa.no-pending-request"));
            return;
        }

        UUID senderUUID = pendingRequests.get(targetUUID);
        Player sender = Bukkit.getPlayer(senderUUID);

        if (sender == null) {
            target.sendMessage(LangFile.getString("tpa.player-not-found"));
            pendingRequests.remove(targetUUID);
            requestExpirationTimes.remove(targetUUID);
            return;
        }

        long expirationTime = requestExpirationTimes.get(targetUUID);
        if (System.currentTimeMillis() > expirationTime) {

            sender.sendMessage(LangFile.getString("tpa.expired.from")
                    .replaceAll("%player%", target.getName())
            );

            target.sendMessage(LangFile.getString("tpa.expired.to")
                    .replaceAll("%player%", sender.getName())
            );

            pendingRequests.remove(targetUUID);
            requestExpirationTimes.remove(targetUUID);
            return;
        }

        sender.teleport(target.getLocation());

        sender.sendMessage(LangFile.getString("tpa.accepted")
                .replaceAll("%sender%", sender.getName())
                .replaceAll("%player%", target.getName()));

        target.sendMessage(LangFile.getString("tpa.accepted")
                .replaceAll("%sender%", sender.getName())
                .replaceAll("%player%", target.getName()));

        pendingRequests.remove(targetUUID);
        requestExpirationTimes.remove(targetUUID);
    }

    private void denyRequest(Player target) {
        UUID targetUUID = target.getUniqueId();
        if (!pendingRequests.containsKey(targetUUID)) {
            target.sendMessage(LangFile.getString("tpa.no-pending-request"));
            return;
        }

        UUID senderUUID = pendingRequests.get(targetUUID);
        Player sender = Bukkit.getPlayer(senderUUID);

        if (sender == null) {
            target.sendMessage(LangFile.getString("tpa.player-not-found"));
            pendingRequests.remove(targetUUID);
            requestExpirationTimes.remove(targetUUID);
            return;
        }

        sender.sendMessage(LangFile.getString("tpa.denied.to")
                .replaceAll("%player%", target.getName())
        );

        target.sendMessage(LangFile.getString("tpa.denied.from")
                .replaceAll("%player%", sender.getName())
        );

        pendingRequests.remove(targetUUID);
        requestExpirationTimes.remove(targetUUID);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String partialName = args[0].toLowerCase();

            completions.add("accept");
            completions.add("deny");

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                String playerName = player.getName();
                if (playerName.toLowerCase().startsWith(partialName)) {
                    completions.add(playerName);
                }
            }
        }

        return completions;
    }
}
