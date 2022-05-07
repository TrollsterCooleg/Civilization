package com.cooleg.civutils.utils;


import com.cooleg.civutils.CivCmd;
import com.cooleg.civutils.CivUtils;
import com.cooleg.civutils.commands.Distribute;
import com.cooleg.civutils.commands.Manage;
import com.cooleg.civutils.commands.PvpToggle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EventHandling implements Listener {

    private CivUtils civUtils;

    public EventHandling(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (!civUtils.getConfig().getBoolean("options.ForcedSpawnPoint")) {return;}
        e.setRespawnLocation(civUtils.distribute.getLocation(e.getPlayer(), false));
    }

    @EventHandler
    public void craftAttempt(PrepareItemCraftEvent e) {
        try {
            for (Material material : civUtils.items) {
                if (e.getRecipe().getResult().getType().equals(material)) {
                    e.getInventory().setResult(new ItemStack(Material.AIR));
                    return;
                }
            }
        } catch (Exception exception) {}
        return;
    }
    @EventHandler
    public void onLoad(WorldLoadEvent e) {
        List<String> worlds = civUtils.getConfig().getStringList("worlds");
        if (worlds.contains(e.getWorld().getName())) {return;}
        worlds.add(e.getWorld().getName());
        civUtils.getConfig().set("worlds", worlds);
        civUtils.saveConfig();
        return;
    }
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.DARK_GREEN + "PVP Menu") && e.getCurrentItem() != null) {
            if (e.getRawSlot() == 4) {
                Player p = (Player) e.getWhoClicked();
                PvpToggle pvpToggle = new PvpToggle();
                pvpToggle.PvpToggle(p, civUtils);
                pvpToggle.UpdateInventory(p, e);
            }
            e.setCancelled(true);
        }
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.DARK_GREEN + "Manager") && e.getCurrentItem() != null) {
            if (e.getRawSlot() == 3) {
                Player p = (Player) e.getWhoClicked();
                PvpToggle pvpToggle = new PvpToggle();
                pvpToggle.PvpToggle(p, civUtils);
                Manage manage = new Manage();
                manage.UpdatePvp(p, e);
            }
            e.setCancelled(true);
        }
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.DARK_GREEN + "Manager") && e.getCurrentItem() != null) {
            if (e.getRawSlot() == 5) {
                if (civUtils.border) {
                    civUtils.borderUtils.stopBorder();
                } else {
                    civUtils.borderUtils.startBorder((Player) e.getWhoClicked());
                }
                Player p = (Player) e.getWhoClicked();
                Manage manage = new Manage();
                manage.UpdateBorder(e, civUtils);
            }
            e.setCancelled(true);
        }
        return;
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
        return;

    }

}
