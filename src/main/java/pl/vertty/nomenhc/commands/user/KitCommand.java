package pl.vertty.nomenhc.commands.user;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.helpers.KitUtil;
import pl.vertty.nomenhc.menu.drop.DropInventory;

public class KitCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public KitCommand() {
        super("kit", "Komenda kity", "kit", "", new String[] { "kits" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        KitUtil.open(p);
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
