package me.ghostdevelopment.kore.commands.impl.player;

import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@CommandInfo(name = "home", permission = "kore.home", tabCompleter = true)
public class CommandHome extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("home.enabled")) {
            sendMessage(sender, "command-disabled");
            return;
        }

        if (!(sender instanceof Player)) {
            sendMessage(sender, "only-players");
            return;
        }

        Player player = (Player) sender;
        if (args.length == 0) {
            handleHomeTeleport(player);
        } else if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "set":
                    handleSetHome(player);
                    break;
                case "remove":
                    handleRemoveHome(player);
                    break;
                default:
                    sendMessage(player, "home.usage");
                    break;
            }
        } else {
            sendMessage(player, "home.usage");
        }
    }

    private void handleHomeTeleport(Player player) {
        if (Functions.checkHome(player)) {
            Location home = Functions.getHomeLoc(player);
            player.teleport(home);
            sendMessage(player, "home.teleported");
        } else {
            sendMessage(player, "home.not-set");
        }
    }

    private void handleSetHome(Player player) {
        if (!Functions.checkHome(player)) {
            try {
                Functions.addHome(player);
                sendMessage(player, "home.set");
            } catch (Exception e) {
                sendMessage(player, "&cAn error occurred while setting home.");
            }
        } else {
            sendMessage(player, "home.already-set");
        }
    }

    private void handleRemoveHome(Player player) {
        if (Functions.checkHome(player)) {
            try {
                Functions.delHome(player);
                sendMessage(player, "home.removed");
            } catch (Exception e) {
                sendMessage(player, "&cAn error occurred while removing home.");
            }
        } else {
            sendMessage(player, "home.not-set");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("set");
            completions.add("remove");
        }

        return null;
    }
}
