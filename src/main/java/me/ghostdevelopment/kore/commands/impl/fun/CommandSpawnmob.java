package me.ghostdevelopment.kore.commands.impl.fun;

import lombok.Getter;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.utils.Console;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "spawnmob", permission = "kore.spawnmob", tabCompleter = true)
public class CommandSpawnmob extends KoreCommand {

    @Getter
    private static final ArrayList<EntityType> entities = new ArrayList<>();

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!SettingsFile.getFile().getBoolean("spawnmob.enabled")) {
            sender.sendMessage(LangFile.getString("command-disabled"));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(LangFile.getString("spawnmob.usage"));
            return;
        }

        Player player = (Player) sender;

        if (args.length < 1 || args.length > 2) {
            player.sendMessage(LangFile.getString("spawnmob.usage"));
            return;
        }

        EntityType entityType;
        try {
            entityType = EntityType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage(LangFile.getString("spawnmob.invalid"));
            return;
        }

        int entityNum = 1;
        if (args.length == 2) {
            try {
                entityNum = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(LangFile.getString("spawnmob.invalid"));
                return;
            }
        }

        spawnEntities(player, entityType, entityNum);
    }

    private void spawnEntities(Player player, EntityType entityType, int entityNum) {
        if (SettingsFile.getFile().getBoolean("spawnmob.async")) {
            Bukkit.getScheduler().runTask(Kore.getInstance(), () -> spawnEntitiesSync(player, entityType, entityNum));
        } else {
            spawnEntitiesSync(player, entityType, entityNum);
        }
    }

    private void spawnEntitiesSync(Player player, EntityType entityType, int entityNum) {
        try {
            for (int i = 0; i < entityNum; i++) {
                player.getWorld().spawn(player.getLocation(), entityType.getEntityClass());
            }
            player.sendMessage(LangFile.getString("spawnmob.spawned")
                    .replaceAll("%num%", String.valueOf(entityNum))
            );
        } catch (Exception e) {
            Console.warning(e + "\n\nUnable to spawn entity");
            player.sendMessage(LangFile.getString("spawnmob.error"));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            String partialName = args[0].toLowerCase();
            for (EntityType entityType : entities) {
                String entityName = entityType.name().toLowerCase();
                if (entityName.startsWith(partialName)) {
                    completions.add(entityName.toUpperCase());
                }
            }
        }

        return completions;
    }
}
