package com.cooleg.civutils;

import com.cooleg.civutils.commands.Distribute;
import com.cooleg.civutils.commands.TeamAssign;
import me.cooleg.easycommands.Command;
import me.cooleg.easycommands.commandmeta.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.List;

public class CivCmd implements Command {

    private CivUtils civUtils;
    private TeamAssign teamAssign;
    public CivCmd(CivUtils civUtils, TeamAssign teamAssign) {
        this.civUtils = civUtils;
        this.teamAssign = teamAssign;
    }

    @Override
    public boolean rootCommand(CommandSender commandSender, String s) {
        commandSender.sendMessage(ChatColor.GOLD + "The commands are distribute, assign, assignall, massassign, manage, border, pvp, setpos, or reload.");
        return true;
    }

    @Override
    public boolean noMatch(CommandSender commandSender, String s, String[] strings) {
        return rootCommand(commandSender, s);
    }

    @SubCommand("manage")
    private boolean manageSubcommand(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {new com.cooleg.civutils.commands.Manage().managerGui(((Player) sender).getPlayer(), civUtils);}
        return true;
    }

    @SubCommand("pvp")
    private boolean pvpSubcommand(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {
            new com.cooleg.civutils.commands.PvpToggle().pvpGui(((Player) sender).getPlayer());
        }
        return true;
    }

    @SubCommand("assign")
    private boolean assignSubcommand(CommandSender sender, String s, String[] args) {
        if (args.length == 2) {
            try {
                civUtils.teamAssign.assign(Bukkit.getOfflinePlayer(args[0]), args[1]);
            } catch (Exception e) {
                sender.sendMessage("Yeah okay so smthn went wrong heehee");
            }
        }
        return true;
    }

    @SubCommand("singletp")
    private boolean singletpSubcommand(CommandSender sender, String s, String[] args) {
        try {
            new Distribute(civUtils).TeleportPlayer(Bukkit.getPlayer(args[1]));
        } catch (Exception e) {
            // Nothing here too lazy
        }
        return true;
    }

    @SubCommand("assignall")
    private boolean assignallSubcommand(CommandSender sender, String s, String[] args) {
        civUtils.teamAssign.AssignAll();
        return true;
    }

    @SubCommand("border")
    private boolean borderSubcommand(CommandSender sender, String s, String[] args) {
        /*
            if (!(sender instanceof Player)) {sender.sendMessage("This command must be ran as a player"); break;}
            if (civUtils.border) {borderUtils.stopBorder(); sender.sendMessage(ChatColor.GOLD + "Border disabled! (Unless it sent an error)");} else {borderUtils.startBorder(((Player) sender).getPlayer()); sender.sendMessage(ChatColor.GOLD + "Border enabled! (Unless it sent an error)");}
            break;
        */
        sender.sendMessage("Command disabled because its annoying to actually fix and bugtest and very manual. its just not worth it man. use worldguard like a normal human being");
        return true;
    }

    @SubCommand("help")
    private boolean helpSubcommand(CommandSender sender, String s, String[] args) {
        rootCommand(sender, s);
        return true;
    }

    @SubCommand("distribute")
    private boolean distributeSubcommand(CommandSender sender, String s, String[] args) {
        Distribute distribute = new com.cooleg.civutils.commands.Distribute(civUtils);
        distribute.spread();
        sender.sendMessage(ChatColor.GOLD + "Players distributed!");
        return true;
    }

    @SubCommand("setpos")
    private boolean setposSubcommand(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {new com.cooleg.civutils.commands.SetPos(args,civUtils, ((Player) sender).getPlayer()); sender.sendMessage(ChatColor.GOLD + "Position Set!");}
        return true;
    }

    @SubCommand("massassign")
    private boolean massassignSubcommand(CommandSender sender, String s, String[] args) {
        if (sender instanceof Player) {civUtils.massAssignUtil.openGui(((Player) sender).getPlayer());};
        return true;
    }

    @SubCommand("exile")
    private boolean exileSubcommand(CommandSender sender, String s, String[] args) {
        if (args.length == 2) {
            civUtils.teamAssign.assign(Bukkit.getOfflinePlayer(args[1]), "exile");
        }
        return true;
    }

    @SubCommand("reload")
    private boolean reloadSubcommand(CommandSender sender, String s, String[] args) {
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
        civUtils.setPvp(pvp);
        civUtils.kick = civUtils.getConfig().getBoolean("options.KickOnDeath");
        civUtils.unWl = civUtils.getConfig().getBoolean("options.UnWhitelistOnDeath");
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
        return true;
    }
    @Nonnull
    @Override
    public String name() {
        return "civutils";
    }

    @Override
    public List<String> aliases() {
        return List.of(new String[]{"civ", "civilization"});
    }
}
