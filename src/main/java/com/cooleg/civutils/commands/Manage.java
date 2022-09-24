package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Collections;

public class Manage {

    private static ItemStack borderOn = new ItemStack(Material.GREEN_CONCRETE);
    private static ItemStack borderOff = new ItemStack(Material.RED_CONCRETE);

    static {
        ItemMeta borderOnMeta = borderOn.getItemMeta();
        borderOnMeta.setDisplayName(ChatColor.GREEN + "The border is currently enabled!");
        borderOnMeta.setLore(Collections.singletonList("Click to disable the border."));
        borderOn.setItemMeta(borderOnMeta);
        ItemMeta borderOffMeta = borderOff.getItemMeta();
        borderOffMeta.setDisplayName(ChatColor.GREEN + "The border is currently disabled!");
        borderOffMeta.setLore(Collections.singletonList("Click to enable the border."));
        borderOff.setItemMeta(borderOffMeta);
    }

    public void managerGui(Player player, CivUtils civUtils) {
        Inventory inv = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "Manager");
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, PvpToggle.filler);
        }
        if (player.getWorld().getPVP()) {
            inv.setItem(3, PvpToggle.pvpOn);
        } else {
            inv.setItem(3, PvpToggle.pvpOff);
        }
        if (civUtils.border) {
            inv.setItem(5, borderOn);
        } else {
            inv.setItem(5, borderOff);
        }

        player.openInventory(inv);
    }

    public void UpdateBorder (InventoryClickEvent e, CivUtils civUtils) {
        if (civUtils.border) {
            e.getInventory().setItem(5, borderOn);
        } else {
            e.getInventory().setItem(5, borderOff);
        }

    }

    public void UpdatePvp (Player player, InventoryClickEvent e) {
        if (player.getWorld().getPVP()) {
            e.getInventory().setItem(3, PvpToggle.pvpOn);
        } else {
            e.getInventory().setItem(3, PvpToggle.pvpOff);
        }

    }

}
