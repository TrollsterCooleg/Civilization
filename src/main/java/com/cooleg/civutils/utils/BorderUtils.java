package com.cooleg.civutils.utils;

import com.cooleg.civutils.CivUtils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
        for (String string : civUtils.teamCache) {
            try {
                lowXMap.put(string, civUtils.getConfig().getDouble(string + ".lower-x"));
                highXMap.put(string, civUtils.getConfig().getDouble(string + ".higher-x"));
                lowZMap.put(string, civUtils.getConfig().getDouble(string + ".lower-z"));
                highZMap.put(string, civUtils.getConfig().getDouble(string + ".higher-z"));
            } catch (Exception e) {civUtils.getLogger().severe("Hey there bucko you are missing border coordinates and shit will probably break!!!!!! hahhahahahah");}
        }
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

    public void stopBorder() {
        civUtils.border = false;
        border.cancel();
    }

}
