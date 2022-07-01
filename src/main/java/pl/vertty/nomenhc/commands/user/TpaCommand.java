package pl.vertty.nomenhc.commands.user;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.TpaUtil;
import pl.vertty.nomenhc.objects.Teleport;

public class TpaCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public TpaCommand() {
        super("tpa", "Komenda tpa", "tpa <nick>", "", new String[] { "tpa" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (args.length < 1) {
            String msg = "tpa <nick>";
            msg = msg.replace("{USAGE}", getUsage());
            return ChatUtil.sendMessage(p, msg);
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null)
            return ChatUtil.sendMessage(p, "&4Blad: &7Gracz nie jest online!");
        if (o == p)
            return ChatUtil.sendMessage(p, "&4Blad: &7Nies mozesz wyslac zaproszenia sam do siebie!");
        if (TpaUtil.getLastSenderByReceiver().get(o) == p)
            return ChatUtil.sendMessage(p, "&4Blad: &7Ten gracz ma juz zaproszenie do teleportu od ciebie!");
        TpaUtil.sentRequest(p, o);
        ChatUtil.sendMessage(p, "&8>> &7Wyslales prosbe o teleportacje do gracz &9" + o.getDisplayName());
        ChatUtil.sendMessage(o, "&7Gracz &9" + p.getDisplayName() + " &7chce sie do ciebie teleportowac!");
        ChatUtil.sendMessage(o, "  &7Aby zaakceptowac zaproszenie wpisz &9/tpaccept");
        ChatUtil.sendMessage(o, "  &7Aby odrzucic zaproszenie wpisz &9/tpadeny");
        return ChatUtil.sendMessage(o, "  &7Zaproszenie jest wazne przez &960 &7sec.");
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
