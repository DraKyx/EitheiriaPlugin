package fr.drakyx.commands.utils;

import fr.drakyx.Eitheria;
import fr.drakyx.utils.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPos implements CommandExecutor {

    public CommandPos(Eitheria eitheria) {
    }

    private Cuboid cuboid = new Cuboid(null, null);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou're not a player !");
        }

        Player p = (Player) commandSender;

        if (args.length == 0) {
            p.sendMessage("§6/pos 1 \n/pos 2 \n/pos fill [material]");
        }

        if (args.length == 1) {
            if (args[0].equals("1")) {
                Location loc = p.getLocation();

                cuboid.setLoc1(loc);
                p.sendMessage("§6First position set at x=" + (int) loc.getX() + " y=" + (int) loc.getY() + " z=" + (int) loc.getZ());
            }

            if (args[0].equals("2")) {
                Location loc = p.getLocation();

                cuboid.setLoc2(loc);
                p.sendMessage("§6Second position set at x=" + (int) loc.getX() + " y=" + (int) loc.getY() + " z=" + (int) loc.getZ());
            }

            if (args[0].equals("fill")) {
                p.sendMessage("§6/pos fill [material]");
            }
        }

        if (args.length == 2) {
            if (p.hasPermission("admin")) {
                if (args[0].equals("fill")) {
                    if (Material.matchMaterial(args[1]) != null) {
                        if (cuboid.getLoc1() != null && cuboid.getLoc2() != null) {
                            cuboid.setBlocks(Material.matchMaterial(args[1]));
                            p.sendMessage("§6Blocks filled with " + args[1]);
                        } else {
                            p.sendMessage("§6You need to set 2 postitions");
                        }
                    } else {
                        p.sendMessage("§6Must be a real material");
                    }
                }
                else {
                    p.sendMessage("§6/pos 1 \n/pos 2 \n/pos fill [material]");
                }
            }
            else {
                p.sendMessage("§cYou don't have the permission to perform this command");
            }
        }

        return false;
    }
}
