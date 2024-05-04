package fr.drakyx.listeners;

import fr.drakyx.Eitheria;
import fr.drakyx.Mine;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteract implements Listener {

    private final Eitheria eitheria;

    public PlayerInteract(Eitheria eitheria) {
        this.eitheria = eitheria;
    }

    public Eitheria getMain() {
        return eitheria;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Inventory inventory = Bukkit.createInventory(null, 3*9, "Upgrade Pickaxe");

        if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                p.openInventory(inventory);
            }
        }

        else if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Block b = e.getClickedBlock();

            if(b.getType().equals(Material.PRISMARINE)) {
                if (getMain().getMines().containsKey(p.getUniqueId())) {
                    Mine mine = getMain().getMines().get(p.getUniqueId());

                    World world = Bukkit.getWorld("world");
                    Location loc = new Location(world, mine.getX(), mine.getY(), mine.getZ(), mine.getYaw(), mine.getPitch());

                    p.teleport(loc);
                }
                else {
                    String keys = getMain().getConfig().getConfigurationSection("mines").getKeys(false).toString();
                    String[] names = keys.substring(1, keys.length() - 1).split(", ");

                    for(String name : names) {
                        try {
                            int name_int = Integer.valueOf(name);

                            getMain().getConfig().set("mines." + p.getUniqueId() + ".spawnpoint" + ".x", getMain().getConfig().getDouble("mines." + name + ".spawnpoint.x"));
                            getMain().getConfig().set("mines." + p.getUniqueId() + ".spawnpoint" + ".y", getMain().getConfig().getDouble("mines." + name + ".spawnpoint.y"));
                            getMain().getConfig().set("mines." + p.getUniqueId() + ".spawnpoint" + ".z", getMain().getConfig().getDouble("mines." + name + ".spawnpoint.z"));
                            getMain().getConfig().set("mines." + p.getUniqueId() + ".spawnpoint" + ".yaw", getMain().getConfig().getDouble("mines." + name + ".spawnpoint.yaw"));
                            getMain().getConfig().set("mines." + p.getUniqueId() + ".spawnpoint" + ".pitch", getMain().getConfig().getDouble("mines." + name + ".spawnpoint.pitch"));
                            getMain().getConfig().set("mines." + p.getUniqueId() + ".level", 0);
                            getMain().getConfig().set("mines." + name, null);
                            getMain().saveConfig();

                            double x = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.x");
                            double y = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.y");
                            double z = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.z");
                            double yaw = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.yaw");
                            double pitch = getMain().getConfig().getDouble("mines." + p.getUniqueId() + ".spawnpoint.pitch");

                            World world = Bukkit.getWorld("world");
                            Location loc = new Location(world, x, y, z, (float) yaw, (float) pitch);

                            p.teleport(loc);

                            break;
                        }
                        catch (NumberFormatException exception) {

                        }
                    }
                }
            }

            else if (p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                p.openInventory(inventory);
            }
        }
    }
}
