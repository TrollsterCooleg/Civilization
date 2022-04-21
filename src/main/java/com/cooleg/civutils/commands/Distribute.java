package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Distribute {
    private CivUtils civUtils;
    public Distribute(CivUtils civUtils) {
        this.civUtils = civUtils;
        spread();
        return;
    }

    public void spread() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (String string : civUtils.getConfig().getKeys(false)) {
                String perm = "civ.team."+string;
                if (p.hasPermission(perm)) {
                    Double x = civUtils.getConfig().getDouble(string+".x");
                    Double y = civUtils.getConfig().getDouble(string+".y");
                    Double z = civUtils.getConfig().getDouble(string+".z");
                    World world = Bukkit.getWorld(civUtils.getConfig().getString(string+".world"));
                    Location loc = new Location(world,x,y,z);
                    p.teleport(loc);
                    break;
                }

            }

        }
        return;

    }

}
