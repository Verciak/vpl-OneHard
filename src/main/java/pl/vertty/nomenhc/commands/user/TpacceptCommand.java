package pl.vertty.nomenhc.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.TpaUtil;
import pl.vertty.nomenhc.objects.Teleport;

public class TpacceptCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public TpacceptCommand() {
        super("tpaccept", "Komenda tpaccept", "tpaccept", "", new String[] { "tpaccept" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (TpaUtil.getLastSenderByReceiver().get(p) == null)
            return ChatUtil.sendMessage(p, "&4Blad: &7Nie masz oczekujacej prosby o teleportacje!");
        Player o = TpaUtil.getLastSenderByReceiver().get(p);
        if (o == null) {
            TpaUtil.denyRequest(o, p);
            return ChatUtil.sendMessage(p, "&4Blad: &7Nie masz oczekujacej prosby o teleportacje!");
        }
        if (p.getLocation().getY() < 3.0D)
            ChatUtil.sendMessage(p, "&4Blad: &c/tpaccept mozna uzyc tylko powyzej Y:3");
        if ((System.currentTimeMillis() - TpaUtil.getLastSenderRequestTime().get(o)) / 1000L > 60L) {
            TpaUtil.denyRequest(o, p);
            return ChatUtil.sendMessage(p, "&4Blad: &7Nie masz oczekujacej prosby o teleportacje!");
        }
        TpaUtil.acceptRequest(o, p);
        ChatUtil.sendMessage(o, "&8>> &7Gracz &9" + p.getName() + " &7zaakceptowal twoja prosbe o teleportacje!");
        new Teleport(o.getUniqueId(),System.currentTimeMillis() + 10 * 1000, Bukkit.getScheduler().runTaskLater(GuildPlugin.getPlugin(),() -> {
            Teleport.remove(o.getUniqueId());
            o.teleport(p.getLocation());
        },10*20));
        return ChatUtil.sendMessage(p, "&8>> &7Zaakceptowales prosbe o teleportacje gracza &9" + o.getName());
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
