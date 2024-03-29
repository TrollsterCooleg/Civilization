package com.cooleg.civutils.utils;


import com.cooleg.civutils.CivUtils;
import com.cooleg.civutils.commands.Manage;
import com.cooleg.civutils.commands.PvpToggle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EventHandling implements Listener {

    private CivUtils civUtils;

    public EventHandling(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    @EventHandler
    public void lava(PlayerBucketEmptyEvent event) {
        if ((event.getBucket() == Material.LAVA_BUCKET) && (!civUtils.getPvp())) {
            event.setCancelled(true);
        }
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
            /*
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
            */
            e.getWhoClicked().sendMessage("Border stuff disabled because its annoying and hard to use. Use worldguard please.");
            e.setCancelled(true);
        }
        return;
    }

    @EventHandler
    public void guiOpen(InventoryOpenEvent event) {
        if (civUtils.curing) {return;}
        if (event.getInventory().getType() == InventoryType.MERCHANT) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        try {
            if (civUtils.kick) {
                event.getEntity().getInventory().clear();
                event.getEntity().getPlayer().kickPlayer(ChatColor.RED + "You died...");
            }
            if (civUtils.unWl) {
               event.getEntity().getPlayer().setWhitelisted(false);
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Hey there man i would love to possibly kick or unwhitelist people but small problem your config is brokey");
        }
        return;

    }

    @EventHandler
    public void curePatch(EntityTransformEvent e) {
        if (civUtils.curing) {return;}
        if (e.getTransformReason() == EntityTransformEvent.TransformReason.CURED) {
            e.setCancelled(true);
        }
    }
}
