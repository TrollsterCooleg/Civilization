package com.cooleg.civutils;

import com.cooleg.civutils.security.SecureHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class CivUtils extends JavaPlugin {

    public static boolean enabled = false;

    @Override
    public void onEnable() {
        // Important Startup
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getCommand("civutils").setExecutor(new CivCmd(this));
        Bukkit.getPluginManager().registerEvents(new SecureHandler(this), this);
    }

    @Override
    public void onDisable() {

    }

}
