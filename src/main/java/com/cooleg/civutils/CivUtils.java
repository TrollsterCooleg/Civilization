package com.cooleg.civutils;

import com.cooleg.civutils.security.SecureHandler;
import com.cooleg.civutils.utils.BorderUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Set;

public final class CivUtils extends JavaPlugin {

    public boolean enabled = false;
    public boolean border = false;
    public Set<String> teamCache;
    public HashMap<Player, String> playerCache = new HashMap<>();
    public BorderUtils borderUtils;

    @Override
    public void onEnable() {
        // Important Startup
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        teamCache = this.getConfig().getKeys(false);
        borderUtils = new BorderUtils(this);
        Bukkit.getPluginManager().registerEvents(new SecureHandler(this), this);
        getCommand("civutils").setExecutor(new CivCmd(this,borderUtils));
    }

    @Override
    public void onDisable() {

    }

}
