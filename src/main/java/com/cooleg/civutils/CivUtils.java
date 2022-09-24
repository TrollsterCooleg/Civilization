package com.cooleg.civutils;

import com.cooleg.civutils.commands.Distribute;
import com.cooleg.civutils.commands.TeamAssign;
import com.cooleg.civutils.utils.BlockedCrafts;
import com.cooleg.civutils.utils.BorderUtils;
import com.cooleg.civutils.utils.EventHandling;
import com.cooleg.civutils.utils.MassAssignUtil;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.*;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CivUtils extends JavaPlugin {

    // Preps variables, such as border being on or off, the team cache,
    // and also sets a variable for the borderUtils class beforehand conveniently
    public boolean border = false;
    public boolean curing = false;
    public Set<String> teamCache;
    public BorderUtils borderUtils;
    public Distribute distribute;
    public BlockedCrafts blockedCrafts;
    public TeamAssign teamAssign;
    public MassAssignUtil massAssignUtil;
    public static Permission perms = null;
    public List<Material> items = new ArrayList<>();
    boolean pvp;

    @Override
    public void onEnable() {
        setupPermissions();
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
        Bukkit.getPluginManager().registerEvents(new EventHandling(this), this);
        Bukkit.getPluginManager().registerEvents(massAssignUtil = new MassAssignUtil(this), this);
        getCommand("civutils").setExecutor(new CivCmd(this,borderUtils,teamAssign));
        getCommand("civutils").setTabCompleter(new CivCompleter());
        try {
            for (String string : (List<String>) getConfig().getList("worlds")) {
                Bukkit.createWorld(new WorldCreator(string));
            }
        } catch (Exception e) {
            getLogger().severe("Your worlds list is empty. Ignore this if you are using the default world for your event, but make sure that you add the worlds you need that arent the default world to the worlds list in the config.yml.");
        }
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

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    @Override
    public void onDisable() {

    }

    public boolean getPvp() {
        return pvp;
    }

    public void setPvp(Boolean pvp) {
        this.pvp = pvp;
    }

}
