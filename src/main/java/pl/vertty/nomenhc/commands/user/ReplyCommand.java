package pl.vertty.nomenhc.commands.user;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.helpers.ChatUtil;

import java.util.HashMap;
import java.util.UUID;


public class ReplyCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    private static final HashMap<UUID, Long> times = new HashMap<>();

    public ReplyCommand() {
        super("reply", "Komenda reply", "/r <wiadomosc>", "", new String[] { "r" });
    }

    @Override
    public boolean onCommand(final Player player, final String[] args) {
        if (args.length < 1) {
            return ChatUtil.sendMessage(player, getUsage());
        }
        UUID last = TellCommand.getLastMsg().get(player.getUniqueId());
        if (last == null)
            return ChatUtil.sendMessage(player, "&4Blad: &cNie masz komu odpisac!");
        Player o = Bukkit.getPlayer(last);
        if (o == null)
            return ChatUtil.sendMessage(player, "&4Blad: &cGracz nie jest online!");
        Long t = times.get(player.getUniqueId());
        if (t != null && System.currentTimeMillis() - t < 3000L)
            return ChatUtil.sendMessage(player, "&4Nie spamuj!");
        times.put(player.getUniqueId(), System.currentTimeMillis());
        String message = ChatColor.stripColor(ChatUtil.fixColor(StringUtils.join(args, " ")));
        TellCommand.getLastMsg().put(player.getUniqueId(), o.getUniqueId());
        TellCommand.getLastMsg().put(o.getUniqueId(), player.getUniqueId());
        ChatUtil.sendMessage(player, "&3Ja -> " + o.getName() + "&b: " + message);
        return ChatUtil.sendMessage(o, "&3" + player.getName() + " -> Ja: &b" + message);
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
