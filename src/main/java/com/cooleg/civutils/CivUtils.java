package com.cooleg.civutils;

import com.cooleg.civutils.commands.Distribute;
import com.cooleg.civutils.commands.TeamAssign;
import com.cooleg.civutils.utils.BlockedCrafts;
import com.cooleg.civutils.utils.BorderUtils;
import com.cooleg.civutils.utils.EventHandling;
import jdk.nashorn.internal.ir.Block;
import net.luckperms.api.LuckPerms;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    public boolean curing = false;
    public Set<String> teamCache;
    public BorderUtils borderUtils;
    public Distribute distribute;
    public BlockedCrafts blockedCrafts;
    public TeamAssign teamAssign;
    public LuckPerms api;
    public List<Material> items = new ArrayList<>();

    @Override
    public void onEnable() {
        // Important Startup
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        curing = getConfig().getBoolean("options.villagers");
        try {
            teamCache = this.getConfig().getConfigurationSection("teams").getKeys(false);
        } catch (Exception e) {
            this.getLogger().severe("Somethings wrong in the config.yml");
        }
        borderUtils = new BorderUtils(this);
        distribute = new Distribute(this);
        teamAssign = new TeamAssign(this);
        blockedCrafts = new BlockedCrafts(this);
        blockedCrafts.refreshList();
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
