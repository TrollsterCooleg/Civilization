package com.cooleg.civutils;

import com.cooleg.civutils.commands.TeamAssign;
import com.cooleg.civutils.utils.BorderUtils;
import com.cooleg.civutils.utils.EventHandling;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public final class CivUtils extends JavaPlugin {

    // Preps variables, such as border being on or off, the team cache,
    // and also sets a variable for the borderUtils class beforehand conveniently
    public boolean border = false;
    public Set<String> teamCache;
    public BorderUtils borderUtils;
    public TeamAssign teamAssign;
    public LuckPerms api;
    static CivUtils instance;

    @Override
    public void onEnable() {
        // Important Startup
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        try {
            teamCache = this.getConfig().getConfigurationSection("teams").getKeys(false);
        } catch (Exception e) {
            this.getLogger().severe("Somethings wrong in the config.yml");
        }
        borderUtils = new BorderUtils(this);
        teamAssign = new TeamAssign(this);
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {api = provider.getProvider();}
        Bukkit.getPluginManager().registerEvents(new EventHandling(this), this);
        getCommand("civutils").setExecutor(new CivCmd(this,borderUtils,teamAssign));
        try {
            for (String string : (List<String>) getConfig().getList("worlds")) {
                Bukkit.createWorld(new WorldCreator(string));
            }
        } catch (Exception e) {
            getLogger().severe("Your worlds list is empty. Ignore this if you are using the default world for your event, but make sure that you add the worlds you need that arent the default world to the worlds list in the config.yml.");
        }
        boolean pvp;
        try {
            pvp = getConfig().getBoolean("options.PVP");
        } catch (Exception e) {
            getConfig().set("options.PVP", true);
            saveConfig();
            pvp = true;
        }
        for (World world : Bukkit.getWorlds()) {
            world.setPVP(pvp);
        }
    }

    @Override
    public void onDisable() {

    }

}
