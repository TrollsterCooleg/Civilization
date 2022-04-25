package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import org.bukkit.entity.Player;

public class SetPos {
    private CivUtils civUtils;
    private Player p;
    private String[] args;
    public SetPos(String[] args, CivUtils civUtils, Player player) {
        // Fucking constructor and location shit
        // imma be real this is pretty basic shit
        this.civUtils = civUtils;
        this.p = player;
        this.args = args;
        saveLoc();
        return;
    }

    public void saveLoc() {
        if (args.length < 3) {
            // Pretty simple shit just saves coords to config.yml
            civUtils.getConfig().set(args[1]+".x", p.getLocation().getBlockX());
            civUtils.getConfig().set(args[1]+".y", p.getLocation().getBlockY());
            civUtils.getConfig().set(args[1]+".z", p.getLocation().getBlockZ());
            civUtils.getConfig().set(args[1]+".world", p.getLocation().getWorld().getName());
            civUtils.saveConfig();
            return;
        }
        return;

    }

}
