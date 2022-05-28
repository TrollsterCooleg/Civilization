package com.cooleg.civutils.utils;

import com.cooleg.civutils.CivUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
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
    private HashMap<String, World> worldMap = new HashMap<>();

    public BorderUtils(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    public void startBorder(Player player) {
        // Caches border in hashmaps so that its faster
        // and more reliable then reading from file every time
        ConfigurationSection config = civUtils.getConfig().getConfigurationSection("teams");
        for (String string : civUtils.teamCache) {
            // Makes sure all the coordinates needed exist
            if (!config.contains(string + ".world") || !config.contains(string + ".lower-x") || !config.contains(string + ".higher-x") || !config.contains(string + ".lower-z") || !config.contains(string + ".higher-z")) {
                civUtils.getLogger().severe("Hey there bucko you are missing or messed up your border coordinates (or forgot to put the world in)! Im turning the border thing off for your own good! Check the config for the team " + string + " please uwu");
                player.sendMessage("Hey there man, you are missing a border coordinate (or world) for the team " + string + ", you better fix that.");
                return;
            }
            try {
                lowXMap.put(string, config.getDouble(string + ".lower-x"));
                highXMap.put(string, config.getDouble(string + ".higher-x"));
                lowZMap.put(string, config.getDouble(string + ".lower-z"));
                highZMap.put(string, config.getDouble(string + ".higher-z"));
                worldMap.put(string, Bukkit.getWorld(config.getString(string + ".world")));
            } catch (Exception e) {player.sendMessage("Hey there man, you are missing a border coordinate for the team " + string + ", you better fix that.");
                civUtils.getLogger().severe("Hey there bucko you are missing or messed up your border coordinates! Im turning the border thing off for your own good! Check the config for the team " + string + " please uwu"); return;}
            // Checks for coords in wrong order
            if (lowXMap.get(string) > highXMap.get(string)) {
                Double actualLow = highXMap.get(string);
                Double actualHigh = lowXMap.get(string);
                config.set(string+".lower-x", actualLow);
                config.set(string+".higher-x", actualHigh);
                civUtils.saveConfig();
                lowXMap.put(string, actualLow);
                lowXMap.put(string, actualHigh);
            }
            if (lowZMap.get(string) > highZMap.get(string)) {
                Double actualLow = highZMap.get(string);
                Double actualHigh = lowZMap.get(string);
                config.set(string+".lower-z", actualLow);
                config.set(string+".higher-z", actualHigh);
                civUtils.saveConfig();
                lowZMap.put(string, actualLow);
                lowZMap.put(string, actualHigh);
            }
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
                        if (p.hasPermission("civ.exclude")) {return;}
                        if (p.hasPermission(perm)) {
                            Location playerLocation = p.getLocation();
                            if (playerLocation.getBlockX() < lowXMap.get(string)) {
                                playerLocation.setX(lowXMap.get(string));
                                playerLocation.setWorld(worldMap.get(string));
                                p.teleport(playerLocation);
                            }
                            if (playerLocation.getBlockX() > highXMap.get(string)) {
                                playerLocation.setX(highXMap.get(string)-1);
                                playerLocation.setWorld(worldMap.get(string));
                                p.teleport(playerLocation);
                            }
                            if (playerLocation.getBlockZ() < lowZMap.get(string)) {
                                playerLocation.setZ(lowZMap.get(string));
                                playerLocation.setWorld(worldMap.get(string));
                                p.teleport(playerLocation);
                            }
                            if (playerLocation.getBlockZ() > highZMap.get(string)) {
                                playerLocation.setZ(highZMap.get(string)-1);
                                playerLocation.setWorld(worldMap.get(string));
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
