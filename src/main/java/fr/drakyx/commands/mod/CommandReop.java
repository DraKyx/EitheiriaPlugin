package fr.drakyx.commands.mod;

import fr.drakyx.Eitheria;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandReop implements CommandExecutor {

    private final Eitheria eitheria;

    public CommandReop(Eitheria eitheria) {
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

        if (getMain().getConfig().getString("op").contains(p.getName())) {
            p.setOp(true);
        }

        return false;
    }
}
