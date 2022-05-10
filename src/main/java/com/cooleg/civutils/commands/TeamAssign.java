package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Iterator;

import static com.cooleg.civutils.CivUtils.perms;

public class TeamAssign {
    CivUtils civUtils;

    public TeamAssign(CivUtils civUtils) {
        this.civUtils = civUtils;
    }

    public void AssignAll() {
        int current = 0;
        int teams = civUtils.teamCache.size();
        String team;
        Iterator<String> teamIterator = civUtils.teamCache.iterator();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("civ.exclude")) {
                if (teamIterator.hasNext()) {
                    team = teamIterator.next();
                } else {
                    teamIterator = civUtils.teamCache.iterator();
                    team = teamIterator.next();
                }

            }
        }
    }

    public void assign(OfflinePlayer p, String team) {
        try {
            boolean hasGroup = false;
            for (String group : perms.getGroups()) {
                if (team.equals(group)) {
                    hasGroup = true;
                    break;
                }
            }
            if (!hasGroup) {return;}
            perms.playerAddGroup(null, p, team);
        } catch (Exception e) {
            Bukkit.getLogger().severe("You have some sorta team in your config.yml that doesnt exist in luckperms.");
            Bukkit.getLogger().severe("Check that the group in your config called " + team + " actually exists in luckperms.");
            Bukkit.getLogger().severe("This is why i said to be careful :shrug:");
        }
    }
}



