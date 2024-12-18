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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChatEvents implements Listener {

    private final Kore plugin = Kore.getInstance();
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private LuckPerms luckPerms = Kore.getInstance().getServer().getServicesManager().load(LuckPerms.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(final AsyncPlayerChatEvent event) {
        final String originalMessage = event.getMessage();
        final Player player = event.getPlayer();

        List<String> blacklist = SettingsFile.getFile().getStringList("chat.blacklist");

        String message = censorMessage(originalMessage, blacklist);

        final CachedMetaData metaData = luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
        final String group = metaData.getPrimaryGroup();

        try {
            String format = plugin.getConfig().getString(plugin.getConfig().getString("chat.group-formats." + group) != null ? "chat.group-formats." + group : "chat.chat-format")
                    .replace("%prefix%", metaData.getPrefix() != null ? metaData.getPrefix() : "")
                    .replace("%suffix%", metaData.getSuffix() != null ? metaData.getSuffix() : "")
                    .replace("%prefixes%", metaData.getPrefixes().keySet().stream().map(key -> metaData.getPrefixes().get(key)).collect(Collectors.joining()))
                    .replace("%suffixes%", metaData.getSuffixes().keySet().stream().map(key -> metaData.getSuffixes().get(key)).collect(Collectors.joining()))
                    .replace("%world%", player.getWorld().getName())
                    .replace("%name%", player.getName())
                    .replace("%displayname%", player.getDisplayName())
                    .replace("%username-color%", metaData.getMetaValue("username-color") != null ? metaData.getMetaValue("username-color") : "")
                    .replace("%message-color%", metaData.getMetaValue("message-color") != null ? metaData.getMetaValue("message-color") : "");

            format = colorize(translateHexColorCodes(plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(player, format) : format));

            event.setFormat(format.replace("%message%", player.hasPermission("kore.chat.colorcodes") && player.hasPermission("kore.chat.rgbcodes")
                    ? colorize(translateHexColorCodes(message)) : player.hasPermission("kore.chat.colorcodes") ? colorize(message) : player.hasPermission("kore.chat.rgbcodes")
                    ? translateHexColorCodes(message) : message).replace("%", "%%"));
        } catch (Exception e) {

        }
    }
    
    private String colorize(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String translateHexColorCodes(final String message) {
        final char colorChar = ChatColor.COLOR_CHAR;
        final Matcher matcher = HEX_PATTERN.matcher(message);
        final StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);

        while (matcher.find()) {
            final String group = matcher.group(1);
            matcher.appendReplacement(buffer, colorChar + "x"
                    + colorChar + group.charAt(0) + colorChar + group.charAt(1)
                    + colorChar + group.charAt(2) + colorChar + group.charAt(3)
                    + colorChar + group.charAt(4) + colorChar + group.charAt(5));
        }

        return matcher.appendTail(buffer).toString();
    }

    private String censorMessage(String message, List<String> blacklist) {
        for (String word : blacklist) {
            String censoredWord = "*".repeat(word.length());
            message = message.replaceAll("(?i)\\b" + Pattern.quote(word) + "\\b", censoredWord);
        }
        return message;
    }
}
