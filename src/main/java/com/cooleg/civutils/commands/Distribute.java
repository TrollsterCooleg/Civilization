package com.cooleg.civutils.commands;

import com.cooleg.civutils.CivUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Distribute {
    private CivUtils civUtils;
    ConfigurationSection config;

    public Distribute(CivUtils civUtils) {
        this.civUtils = civUtils;
        config  = civUtils.getConfig().getConfigurationSection("teams");
    }

    // This took me so long because i was dumb as fuck and had to
    // think of a system to do this shit.
    // Imma be real tho it was easy in the end
    public void spread() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            TeleportPlayer(p);
        }
    }

    public Location getLocation(Player p) {
        for (String string : civUtils.teamCache) {
            String perm = "civ.team."+string;
            if (p.hasPermission(perm)) {
                try {
                    double x = config.getDouble(string+".x");
                    double y = config.getDouble(string+".y");
                    double z = config.getDouble(string+".z");
                    Bukkit.createWorld(new WorldCreator(config.getString(string+".world")));
                    World world = Bukkit.getWorld(config.getString(string+".world"));
                    Location loc = new Location(world,x,y,z);
                    return loc;
                } catch (Exception e) {
                    Bukkit.getLogger().severe("So basically the config is messed up so the tp corrds are sadge. Error below:");
                    Bukkit.getLogger().severe(e.getMessage());
                    return new Location(p.getWorld(),0,-64,0);
                }
            }
        }
        return new Location(p.getWorld(),p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ());
    }

    public void TeleportPlayer(Player p) {
        for (String string : civUtils.teamCache) {
            // just sets the permission node needed beforehand
            // cause for some reaosn its unreliable otherwise
            String perm = "civ.team."+string;
            if (p.hasPermission(perm)) {
                // Adds a tag for commands or stuff do do mid event for each team.
                p.addScoreboardTag(string);
                // Just grabs the coords and tps people it aint that much
                p.teleport(getLocation(p));
                break;
            }

        }
    }

}
