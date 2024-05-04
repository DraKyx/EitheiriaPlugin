package fr.drakyx.commands.mod;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInvsee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cYou're not a player !");
        }

        Player p = (Player) commandSender;
        Player target = Bukkit.getPlayer(args[0]);

        p.openInventory(target.getInventory());

        return false;
    }
}
