package me.ghostdevelopment.kore.commands.impl.admin;

import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(name = "setspawn", permission = "kore.setspawn")
public class CommandSetspawn extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!SettingsFile.getFile().getBoolean("spawn.enabled")) {
            sendMessage(sender, "command-disabled");
            return;
        }

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 0) {
                Functions.setSpawnLoc(player.getLocation());
                player.sendMessage(LangFile.getString("spawn.set")
                        .replaceAll("%x%", String.valueOf(player.getLocation().getX()))
                        .replaceAll("%y%", String.valueOf(player.getLocation().getY()))
                        .replaceAll("%z%", String.valueOf(player.getLocation().getZ()))
                );
                return;
            }
            sendMessage(player, "spawn.usage.admin");
        } else {
            sendMessage(sender, "only-players");
        }
    }
}
