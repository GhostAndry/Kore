package me.ghostdevelopment.kore.commands.impl.player;

import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@SuppressWarnings("ALL")
@CommandInfo(name = "trash", permission = "kore.trash")
public class CommandTrash extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(SettingsFile.getFile().getBoolean("trash.enabled"))) {
            sender.sendMessage(LangFile.getString("command-disabled"));
            return;
        }

        if (sender instanceof Player) {

            Player player = (Player) sender;

            Inventory inventory = Bukkit.createInventory(player, 54, Color.Color("&cTrash"));

            player.openInventory(inventory);

        } else {
            sender.sendMessage(LangFile.getString("only-players"));
        }
    }
}
