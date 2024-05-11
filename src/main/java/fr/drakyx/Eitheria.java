package fr.drakyx;

import fr.drakyx.commands.mod.CommandReop;
import fr.drakyx.commands.utils.CommandMine;
import fr.drakyx.commands.utils.CommandPos;
import fr.drakyx.listeners.JoinServer;
import fr.drakyx.listeners.PlayerCommand;
import fr.drakyx.listeners.PlayerInteract;
import fr.drakyx.commands.mod.CommandInvsee;
import fr.drakyx.utils.Mine;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Eitheria extends JavaPlugin {

    private Map<UUID, Mine> mines = new HashMap<>();

    @Override
    public void onEnable() {
        loadMines();

        /*
         * Config
         */
        saveDefaultConfig();

        /*
         * Listeners
         */
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerCommand(), this);
        pm.registerEvents(new JoinServer(), this);

        /*
         * Commands
         */
        getCommand("invsee").setExecutor(new CommandInvsee());
        getCommand("reop").setExecutor(new CommandReop(this));
        getCommand("mine").setExecutor(new CommandMine(this));
        getCommand("pos").setExecutor(new CommandPos(this));
    }

    @Override
    public void onDisable() {
        saveMines();
    }

    private void saveMines() {
        for (Map.Entry<UUID, Mine> entry: mines.entrySet()) {
            UUID playerUUID = entry.getKey();
            Mine mine = entry.getValue();

            this.getConfig().set("mines." + playerUUID + ".spawnpoint" + ".x", mine.getX());
            this.getConfig().set("mines." + playerUUID + ".spawnpoint" + ".y", mine.getY());
            this.getConfig().set("mines." + playerUUID + ".spawnpoint" + ".z", mine.getZ());
            this.getConfig().set("mines." + playerUUID + ".spawnpoint" + ".yaw", mine.getYaw());
            this.getConfig().set("mines." + playerUUID + ".spawnpoint" + ".pitch", mine.getPitch());
            this.getConfig().set("mines." + playerUUID + ".level", mine.getLevel());
            this.saveConfig();
        }
    }

    private void loadMines() {
        if(this.getConfig().getConfigurationSection("mines") != null) {
            String keys = this.getConfig().getConfigurationSection("mines").getKeys(false).toString();
            String[] uuids = keys.substring(1, keys.length() - 1).split(", ");

            Map<UUID, Mine> mineMap = new HashMap<>();

            for (String uuid : uuids) {
                double x = this.getConfig().getDouble("mines." + uuid + ".spawnpoint.x");
                double y = this.getConfig().getDouble("mines." + uuid + ".spawnpoint.y");
                double z = this.getConfig().getDouble("mines." + uuid + ".spawnpoint.z");
                double yaw = this.getConfig().getDouble("mines." + uuid + ".spawnpoint.yaw");
                double pitch = this.getConfig().getDouble("mines." + uuid + ".spawnpoint.pitch");
                int level = this.getConfig().getInt("mines." + uuid + ".spawnpoint.level");

                mineMap.put(UUID.fromString(uuid), new Mine(x, y, z, (float) yaw, (float) pitch, level));
            }

            this.mines = mineMap;
        }
    }

    public Map<UUID, Mine> getMines() {
        return mines;
    }
}