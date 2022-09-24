package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PvpToggle {

    public static ItemStack pvpOn = new ItemStack(Material.GREEN_CONCRETE);
    public static ItemStack pvpOff = new ItemStack(Material.RED_CONCRETE);
    public static ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

    static {
        ItemMeta pvpOnMeta = pvpOn.getItemMeta();
        pvpOnMeta.setDisplayName(ChatColor.GREEN + "PVP is currently enabled!");
        pvpOnMeta.setLore(Collections.singletonList("Click to disable PVP"));
        pvpOn.setItemMeta(pvpOnMeta);
        ItemMeta pvpOffMeta = pvpOff.getItemMeta();
        pvpOffMeta.setDisplayName(ChatColor.GREEN + "PVP is currently disabled!");
        pvpOffMeta.setLore(Collections.singletonList("Click to enable PVP"));
        pvpOff.setItemMeta(pvpOffMeta);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);
    }

    public void pvpGui(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "PVP Menu");
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, filler);
        }
        if (player.getWorld().getPVP()) {
            inv.setItem(4, pvpOn);
        } else {
            inv.setItem(4, pvpOff);
        }

        player.openInventory(inv);
    }

    public void UpdateInventory (Player player, InventoryClickEvent e) {
        if (player.getWorld().getPVP()) {
            e.getInventory().setItem(4, pvpOn);
        } else {
            e.getInventory().setItem(4, pvpOff);
        }

    }

    public void PvpToggle(Player p, CivUtils civUtils) {
        if (civUtils.getPvp() == true) {
            civUtils.setPvp(false);
            for (World world : Bukkit.getWorlds()) {
                world.setPVP(false);
                civUtils.getConfig().set("options.PVP", false);
                civUtils.saveConfig();
            }
        } else {
            civUtils.setPvp(true);
            for (World world : Bukkit.getWorlds()) {
                world.setPVP(true);
                civUtils.getConfig().set("options.PVP", true);
                civUtils.saveConfig();
            }
        }
    }
}
