package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Distribute {
    private CivUtils civUtils;
    public Distribute(CivUtils civUtils, CommandSender sender) {
        this.civUtils = civUtils;
        try {spread();} catch (NullPointerException e) {sender.sendMessage("Dayum you yucky cause u put nothin in your config.yml you wierdo creep trying to send people to nowhere"); civUtils.getLogger().severe("Bro someone tried to send people places but there aint no places to send them what the hell lmao");}
    }

    // This took me so long because i was dumb as fuck and had to
    // think of a system to do this shit.
    // Imma be real tho it was easy in the end
    public void spread() {
        ConfigurationSection config = civUtils.getConfig().getConfigurationSection("teams");
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (String string : civUtils.teamCache) {
                // just sets the permission node needed beforehand
                // cause for some reaosn its unreliable otherwise
                String perm = "civ.team."+string;
                if (p.hasPermission(perm)) {
                    // Adds a tag for commands or stuff do do mid event for each team.
                    p.addScoreboardTag(string);
                    // Just grabs the coords and tps people it aint that much
                    double x = config.getDouble(string+".x");
                    double y = config.getDouble(string+".y");
                    double z = config.getDouble(string+".z");
                    World world = Bukkit.getWorld(config.getString(string+".world"));
                    Location loc = new Location(world,x,y,z);
                    p.teleport(loc);
                    break;
                }

            }

        }

    }

}
