package com.cooleg.civutils.utils;


import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EventHandling implements Listener {

    private CivUtils civUtils;

    public EventHandling(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        try {
            if (civUtils.getConfig().getBoolean("options.KickOnDeath")) {
                event.getEntity().getPlayer().kickPlayer(ChatColor.RED + "You died...");
            }
            if (civUtils.getConfig().getBoolean("options.UnWhitelistOnDeath")) {
               event.getEntity().getPlayer().setWhitelisted(false);
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Hey there man i would love to possibly kick or unwhitelist people but small problem your config is brokey");
        }

    }

}
