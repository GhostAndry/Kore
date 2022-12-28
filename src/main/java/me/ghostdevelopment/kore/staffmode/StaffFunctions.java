package me.ghostdevelopment.kore.staffmode;

import org.bukkit.entity.Player;

import java.util.UUID;

public class StaffFunctions {

    public static void setStaffMode(Player player, Boolean tf){
        String name = player.getName();

        StaffFiles.get().set(name, tf);
        StaffFiles.save();
    }

}
