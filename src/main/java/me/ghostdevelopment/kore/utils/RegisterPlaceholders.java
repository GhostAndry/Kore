package me.ghostdevelopment.kore.utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.commands.impl.admin.CommandFly;
import me.ghostdevelopment.kore.commands.impl.admin.CommandGod;
import me.ghostdevelopment.kore.commands.impl.admin.CommandVanish;
import org.bukkit.entity.Player;

@SuppressWarnings({"NullableProblems", "FieldCanBeLocal"})
public class RegisterPlaceholders extends PlaceholderExpansion {

    private final String pluginName = "kore";
    private final String authorName = "GhostAndry";
    private final String version = Kore.getInstance().getDescription().getVersion();

    @Override
    public String getIdentifier() {
        return pluginName;
    }

    @Override
    public String getAuthor() {
        return authorName;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null)
            return "";

        if (params.equals("vanished"))
            return String.valueOf(CommandVanish.getVanished().contains(player));
        if (params.equals("flying"))
            return String.valueOf(CommandFly.getFlyingPlayers().contains(player));
        if (params.equals("godmode"))
            return String.valueOf(CommandGod.getGodPlayers().contains(player));

        return null;
    }
}