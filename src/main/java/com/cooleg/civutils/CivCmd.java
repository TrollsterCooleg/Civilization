package com.cooleg.civutils;

import com.cooleg.civutils.commands.Distribute;
import com.cooleg.civutils.commands.TeamAssign;
import com.cooleg.civutils.utils.BorderUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class CivCmd implements CommandExecutor {
    private CivUtils civUtils;
    private BorderUtils borderUtils;
    private TeamAssign teamAssign;
    public CivCmd(CivUtils civUtils, BorderUtils borderUtils, TeamAssign teamAssign) {
        this.civUtils = civUtils;
        this.borderUtils = borderUtils;
        this.teamAssign = teamAssign;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && !sender.hasPermission("civ.commands")) {return false;}

        if (args.length == 0) {
            sender.sendMessage(ChatColor.GOLD + "The commands are distribute, assignall, massassign, manage, border, pvp, setpos, or reload.");
            return false;
        }
        // Ahhh big ugly spagetti
        // at least i used .toLowerCase
        // Border toggles border, help shows cmd list, distribute
        // distributes the players, setpos adds distribution positions
        // assign gives everyone a team and reload reloads the config. easy enough
        switch (args[0].toLowerCase()) {
            case ("manage"):
                if (sender instanceof Player) {new com.cooleg.civutils.commands.Manage().managerGui(((Player) sender).getPlayer(), civUtils);}
                break;
            case ("pvp"):
                if (sender instanceof Player) {
                    new com.cooleg.civutils.commands.PvpToggle().pvpGui(((Player) sender).getPlayer());
                }
                break;
            case ("assignall"):
                civUtils.teamAssign.AssignAll();
                break;
            case ("border"):
                if (!(sender instanceof Player)) {sender.sendMessage("This command must be ran as a player"); break;}
                if (civUtils.border) {borderUtils.stopBorder(); sender.sendMessage(ChatColor.GOLD + "Border disabled! (Unless it sent an error)");} else {borderUtils.startBorder(((Player) sender).getPlayer()); sender.sendMessage(ChatColor.GOLD + "Border enabled! (Unless it sent an error)");}
                break;
            case ("help"):
                sender.sendMessage("The commands are distribute, assignall, massassign, manage, border, pvp, setpos, or reload.");
                break;
            case ("distribute"):
                Distribute distribute = new com.cooleg.civutils.commands.Distribute(civUtils);
                distribute.spread();
                sender.sendMessage(ChatColor.GOLD + "Players distributed!");
                break;
            case ("setpos"):
                if (sender instanceof Player) {new com.cooleg.civutils.commands.SetPos(args,civUtils, ((Player) sender).getPlayer()); sender.sendMessage(ChatColor.GOLD + "Position Set!");}
                break;
            case ("massassign"):
                if (sender instanceof Player) {civUtils.massAssignUtil.openGui(((Player) sender).getPlayer());};
                break;
            case ("reload"):
                civUtils.reloadConfig();
                civUtils.curing = civUtils.getConfig().getBoolean("options.villagers");

                civUtils.blockedCrafts.refreshList();
                boolean pvp;
                try {
                    pvp = civUtils.getConfig().getBoolean("options.PVP");
                } catch (Exception e) {
                    civUtils.getConfig().set("options.PVP", true);
                    civUtils.saveConfig();
                    pvp = true;
                }
                for (World world : Bukkit.getWorlds()) {
                    world.setPVP(pvp);
                }
                try {
                    civUtils.teamCache = civUtils.getConfig().getConfigurationSection("teams").getKeys(false);
                } catch (Exception e) {
                    civUtils.getLogger().severe("Smthn isnt formatted correctly in the config.yml");
                    sender.sendMessage("something isnt right in the config.yml");
                }
                sender.sendMessage(ChatColor.GOLD + "All configurations reloaded!");
                break;
        }

        return false;
    }
}
