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
    }

    public void spread() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (String string : civUtils.teamCache) {
                String perm = "civ.team."+string;
                if (p.hasPermission(perm)) {
                    p.addScoreboardTag(string);
                    double x = civUtils.getConfig().getDouble(string+".x");
                    double y = civUtils.getConfig().getDouble(string+".y");
                    double z = civUtils.getConfig().getDouble(string+".z");
                    World world = Bukkit.getWorld(civUtils.getConfig().getString(string+".world"));
                    Location loc = new Location(world,x,y,z);
                    p.teleport(loc);
                    break;
                }

            }

        }

    }

}
