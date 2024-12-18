package me.ghostdevelopment.kore.commands.impl.admin;

import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

@CommandInfo(name = "sun", permission = "kore.sun")
public class CommandSun extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        for (World world : Bukkit.getServer().getWorlds()) {
            world.setTime(1000);
            world.setStorm(false);
            world.setWeatherDuration(1000000000);
        }
    }
}
