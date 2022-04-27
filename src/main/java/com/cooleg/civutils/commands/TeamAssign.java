package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.types.InheritanceNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;

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
                User user = civUtils.api.getPlayerAdapter(Player.class).getUser(p);
                if (teamIterator.hasNext()) {
                    team = teamIterator.next();
                } else {
                    teamIterator = civUtils.teamCache.iterator();
                    team = teamIterator.next();
                }
                try {
                    Group chosenGroup = civUtils.api.getGroupManager().getGroup(team);
                    InheritanceNode node = InheritanceNode.builder(chosenGroup).build();
                    user.data().add(node);
                    civUtils.api.getUserManager().saveUser(user);
                    p.sendMessage("Set your team to " + team + "!");
                } catch (Exception e) {
                    Bukkit.getLogger().severe("You have some sorta team in your config.yml that doesnt exist in luckperms.");
                    Bukkit.getLogger().severe("Check that the group in your config called " + team + " actually exists in luckperms.");
                    Bukkit.getLogger().severe("This is why i said to be careful :shrug:");
                }
            }
        }
    }
}

