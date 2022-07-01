package pl.vertty.nomenhc.commands.admin;


import org.apache.commons.lang.StringUtils;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.handlers.WhiteListManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.User;

public class WhiteListCommand extends PlayerCommand {

    public WhiteListCommand() {
        super("whitelist", "whitelist", "/whitelist <on|off|add|remove|list> [gracz]", "cmd.admin.whitelist", new String[] { "wl" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if (args.length < 1) {
            return ChatUtil.sendMessage(p, getUsage());
        }
        String s2 = args[0];
        if (s2.equals("remove")) {
            User user = UserManager.get(args[1]);
            if (args.length != 2) {
                return ChatUtil.sendMessage(p, getUsage());
            }
            if(user == null){
                return ChatUtil.sendMessage(p, "&4Blad: &cTen gracz nie jest w bazie danych!");
            }
            if (!user.isWhitelist()) {
                return ChatUtil.sendMessage(p, "&4Blad: &cTen gracz nie jest na whitelist!");
            }

            WhiteListManager.removeWhitelist(args[1]);
            return ChatUtil.sendMessage(p, "&8>> &cUsunieto &6" + args[1] + "&c z whitelist!");
        }
        if (s2.equals("on")) {
            WhiteListManager.setWhitelist(true);
            return ChatUtil.sendMessage(p, "&8>> &cWhiteList zostalo wlaczone!");
        }
        if (s2.equals("add")) {
            User user = UserManager.get(args[1]);
            if (args.length != 2) {
                return ChatUtil.sendMessage(p, "&6Prawidlowe uzycie: &c" + getUsage());
            }
            if(user == null){
                return ChatUtil.sendMessage(p, "&4Blad: &cTen gracz nie jest w bazie danych!");
            }
            if (user.isWhitelist()) {
                return ChatUtil.sendMessage(p, "&4Blad: &cTen gracz jest juz na whitelist!");
            }
            WhiteListManager.addWhitelist(args[1]);
            return ChatUtil.sendMessage(p, "&8>> &cDodano &6" + args[1] + "&c na whitelist!");
        }
        if (s2.equals("off")) {
            WhiteListManager.setWhitelist(false);
            return ChatUtil.sendMessage(p, "&8>> &cWhiteList zostalo wylaczone!");
        }
        if (s2.equals("list")) {
            return ChatUtil.sendMessage(p, "&8>> &cLista graczy na whitelist: \n &6" + StringUtils.join(WhiteListManager.getWhitelisted(), ", "));
        }
        return ChatUtil.sendMessage(p, "&6Prawidlowe uzycie: &c" + getUsage());
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        if (args.length < 1) {
            return ChatUtil.sendMessage(p, getUsage());
        }
        String s2 = args[0];
        if (s2.equals("remove")) {
            User user = UserManager.get(args[1]);
            if (args.length != 2) {
                return ChatUtil.sendMessage(p, getUsage());
            }
            if(user == null){
                return ChatUtil.sendMessage(p, "&4Blad: &cTen gracz nie jest w bazie danych!");
            }
            if (!user.isWhitelist()) {
                return ChatUtil.sendMessage(p, "&4Blad: &cTen gracz nie jest na whitelist!");
            }
            WhiteListManager.removeWhitelist(args[1]);
            return ChatUtil.sendMessage(p, "&8>> &cUsunieto &6" + args[1] + "&c z whitelist!");
        }
        if (s2.equals("on")) {
            WhiteListManager.setWhitelist(true);
            return ChatUtil.sendMessage(p, "&8>> &cWhiteList zostalo wlaczone!");
        }
        if (s2.equals("add")) {
            User user = UserManager.get(args[1]);
            if (args.length != 2) {
                return ChatUtil.sendMessage(p, "&6Prawidlowe uzycie: &c" + getUsage());
            }
            if(user == null){
                return ChatUtil.sendMessage(p, "&4Blad: &cTen gracz nie jest w bazie danych!");
            }
            if (user.isWhitelist()) {
                return ChatUtil.sendMessage(p, "&4Blad: &cTen gracz jest juz na whitelist!");
            }
            WhiteListManager.addWhitelist(args[1]);
            return ChatUtil.sendMessage(p, "&8>> &cDodano &6" + args[1] + "&c na whitelist!");
        }
        if (s2.equals("off")) {
            WhiteListManager.setWhitelist(false);
            return ChatUtil.sendMessage(p, "&8>> &cWhiteList zostalo wylaczone!");
        }
        if (s2.equals("list")) {
            return ChatUtil.sendMessage(p, "&8>> &cLista graczy na whitelist: \n &6" + StringUtils.join(WhiteListManager.getWhitelisted(), ", "));
        }
        return ChatUtil.sendMessage(p, "&6Prawidlowe uzycie: &c" + getUsage());
    }


}

