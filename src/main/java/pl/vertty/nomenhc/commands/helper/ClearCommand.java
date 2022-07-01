package pl.vertty.nomenhc.commands.helper;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.configs.Messages;
import pl.vertty.nomenhc.objects.drop.Drop;

public class ClearCommand extends PlayerCommand {

    public ClearCommand() {
        super("clear", "Czyszczenie EQ", "clear", "cmd.admin.clear", new String[] { "clear" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (args.length == 0) {
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
            p.getInventory().clear();
            ChatUtil.sendMessage(p, Messages.getInstance().clear_eq);
            return true;
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null) {
            ChatUtil.sendMessage(p, Messages.getInstance().clear_error);
            return true;
        }
        o.getInventory().clear();
        o.getInventory().setHelmet(null);
        o.getInventory().setChestplate(null);
        o.getInventory().setLeggings(null);
        o.getInventory().setBoots(null);
        ChatUtil.sendMessage(p, Messages.getInstance().clear_eq);
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p0, String[] p1) {
        return false;
    }


}

