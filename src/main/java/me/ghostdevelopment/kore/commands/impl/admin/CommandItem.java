package me.ghostdevelopment.kore.commands.impl.admin;

import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(name = "item", permission = "kore.item", tabCompleter = true)
public class CommandItem extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(SettingsFile.getFile().getBoolean("item.enabled"))) {
            sender.sendMessage(LangFile.getString("command-disabled"));
            return;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(LangFile.getString("only-player"));
            return;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(LangFile.getString("item.usage"));
            return;
        }

        String materialName = args[0].toUpperCase();
        Material material = Material.getMaterial(materialName);

        if (material == null) {
            player.sendMessage(LangFile.getString("item.invalid-material").replace("%material%", materialName));
            return;
        }

        int quantity = 1;
        if (args.length >= 2) {
            try {
                quantity = Integer.parseInt(args[1]);
                if (quantity <= 0) {
                    player.sendMessage(LangFile.getString("item.invalid-quantity"));
                    return;
                }
            } catch (NumberFormatException e) {
                player.sendMessage(LangFile.getString("item.invalid-quantity-format"));
                return;
            }
        }

        ItemStack itemStack = new ItemStack(material, quantity);
        player.getInventory().addItem(itemStack);
        player.sendMessage(LangFile.getString("item.success")
                .replace("%quantity%", String.valueOf(quantity))
                .replace("%material%", material.name()));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            for (Material material : Material.values()) {
                if (material.name().toUpperCase().startsWith(args[0].toUpperCase())) {
                    completions.add(material.name());
                }
            }
        } else if (args.length == 2) {
            for (int i = 1; i < 65; i++) {
                completions.add(String.valueOf(i));
            }
        }

        return completions;
    }
}
