package fr.drakyx.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Cuboid {

    private Location loc1;
    private Location loc2;

    public Cuboid(Location loc1, Location loc2) {
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public void setBlocks(Material material) {
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();
        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();

        int x_min = Math.min(x1, x2);
        int y_min = Math.min(y1, y2);
        int z_min = Math.min(z1, z2);

        int x_max = Math.max(x1, x2);
        int y_max = Math.max(y1, y2);
        int z_max = Math.max(z1, z2);

        for (int x = x_min; x <= x_max; x++) {
            for (int y = y_min; y <= y_max; y++) {
                for (int z = z_min; z <= z_max; z++) {
                    Location blockLoc = new Location(Bukkit.getWorld("world"), x, y, z);
                    Block block = blockLoc.getBlock();
                    block.setType(material);
                }
            }
        }
    }

    public Location getLoc1() {
        return loc1;
    }

    public Location getLoc2() {
        return loc2;
    }

    public void setLoc1(Location loc1) {
        this.loc1 = loc1;
    }

    public void setLoc2(Location loc2) {
        this.loc2 = loc2;
    }
}