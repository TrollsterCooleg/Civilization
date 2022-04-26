package com.cooleg.civutils.utils;

import com.cooleg.civutils.CivUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;


public class BorderUtils {
    public BukkitTask border;
    private CivUtils civUtils;
    private HashMap<String, Double> lowXMap = new HashMap<>();
    private HashMap<String, Double> highXMap = new HashMap<>();
    private HashMap<String, Double> lowZMap = new HashMap<>();
    private HashMap<String, Double> highZMap = new HashMap<>();

    public BorderUtils(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    public void startBorder() {
        // Caches border in hashmaps so that its faster
        // and more reliable then reading from file every time
        for (String string : civUtils.teamCache) {
            ConfigurationSection config = civUtils.getConfig().getConfigurationSection("teams");
            try {
                lowXMap.put(string, config.getDouble(string + ".lower-x"));
                highXMap.put(string, config.getDouble(string + ".higher-x"));
                lowZMap.put(string, config.getDouble(string + ".lower-z"));
                highZMap.put(string, config.getDouble(string + ".higher-z"));
            } catch (Exception e) {civUtils.getLogger().severe("Hey there bucko you are missing border coordinates and shit will probably break!!!!!! hahhahahahah");}
        }
        // Marks the border being on as true, and starts a loop of it
        // checking for players in each team
        // and if they are within their border.
        civUtils.border = true;
        border = new BukkitRunnable() {
            @Override
            public void run() {
                for (String string : civUtils.teamCache) {
                    String perm = "civ.team."+string;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.hasPermission(perm)) {
                            Location playerLocation = p.getLocation();
                            if (playerLocation.getBlockX() < lowXMap.get(string)) {
                                playerLocation.setX(lowXMap.get(string));
                                p.teleport(playerLocation);
                            }
                            if (playerLocation.getBlockX() > highXMap.get(string)) {
                                playerLocation.setX(highXMap.get(string)-1);
                                p.teleport(playerLocation);
                            }
                            if (playerLocation.getBlockZ() < lowZMap.get(string)) {
                                playerLocation.setZ(lowZMap.get(string));
                                p.teleport(playerLocation);
                            }
                            if (playerLocation.getBlockZ() > highZMap.get(string)) {
                                playerLocation.setZ(highZMap.get(string)-1);
                                p.teleport(playerLocation);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(civUtils,20,40);
    }

    // Literally cancels the runnable and marks border as off
    // so that the toggles work. Not much here imma be real
    public void stopBorder() {
        civUtils.border = false;
        border.cancel();
    }

}
