package pl.vertty.nomenhc.commands.admin;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.handlers.BanHandler;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.DataUtil;
import pl.vertty.nomenhc.objects.Ban;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.objects.configs.Messages;

public class BanCommand extends PlayerCommand {

    public BanCommand() {
        super("ban", "Banowanie graczy", "/ban <name> <time/perm> [reason]", "cmd.admin.ban", new String[] { "" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        String name, uuid, ip, deviceid, IdentityPublicKey, ClientId;
        Ban b;
        if (args.length < 2) {
            ChatUtil.sendMessage(p, "&cPoprawne uzycie komendy: &7/ban <name> <time/perm> [reason]");
            return true;
        }
        String powod = Messages.getInstance().ban_no_reason;
        if (args.length > 2) {
            powod = StringUtils.join(args, " ", 2, args.length);
        }
        Player tar = Bukkit.getPlayer(args[0]);
        if (tar == null) {
            name = args[0];
            ip = "";
            uuid = "";
        } else {
            name = tar.getName();
            uuid = tar.getUniqueId().toString();
            ip = String.valueOf(tar.getAddress());
        }
        if (BanHandler.getBan(name) != null) {
           ChatUtil.sendMessage(p, Messages.getInstance().user_have_ban);
            return true;
        }
        long time = DataUtil.parseDateDiff(args[1], true);
        if (!args[1].equalsIgnoreCase("perm")) {
            b = new Ban(uuid, name, ip, powod, p.getName(), time);
        } else {
            b = new Ban(uuid, name, ip, powod, p.getName(), DataUtil.parseDateDiff("1000y", true));
        }
        BanHandler.createBan(b);
        String reason = Messages.getInstance().ban_reason;
        ChatUtil.sendMessage(Bukkit.getOnlinePlayers(), reason.replace("{ADMIN}", p.getName()).replace("{NAME}", b.getName()).replace("{REASON}", b.getReason()).replace("{EXPIRE}", (b.getExire() == 0L) ? "Nigdy" : DataUtil.getDate(b.getExire())));
        if (tar != null) {
            tar.kickPlayer(BanHandler.getReason(true, b));
        }
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        String name, uuid, ip, deviceid, IdentityPublicKey, ClientId;
        Ban b;
        if (args.length < 2) {
            ChatUtil.sendMessage(p, "&cPoprawne uzycie komendy: &7/ban <name> <time/perm> [reason]");
            return true;
        }
        String powod = Messages.getInstance().ban_no_reason;
        if (args.length > 2) {
            powod = StringUtils.join(args, " ", 2, args.length);
        }
        Player tar = Bukkit.getPlayer(args[0]);
        if (tar == null) {
            name = args[0];
            ip = "";
            uuid = "";
        } else {
            name = tar.getName();
            uuid = tar.getUniqueId().toString();
            ip = String.valueOf(tar.getAddress());
        }
        if (BanHandler.getBan(name) != null) {
            ChatUtil.sendMessage(p, Messages.getInstance().user_have_ban);
            return true;
        }
        long time = DataUtil.parseDateDiff(args[1], true);
        if (!args[1].equalsIgnoreCase("perm")) {
            b = new Ban(uuid, name, ip, powod, p.getName(), time);
        } else {
            b = new Ban(uuid, name, ip, powod, p.getName(), DataUtil.parseDateDiff("1000y", true));
        }
        BanHandler.createBan(b);
        String reason = Messages.getInstance().ban_reason;
        ChatUtil.sendMessage(Bukkit.getOnlinePlayers(), reason.replace("{ADMIN}", p.getName()).replace("{NAME}", b.getName()).replace("{REASON}", b.getReason()).replace("{EXPIRE}", (b.getExire() == 0L) ? "Nigdy" : DataUtil.getDate(b.getExire())));
        if (tar != null) {
            tar.kickPlayer(BanHandler.getReason(true, b));
        }
        return false;
    }
}

