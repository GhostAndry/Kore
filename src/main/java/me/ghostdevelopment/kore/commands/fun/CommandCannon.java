package me.ghostdevelopment.kore.commands.fun;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.NotNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandCannon implements CommandExecutor {

    Kore plugin;
    public CommandCannon(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {





        return true;
    }
}
