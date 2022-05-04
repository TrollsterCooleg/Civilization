package com.cooleg.civutils.utils;

import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class BlockedCrafts {

    public CivUtils civUtils;

    public BlockedCrafts(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    public void refreshList() {
        try {
            civUtils.items = new ArrayList<>();
            List<String> list = civUtils.getConfig().getStringList("blocked-crafts");
            for (String string : list) {
                Material material = Material.getMaterial(string);
                civUtils.items.add(material);
            }
        } catch (Exception e) {
            Bukkit.getLogger().severe("Something is goin on with the item blocker and it isnt a good thing thats for sure.");
            Bukkit.getLogger().severe(e.getMessage());
            civUtils.items = new ArrayList<>();
        }
    }
}
