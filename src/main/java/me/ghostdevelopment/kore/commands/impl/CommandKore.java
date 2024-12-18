package me.ghostdevelopment.kore.commands.impl;

import me.ghostdevelopment.kore.Functions;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.commands.CommandInfo;
import me.ghostdevelopment.kore.commands.KoreCommand;
import me.ghostdevelopment.kore.files.LangFile;
import me.ghostdevelopment.kore.files.SettingsFile;
import me.ghostdevelopment.kore.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
@CommandInfo(name = "kore", tabCompleter = true)
public class CommandKore extends KoreCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sendPluginInfo(sender);
            return;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "info":
                sendInfo(sender);
                break;
            case "reload":
                handleReload(sender);
                break;
            case "help":
                sendHelp(sender);
                break;
            case "lang":
            case "language":
                if (args.length == 2) {
                    setLanguage(sender, args[1]);
                } else {
                    sendPluginInfo(sender);
                }
                break;
            default:
                sendPluginInfo(sender);
                break;
        }
    }

    private void sendPluginInfo(CommandSender sender) {
        String version = Kore.getInstance().getDescription().getVersion();
        sender.sendMessage(Color.Color("\n&aThis server is running Kore v" + version + "\n"));
    }

    private void sendInfo(CommandSender sender) {
        String version = Kore.getInstance().getDescription().getVersion();
        sender.sendMessage(Color.Color(
                "&aKore " + version + "\n" +
                        "&aAuthor: &7GhostAndry\n" +
                        "&aGitHub: &b&nhttps://github.com/GhostAndry/Kore-Recoded\n"
        ));
    }

    private void handleReload(CommandSender sender) {
        if (sender instanceof Player && !sender.hasPermission("kore.reload")) {
            sender.sendMessage(LangFile.getString("no-permissions"));
            return;
        }

        try {
            Functions.reloadFiles();
            sender.sendMessage(LangFile.getString("reload.success"));
        } catch (Exception e) {
            Bukkit.getLogger().warning(e.getMessage());
            sender.sendMessage(LangFile.getString("reload.error"));
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(Color.Color("\n" +
                "&c<mandatory> &1[optional]\n" +
                "&7/kore <help|reload|info|(lang|language)>\n" +
                "&7/gamemode &c<gamemode> &1[player]\n" +
                "&7/gmc &1[player]\n" +
                "&7/gms &1[player]\n" +
                "&7/gma &1[player]\n" +
                "&7/gmsp &1[player]\n" +
                "&7/fly &1[player]\n" +
                "&7/teleport /tp &c<player> &1[player]\n" +
                "&7/heal &1[player]\n" +
                "&7/god &1[player]\n" +
                "&7/vanish /v &1[player]\n" +
                "&7/setspawn\n" +
                "&7/spawn &1[player]\n" +
                "&7/trash /disposal\n" +
                "&7/orbitalcannon &c<player | x y z> \n" +
                "&7/smite &c<player>\n" +
                "&7/kill &1[player]\n" +
                "&7/warp &c<add|remove> <name>\n" +
                "&7/warp &c<name> &1[player]\n" +
                "&7/home &1[set|remove]\n" +
                "&7/speed &c<value> &1[player] [type]\n" +
                "&7/killmobs\n" +
                "&7/spawnmob &c<mob> &1[num]\n" +
                "&7/tpa &c<accept|deny|player>"
        ));
    }

    private void setLanguage(CommandSender sender, String lang) {
        if (!LangFile.checkFileExists(lang)) {
            sender.sendMessage(LangFile.getString("messages.not-exist"));
            return;
        }

        SettingsFile.getFile().set("messages", lang);
        SettingsFile.save();
        Functions.reloadFiles();

        sender.sendMessage(LangFile.getString("messages.successfully-set")
                .replace("%lang%", LangFile.getString("lang-name")));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 1) {
            completions.add("info");
            completions.add("reload");
            completions.add("help");
            completions.add("lang");
            completions.add("language");
        } else if (args.length == 2 && (args[0].equalsIgnoreCase("lang") || args[0].equalsIgnoreCase("language"))) {
            completions.addAll(LangFile.getAvailableLanguages());
        }
        return completions;
    }
}
