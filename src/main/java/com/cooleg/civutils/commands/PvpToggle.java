package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class PvpToggle {

    public void pvpGui(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "PVP Menu");
        ItemStack toggle;
        ItemMeta meta;
        ItemStack filter = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta filtermeta = filter.getItemMeta();
        filtermeta.setDisplayName(" ");
        filter.setItemMeta(filtermeta);
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, filter);
        }
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

        inv.setItem(4, toggle);

        player.openInventory(inv);

    }

    public void UpdateInventory (Player player, InventoryClickEvent e) {
        Inventory inv = Bukkit.createInventory(player, 9, ChatColor.DARK_GREEN + "PVP Menu");
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

        e.getInventory().setItem(4, toggle);

    }

    public void PvpToggle(Player p, CivUtils civUtils) {
        if (p.getWorld().getPVP() == true) {
            for (World world : Bukkit.getWorlds()) {
                world.setPVP(false);
                civUtils.getConfig().set("options.PVP", false);
                civUtils.saveConfig();
            }
        } else {
            for (World world : Bukkit.getWorlds()) {
                world.setPVP(true);
                civUtils.getConfig().set("options.PVP", true);
                civUtils.saveConfig();
            }
        }
    }
}
