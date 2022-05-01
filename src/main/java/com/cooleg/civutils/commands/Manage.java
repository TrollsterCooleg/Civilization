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

import java.util.ArrayList;
import java.util.List;

public class Manage {

    public void managerGui(Player player, CivUtils civUtils) {
        Inventory inv = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "Manager");
        ItemStack pvptoggle;
        ItemStack bordertoggle;
        ItemMeta meta;
        ItemStack filter = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta filtermeta = filter.getItemMeta();
        filtermeta.setDisplayName(" ");
        filter.setItemMeta(filtermeta);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, filter);
        }
        if (player.getWorld().getPVP()) {
            pvptoggle = new ItemStack(Material.GREEN_CONCRETE);
            meta = pvptoggle.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "PVP is currently enabled!");
            List<String> loreList = new ArrayList<>();
            loreList.add("Click to disable PVP");
            meta.setLore(loreList);
            pvptoggle.setItemMeta(meta);
        } else {
            pvptoggle = new ItemStack(Material.RED_CONCRETE);
            meta = pvptoggle.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "PVP is currently disabled!");
            List<String> loreList = new ArrayList<>();
            loreList.add("Click to enable PVP");
            meta.setLore(loreList);
            pvptoggle.setItemMeta(meta);
        }
        inv.setItem(3, pvptoggle);
        if (civUtils.border) {
            bordertoggle = new ItemStack(Material.GREEN_CONCRETE);
            meta = bordertoggle.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "The border is currently enabled!");
            List<String> loreList = new ArrayList<>();
            loreList.add("Click to disable the border.");
            meta.setLore(loreList);
            bordertoggle.setItemMeta(meta);
        } else {
            bordertoggle = new ItemStack(Material.RED_CONCRETE);
            meta = bordertoggle.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "The border is currently disabled!");
            List<String> loreList = new ArrayList<>();
            loreList.add("Click to enable the border.");
            meta.setLore(loreList);
            bordertoggle.setItemMeta(meta);
        }
        inv.setItem(5, bordertoggle);

        player.openInventory(inv);
    }

    public void UpdateBorder (InventoryClickEvent e, CivUtils civUtils) {
        ItemStack toggle;
        ItemMeta meta;
        if (civUtils.border) {
            toggle = new ItemStack(Material.GREEN_CONCRETE);
            meta = toggle.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "The border is currently enabled!");
            List<String> loreList = new ArrayList<>();
            loreList.add("Click to disable the border.");
            meta.setLore(loreList);
            toggle.setItemMeta(meta);
        } else {
            toggle = new ItemStack(Material.RED_CONCRETE);
            meta = toggle.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "The border is currently disabled!");
            List<String> loreList = new ArrayList<>();
            loreList.add("Click to enable the border.");
            meta.setLore(loreList);
            toggle.setItemMeta(meta);
        }

        e.getInventory().setItem(5, toggle);

    }

    public void UpdatePvp (Player player, InventoryClickEvent e) {
        ItemStack toggle;
        ItemMeta meta;
        if (player.getWorld().getPVP()) {
            toggle = new ItemStack(Material.GREEN_CONCRETE);
            meta = toggle.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "PVP is currently enabled!");
            List<String> loreList = new ArrayList<>();
            loreList.add("Click to disable PVP");
            meta.setLore(loreList);
            toggle.setItemMeta(meta);
        } else {
            toggle = new ItemStack(Material.RED_CONCRETE);
            meta = toggle.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "PVP is currently disabled!");
            List<String> loreList = new ArrayList<>();
            loreList.add("Click to enable PVP");
            meta.setLore(loreList);
            toggle.setItemMeta(meta);
        }

        e.getInventory().setItem(3, toggle);

    }

}
