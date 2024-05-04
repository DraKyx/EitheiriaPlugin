package fr.drakyx.commands.utils;

import fr.drakyx.Eitheria;
import fr.drakyx.Mine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMine implements CommandExecutor {

    private final Eitheria eitheria;

    public CommandMine(Eitheria eitheria) {
        this.eitheria = eitheria;
    }

    public Eitheria getMain() {
        return eitheria;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou're not a player !");
        }

        Player p = (Player) commandSender;

        if (args.length == 0) {
            if(p.hasPermission("admin")) {
                p.sendMessage("§6/mine reset \n/mine tp [name] \n/mine set [number] \n/mine del [number]");
            }
            else {
                p.sendMessage("§6/mine reset \n/mine tp");
            }
        }

        if (args.length == 1) {
            if (args[0].equals("reset")) {
                World world = p.getWorld();

                for (int x = -189; x <= -179; x++) {
                    for (int y = 104; y <= 115; y++) {
                        for (int z = 317; z <= 327; z++) {
                            Location blockLoc = new Location(world, x, y, z);
                            Block block = blockLoc.getBlock();
                            block.setType(Material.OBSIDIAN);
                        }
                    }
                }
                p.sendMessage("§6Mine has been reseted");
            }

            if(args[0].equals("tp")) {
                if (!(p.hasPermission("admin"))) {
                    if (getMain().getConfig().getString("mines." + p.getUniqueId()) != null) {
                        double x = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.x");
                        double y = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.y");
                        double z = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.z");
                        double yaw = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.yaw");
                        double pitch = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.pitch");

                        World world = Bukkit.getWorld("world");
                        Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);

                        p.teleport(loc);
                    } else {
                        p.sendMessage("§cYou don't have any mine on the world");
                    }
                }
                else {
                    p.sendMessage("§6Put the name of the mine /mine tp [name]");
                }
            }

            if (args[0].equals("set")) {
                p.sendMessage("§6Put the number of the mine /mine set [number]");
            }

            if (args[0].equals("del")) {
                p.sendMessage("§6Put the name of the mine /mine del [number]");
            }
        }

        if (args.length == 2) {
            if (args[0].equals("set")) {
                if (getMain().getConfig().getString("mines." + args[1]) != null) {
                    p.sendMessage("§6This mine already exist");
                } else {
                    Location loc = p.getLocation();
                    Mine mine = new Mine(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), 0);

                    getMain().getMines().put(p.getUniqueId(), mine);
                    p.sendMessage("§6Mine has been created at your position");
                }
            }

            if (args[0].equals("del")) {
                if (getMain().getConfig().getString("mines." + args[1]) == null) {
                    p.sendMessage("§6This mine does not exist");
                } else {
                    getMain().getConfig().set("mines." + args[1], null);
                    getMain().saveConfig();
                    p.sendMessage("§6Mine has been deleted");
                }
            }

            if (p.hasPermission("admin")) {
                if (args[0].equals("tp")) {
                    if (getMain().getConfig().getString("mines." + args[1]) != null) {
                        double x = getMain().getConfig().getDouble("mines." + args[1] + ".spawnpoint.x");
                        double y = getMain().getConfig().getDouble("mines." + args[1] + ".spawnpoint.y");
                        double z = getMain().getConfig().getDouble("mines." + args[1] + ".spawnpoint.z");
                        double yaw = getMain().getConfig().getDouble("mines." + args[1] + ".spawnpoint.yaw");
                        double pitch = getMain().getConfig().getDouble("mines." + args[1] + ".spawnpoint.pitch");

                        World world = Bukkit.getWorld("world");
                        Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);

                        p.teleport(loc);
                    }
                }
            }
            else {
                p.sendMessage("§cYou don't have the permission to perform this command");
            }
        }
        return true;
    }
}
