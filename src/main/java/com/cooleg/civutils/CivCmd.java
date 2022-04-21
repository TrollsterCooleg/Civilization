package com.cooleg.civutils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CivCmd implements CommandExecutor {
    private CivUtils civUtils;
    public CivCmd(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && !sender.hasPermission("civ.commands")) {return false;}

        if (args.length == 0) {
            sender.sendMessage("The commands are distribute, setpos, or reload.");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case ("help"):
                sender.sendMessage("The commands are distribute, setpos, or reload.");
                break;
            case ("distribute"):
                new com.cooleg.civutils.commands.Distribute(civUtils);
                break;
            case ("setpos"):
                if (sender instanceof Player) {new com.cooleg.civutils.commands.SetPos(args,civUtils, ((Player) sender).getPlayer());}
                break;
            case ("reload"):
                civUtils.reloadConfig();
                break;
        }

        return false;
    }
}
