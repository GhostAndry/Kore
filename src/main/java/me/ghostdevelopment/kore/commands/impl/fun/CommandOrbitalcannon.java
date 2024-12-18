package me.ghostdevelopment.kore.commands.impl.fun;

import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "orbitalcannon", permission = "kore.orbitalcannon", tabCompleter = true)
public class CommandOrbitalcannon extends KoreCommand {

    private static final int EXPLOSION_RADIUS = 150;

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!SettingsFile.getFile().getBoolean("orbitalcannon.enabled")) {
            sender.sendMessage(LangFile.getString("command-disabled"));
            return;
        }

        if (args.length != 1 && args.length != 3) {
            sender.sendMessage(LangFile.getString("orbitalcannon.usage"));
            return;
        }

        Location location;
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(LangFile.getString("invalid-target"));
                return;
            }
            location = target.getLocation();
            if (SettingsFile.getFile().getBoolean("orbitalcannon.tell-to-victim")) {
                target.sendMessage(LangFile.getString("orbitalcannon.tell"));
            }
        } else {
            World world = sender.getServer().getWorlds().get(0); // Default to the first world
            try {
                int x = Integer.parseInt(args[0]);
                int y = Integer.parseInt(args[1]);
                int z = Integer.parseInt(args[2]);
                location = new Location(world, x, y, z);
            } catch (NumberFormatException e) {
                sender.sendMessage(LangFile.getString("orbitalcannon.usage"));
                return;
            }
        }

        if (location.getWorld() != null) {
            World world = location.getWorld();
            world.strikeLightningEffect(location);
            for (int i = 0; i < 3; i++) {
                world.createExplosion(location, EXPLOSION_RADIUS, false);
            }
        } else {
            sender.sendMessage(LangFile.getString("orbitalcannon.usage"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String partialName = args[0].toLowerCase();

            completions.add("<x>");
            completions.add("<y>");
            completions.add("<z>");

            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                String playerName = player.getName();
                if (playerName.toLowerCase().startsWith(partialName)) {
                    completions.add(playerName);
                }
            }

        } else if (args.length == 2 || args.length == 3) {
            completions.add("<x>");
            completions.add("<y>");
            completions.add("<z>");
        }

        return completions;
    }
}
