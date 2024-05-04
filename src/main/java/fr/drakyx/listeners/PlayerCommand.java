package fr.drakyx.listeners;

import fr.drakyx.enums.DisabledCommands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        for(DisabledCommands disabledCommands : DisabledCommands.values()) {
            String command = disabledCommands.toString();

            if(command.contains("_")) {
                String[] list_command = command.split("_");
                String real_command = list_command[0] + " " + list_command[1];

                if (!(p.hasPermission("admin")) && e.getMessage().equals("/" + real_command)) {
                    e.setCancelled(true);
                    p.sendMessage("§cYou don't have the permission to perform this command");
                }
            }
            else {
                if (!(p.hasPermission("admin")) && e.getMessage().equals("/" + command)) {
                    e.setCancelled(true);
                    p.sendMessage("§cYou don't have the permission to perform this command");
                }
            }
        }
    }
}
