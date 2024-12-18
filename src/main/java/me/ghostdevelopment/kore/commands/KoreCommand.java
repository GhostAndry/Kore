package me.ghostdevelopment.kore.commands;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public abstract class KoreCommand implements CommandExecutor, TabCompleter {
    private final CommandInfo commandInfo;

    public KoreCommand() {
        this.commandInfo = Objects.requireNonNull(getClass().getDeclaredAnnotation(CommandInfo.class),
                "Commands must have @CommandInfo annotations");
    }

    public CommandInfo getCommandInfo() {
        return commandInfo;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (Kore.getInstance().getConfig().getBoolean("commands.asynchronously")) {
            Bukkit.getScheduler().runTask(Kore.getInstance(), () -> executeCommand(sender, args));
            return true;
        } else {
            return executeCommand(sender, args);
        }
    }

    private boolean executeCommand(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            sender.sendMessage(LangFile.getString("no-permissions"));
            return false;
        }
        execute(sender, args);
        return true;
    }

    private boolean hasPermission(CommandSender sender) {
        if (commandInfo.permission().isEmpty()) {
            return true;
        }
        for (String permission : getPermissions()) {
            if (sender.hasPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getPermissions() {
        List<String> permissions = new ArrayList<>();
        permissions.add(commandInfo.permission());
        permissions.add(commandInfo.permission2());
        permissions.add(commandInfo.permission3());
        permissions.add("kore.*");
        permissions.add("*");
        return permissions;
    }

    protected void sendMessage(@NotNull CommandSender sender, String message) {
        sender.sendMessage(Color.Color(LangFile.getString(message)
                .replaceAll("%prefix%", LangFile.getString("prefix"))
        ));
    }

    protected void sendMessage(@NotNull Player player, String message) {
        player.sendMessage(Color.Color(LangFile.getString(message)
                .replaceAll("%prefix%", LangFile.getString("prefix"))
        ));
    }


    public abstract void execute(CommandSender sender, String[] args);

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
