package me.ghostdevelopment.kore.PlaceholderAPI;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.ghostdevelopment.kore.Kore;
import me.ghostdevelopment.kore.commands.admin.CommandVanish;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RegisterPlaceholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "kore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "GhostAndry";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0-SNAPSHOT";
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
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if(player == null){
            return "";
        }
        if(params.equals("isVanished")){
            if(CommandVanish.vanished.contains(player)){
                return "true";
            }else {
                return "false";
            }
        }
        return null;
    }
}
