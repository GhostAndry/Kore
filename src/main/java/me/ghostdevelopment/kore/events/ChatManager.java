package me.ghostdevelopment.kore.events;

import me.clip.placeholderapi.PlaceholderAPI;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.files.SettingsFile;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatManager implements Listener {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private final Kore plugin = Kore.getInstance();
    private final LuckPerms luckPerms;

    public ChatManager() {
        this.luckPerms = plugin.getServer().getServicesManager().load(LuckPerms.class);
        if (this.luckPerms == null) {
            Bukkit.getLogger().severe("LuckPerms API not loaded. Make sure LuckPerms is installed!");
            throw new IllegalStateException("LuckPerms API not loaded. Make sure LuckPerms is installed!");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!SettingsFile.getFile().getBoolean("chat.enabled")) {
            return;
        }

        Player player = event.getPlayer();
        String originalMessage = event.getMessage();
        
        String filteredMessage = filterMessage(originalMessage, SettingsFile.getFile().getStringList("chat.blacklist-words"), player);
        
        String formattedMessage = formatMessage(player, filteredMessage);
        
        if (plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            formattedMessage = PlaceholderAPI.setPlaceholders(player, formattedMessage);
        }

        formattedMessage = applyColors(formattedMessage);
        
        Bukkit.broadcastMessage(formattedMessage);
        event.setCancelled(true);
    }

    private String filterMessage(String message, List<String> blacklist, Player player) {

        if (player.hasPermission("kore.chatbypass") || player.hasPermission("kore.*") || player.isOp()) {
            return message;
        }

        for (String word : blacklist) {
            String censoredWord = "*".repeat(word.length());
            message = message.replaceAll("(?i)\\b" + Pattern.quote(word) + "\\b", censoredWord);
        }
        return message;
    }

    private String formatMessage(Player player, String message) {
        CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        String group = metaData.getPrimaryGroup();
        String format = SettingsFile.getFile().getString("chat.group-formats." + group,
                SettingsFile.getFile().getString("chat.format"));

        return format
                .replace("%prefix%", getOrDefault(metaData.getPrefix()))
                .replace("%suffix%", getOrDefault(metaData.getSuffix()))
                .replace("%prefixes%", String.join("", metaData.getPrefixes().values()))
                .replace("%suffixes%", String.join("", metaData.getSuffixes().values()))
                .replace("%world%", player.getWorld().getName())
                .replace("%name%", player.getName())
                .replace("%displayname%", player.getDisplayName())
                .replace("%username-color%", getOrDefault(metaData.getMetaValue("username-color")))
                .replace("%message-color%", getOrDefault(metaData.getMetaValue("message-color")))
                .replace("%message%", message)
                .replace("%", "%%");
    }

    private String getOrDefault(String value) {
        return value != null ? value : "";
    }

    private String applyColors(String message) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        return translateHexColorCodes(message);
    }

    private String translateHexColorCodes(String message) {
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder builder = new StringBuilder();

        while (matcher.find()) {
            String hexCode = matcher.group(1);
            matcher.appendReplacement(builder, ChatColor.COLOR_CHAR + "x"
                    + ChatColor.COLOR_CHAR + hexCode.charAt(0) + ChatColor.COLOR_CHAR + hexCode.charAt(1)
                    + ChatColor.COLOR_CHAR + hexCode.charAt(2) + ChatColor.COLOR_CHAR + hexCode.charAt(3)
                    + ChatColor.COLOR_CHAR + hexCode.charAt(4) + ChatColor.COLOR_CHAR + hexCode.charAt(5));
        }
        matcher.appendTail(builder);
        return builder.toString();
    }
}
