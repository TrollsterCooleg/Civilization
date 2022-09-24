package com.cooleg.civutils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CivCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("distribute", "assign", "assignall", "massassign", "manage", "border", "pvp", "setpos", "reload");
        } else if (args.length == 2) {
            if (args[1].equals("exile") || args[1].equals("assign") || args[1].equals("singletp")) {
                List<String> players = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    players.add(p.getName());
                }
            }
        }
        return null;
    }
}
