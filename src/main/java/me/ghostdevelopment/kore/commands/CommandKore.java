package me.ghostdevelopment.kore.commands;

import me.ghostdevelopment.kore.Files.FreezeLocFile;
import me.ghostdevelopment.kore.Files.HomesFile;
import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.Utils;
import me.ghostdevelopment.kore.Files.WarpsFile;
import me.ghostdevelopment.kore.freeze.FreezeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.ghostdevelopment.kore.NotNull;

import java.util.Objects;

@SuppressWarnings({"NullableProblems", "DataFlowIssue", "DuplicatedCode"})
public class CommandKore implements CommandExecutor {

    Kore plugin;
    public CommandKore(Kore plugin){this.plugin=plugin;}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        Player p = (Player) sender;

        if(!(p.hasPermission("kore.help")||p.hasPermission("kore.*")||p.hasPermission("*"))){
            p.sendMessage(Utils.Color("&aThis server is running &b&lKore &a1.0-SNAPSHOT"));
            return false;
        }
        if(args.length==0){
            p.sendMessage(Utils.Color("&aThis server is running &b&lKore &a1.0-SNAPSHOT"));
            p.sendMessage(Utils.Color("&aUse /kore help for help"));
        } else if (args.length==1) {
            if(args[0].equalsIgnoreCase("help")){
                p.sendMessage(Utils.Color(Objects.requireNonNull(plugin.getConfig().getString("messages.general-help")).replaceAll("%prefix%", Objects.requireNonNull(plugin.getConfig().getString("messages.prefix")))));
            } else if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                WarpsFile.reload();
                HomesFile.reload();
                FreezeLocFile.reload();
                p.sendMessage(Utils.Color("%prefix% &aReloaded".replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))));
            } else if (args[0].equalsIgnoreCase("setfreezeloc")) {
                p.sendMessage(Utils.Color(plugin.getConfig().getString("freeze.freezeLoc-set")
                        .replaceAll("%prefix%", plugin.getConfig().getString("messages.prefix"))
                ));
                Functions.setFreezeLoc(FreezeManager.name,p.getLocation());
            }else{
                p.sendMessage(Utils.Color("&aThis server is running &b&lKore &a1.0-SNAPSHOT"));
                p.sendMessage(Utils.Color("&aUse /kore help for help"));
            }

        }

        return true;
    }
}
