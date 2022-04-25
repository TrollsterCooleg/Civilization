package com.cooleg.civutils.utils;

import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CachePlayers implements Listener {
    private CivUtils civUtils;
    
    public CachePlayers(CivUtils civUtils) {
        this.civUtils = civUtils;
    }
    
    public void cacheAll() {
        for (Player p : civUtils.playerCache.keySet()) {civUtils.playerCache.remove(p);}
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (String string : civUtils.teamCache) {
                String perm = "civ.team."+string;
                if (p.hasPermission(perm)) {
                    civUtils.playerCache.put(p, string);
                }
            }
        }
    }
    
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        for (String string : civUtils.teamCache) {
            String perm = "civ.team."+string;
            if (e.getPlayer().hasPermission(perm)) {
                civUtils.playerCache.put(e.getPlayer(), string);
            }
        }
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        civUtils.playerCache.remove(e.getPlayer());
    }

}
