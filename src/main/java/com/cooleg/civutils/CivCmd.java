package com.cooleg.civutils;

import com.cooleg.civutils.utils.BorderUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CivCmd implements CommandExecutor {
    private CivUtils civUtils;
    private BorderUtils borderUtils;
    public CivCmd(CivUtils civUtils, BorderUtils borderUtils) {
        this.civUtils = civUtils;
        this.borderUtils = borderUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && !sender.hasPermission("civ.commands")) {return false;}

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "The commands are distribute, setpos, or reload.");
            return false;
        }
        // Ahhh big ugly spagetti
        // at least i used .toLowerCase
        // Border toggles border, help shows cmd list, distribute
        // distributes the players, setpos adds distribution positions
        // and reload reloads the config. easy enough
        switch (args[0].toLowerCase()) {
            case ("border"):
                if (civUtils.border) {borderUtils.stopBorder(); sender.sendMessage(ChatColor.GOLD + "Border disabled!");} else {borderUtils.startBorder(); sender.sendMessage(ChatColor.GOLD + "Border enabled!");}
                break;
            case ("help"):
                sender.sendMessage("The commands are distribute, setpos, or reload.");
                break;
            case ("distribute"):
                new com.cooleg.civutils.commands.Distribute(civUtils);
                sender.sendMessage(ChatColor.GOLD + "Players distributed!");
                break;
            case ("setpos"):
                if (sender instanceof Player) {new com.cooleg.civutils.commands.SetPos(args,civUtils, ((Player) sender).getPlayer()); sender.sendMessage(ChatColor.GOLD + "Position Set!");}
                break;
            case ("reload"):
                civUtils.reloadConfig();
                civUtils.teamCache = civUtils.getConfig().getKeys(false);
                sender.sendMessage(ChatColor.GOLD + "All configurations reloaded!");
                break;
        }

        return false;
    }
}
