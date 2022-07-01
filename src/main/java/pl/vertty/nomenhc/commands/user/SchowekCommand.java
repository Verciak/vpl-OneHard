package pl.vertty.nomenhc.commands.user;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.menu.SchowekInventory;
import pl.vertty.nomenhc.menu.drop.DropInventory;

public class SchowekCommand extends PlayerCommand {

    public SchowekCommand() {
        super("schowek", "Komenda schowek", "schowek", "", new String[] { "schowek" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        SchowekInventory.open(p);
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
