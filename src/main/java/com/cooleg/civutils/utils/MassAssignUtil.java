package com.cooleg.civutils.utils;

import com.cooleg.civutils.CivUtils;
import com.cooleg.civutils.commands.PvpToggle;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static com.cooleg.civutils.CivUtils.perms;

public class MassAssignUtil implements Listener {

    CivUtils civUtils;
    HashMap<UUID,String> entering = new HashMap<>();

    public MassAssignUtil(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    public void openGui(Player p) {
        NamespacedKey key = new NamespacedKey(civUtils, "team");
        Inventory inv = Bukkit.createInventory(p, 54, ChatColor.DARK_GREEN + "Assign To Groups");
        ItemStack filler = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta fillermeta = filler.getItemMeta();
        fillermeta.setDisplayName(" ");
        filler.setItemMeta(fillermeta);
        int slot = 0;
        for (int i=0; i<=53; i++) {
            inv.setItem(i, filler);
        }
        for (String string : civUtils.teamCache) {
            boolean hasGroup = false;
            for (String group : perms.getGroups()) {
                if (string.equals(group)) {
                    hasGroup = true;
                    break;
                }
            }
            if (!hasGroup) {break;}
            ItemStack item = new ItemStack(Material.ARROW);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.GREEN + "Add to " + string);
            PersistentDataContainer contain = meta.getPersistentDataContainer();
            contain.set(key, PersistentDataType.STRING, string);
            item.setItemMeta(meta);
            inv.setItem(slot, item);
            slot++;
        }
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals(ChatColor.DARK_GREEN + "Assign To Groups") && e.getCurrentItem() != null) {
            if (e.getRawSlot() > 53) {e.setCancelled(true); return;}
            if (e.getClick() == ClickType.SWAP_OFFHAND) {
                e.getWhoClicked().getInventory().setItemInOffHand(e.getWhoClicked().getInventory().getItemInOffHand());
            }
            ItemStack item = e.getCurrentItem();
            if (item.getType().equals(Material.ARROW)) {
                PersistentDataContainer contain = item.getItemMeta().getPersistentDataContainer();
                NamespacedKey key = NamespacedKey.fromString("team", civUtils);
                entering.put(e.getWhoClicked().getUniqueId(), contain.get(key, PersistentDataType.STRING));
                e.getView().close();
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        if (entering.containsKey(event.getPlayer().getUniqueId())) {
            UUID uuid = event.getPlayer().getUniqueId();
            if (event.getMessage().equals("?")) {
                entering.remove(uuid);
                event.getPlayer().sendMessage(ChatColor.GOLD + "Stopped adding players to teams.");
                event.setCancelled(true);
                return;
            }
            try {
                civUtils.teamAssign.assign(Bukkit.getOfflinePlayer(event.getMessage()), entering.get(uuid));
                event.getPlayer().sendMessage(ChatColor.GOLD + "Added player " + event.getMessage() + " to team " + entering.get(event.getPlayer().getUniqueId()));
            } catch (Exception e) {
                event.getPlayer().sendMessage("The username you just provided was invalid, or the group doesn't exist. Please try again, or exit by sending ?");
            }
            event.setCancelled(true);
        }
    }
}
