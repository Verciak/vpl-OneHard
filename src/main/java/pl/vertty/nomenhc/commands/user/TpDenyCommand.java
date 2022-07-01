package pl.vertty.nomenhc.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.TpaUtil;
import pl.vertty.nomenhc.objects.Teleport;

public class TpDenyCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public TpDenyCommand() {
        super("tpadeny", "Komenda tpadeny", "tpadeny", "", new String[] { "tpdeny" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (TpaUtil.getLastSenderByReceiver().get(p) == null)
            return ChatUtil.sendMessage(p, "&4Blad: &cNie masz oczekujacej prosby o teleportacje!");
        Player o = TpaUtil.getLastSenderByReceiver().get(p);
        if (o == null) {
            TpaUtil.denyRequest(o, p);
            return ChatUtil.sendMessage(p, "&4Blad: &cNie masz oczekujacej prosby o teleportacje!");
        }
        if ((System.currentTimeMillis() - TpaUtil.getLastSenderRequestTime().get(o)) / 1000L > 60L) {
            TpaUtil.denyRequest(o, p);
            return ChatUtil.sendMessage(p, "&4Blad: &cNie masz oczekujacej prosby o teleportacje!");
        }
        TpaUtil.denyRequest(o, p);
        ChatUtil.sendMessage(o, "&8>> &7Gracz &9" + p.getName() + " &7odrzucil twoja prosbe o teleportacje!");
        return ChatUtil.sendMessage(p, "&8>> &7Odrzuciles prosbe o teleportacje gracza &9" + o.getName());
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
