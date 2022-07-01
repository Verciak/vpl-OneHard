package pl.vertty.nomenhc.commands.user;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.helpers.ChatUtil;
import java.util.HashMap;
import java.util.UUID;

public class TellCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    private static final HashMap<UUID, Long> times = new HashMap<>();
    private static final HashMap<UUID, UUID> lastMsg = new HashMap<>();
    
    public TellCommand() {
        super("msg", "Komenda msg", "/msg <gracz> <wiadomosc>", "", new String[] { "tell", "whisper" });
    }

    @Override
    public boolean onCommand(final Player player, final String[] args) {
        if (args.length < 2) {
            return ChatUtil.sendMessage(player, getUsage());
        }
        Player o = Bukkit.getPlayer(args[0]);
        if (o == null)
            return ChatUtil.sendMessage(player, "&4Blad: &cGracz nie jest online!");
        Long t = times.get(player.getUniqueId());
        if (t != null && System.currentTimeMillis() - t < 3000L)
            return ChatUtil.sendMessage(player, "&4Nie spamuj!");
        String message = ChatColor.stripColor(ChatUtil.fixColor(StringUtils.join(args, " ", 1, args.length)));
        lastMsg.put(player.getUniqueId(), o.getUniqueId());
        lastMsg.put(o.getUniqueId(), player.getUniqueId());
        times.put(player.getUniqueId(), System.currentTimeMillis());
        ChatUtil.sendMessage(player, "&3Ja -> " + o.getName() + "&b: " + message);
        return ChatUtil.sendMessage(o, "&3" + player.getName() + " -> Ja: &b" + message);
    }

    public static HashMap<UUID, UUID> getLastMsg() {
        return lastMsg;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }

}
