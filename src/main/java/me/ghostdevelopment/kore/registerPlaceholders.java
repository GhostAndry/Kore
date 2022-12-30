package me.ghostdevelopment.kore;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ghostdevelopment.kore.commands.admin.CommandFly;
import me.ghostdevelopment.kore.commands.admin.CommandGod;
import me.ghostdevelopment.kore.commands.admin.CommandVanish;
import org.bukkit.entity.Player;

@SuppressWarnings({"NullableProblems", "FieldCanBeLocal"})
public class registerPlaceholders extends PlaceholderExpansion {

    private final String pluginName = "kore";
    private final String authorName = "GhostAndry";
    private final String version = "1.3-TEST";
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
        if(player==null)return "";

        if(params.equals("vanished")) return String.valueOf(CommandVanish.vanished.contains(player));
        if(params.equals("flying")) return String.valueOf(CommandFly.flyList.contains(player));
        if(params.equals("godmode")) return  String.valueOf(CommandGod.godmode.contains(player));

    return null;
    }
}
