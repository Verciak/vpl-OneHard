package pl.vertty.nomenhc.commands.user;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.menu.drop.DropInventory;

public class DropCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public DropCommand() {
        super("drop", "Komenda dropu", "drop", "", new String[] { "drop" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        DropInventory.open(p);
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
