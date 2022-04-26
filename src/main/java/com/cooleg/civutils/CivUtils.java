package com.cooleg.civutils;

import com.cooleg.civutils.security.SecureHandler;
import com.cooleg.civutils.utils.BorderUtils;
import com.cooleg.civutils.utils.EventHandling;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Set;

public final class CivUtils extends JavaPlugin {

    // Preps variables, such as border being on or off, the team cache,
    // and also sets a variable for the borderUtils class beforehand conveniently
    public boolean enabled = false;
    public boolean border = false;
    public Set<String> teamCache;
    public BorderUtils borderUtils;

    @Override
    public void onEnable() {
        // Important Startup
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        try {
            teamCache = this.getConfig().getConfigurationSection("teams").getKeys(false);
        } catch (Exception e) {
            this.getLogger().severe("Somethings wrong in the config.yml");
        }
        borderUtils = new BorderUtils(this);
        Bukkit.getPluginManager().registerEvents(new EventHandling(this), this);
        Bukkit.getPluginManager().registerEvents(new SecureHandler(this), this);
        getCommand("civutils").setExecutor(new CivCmd(this,borderUtils));
    }

    @Override
    public void onDisable() {

    }

}
