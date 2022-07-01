package pl.vertty.nomenhc.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ColorHelper;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.helpers.KitUtil;
import pl.vertty.nomenhc.helpers.TimeUtils;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.DiscordConfig;
import pl.vertty.nomenhc.objects.configs.DropConfig;
import pl.vertty.nomenhc.objects.configs.Messages;

public class ConfigReloadCommand extends PlayerCommand {

    public ConfigReloadCommand() {
        super("config", "Przeladowanie configu", "config", "cmd.admin.config", new String[] { "cr" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
//        for(Player player : Bukkit.getOnlinePlayers()) {
////            ScoreboardBuilder scoreboardBuilder = ScoreboardManager.getScoreboard(player);
////            if (scoreboardBuilder != null) {
////                scoreboardBuilder.hide();
////            }
//            long time = TimeUtils.getTime("10sek");
//            long timee = TimeUtils.getTime("20sek");
//            User user = UserManager.get(p.getUniqueId());
//            time += System.currentTimeMillis();
//            timee += System.currentTimeMillis();
//            user.setTurbo_exp(String.valueOf(time));
//            user.setTurbo_drop(String.valueOf(timee));
//            user.setTurbo(true);
//        }
        KitUtil.kits.clear();
        new KitUtil(GuildPlugin.getPlugin());
        Messages.load("./plugins/vpl-OneHard/messages.json");
        Config.load("./plugins/vpl-OneHard/config.json");
        DropConfig.load("./plugins/vpl-OneHard/DropConfig.json");
        DiscordConfig.load("./plugins/vpl-OneHard/DiscordConfig.json");
        DropManager.loadDrops();
        p.sendMessage(ColorHelper.colored("&aPrzeladowano config!"));
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        KitUtil.kits.clear();
        new KitUtil(GuildPlugin.getPlugin());
        Messages.load("./plugins/vpl-OneHard/messages.json");
        Config.load("./plugins/vpl-OneHard/config.json");
        DropConfig.load("./plugins/vpl-OneHard/DropConfig.json");
        DiscordConfig.load("./plugins/vpl-OneHard/DiscordConfig.json");
        DropManager.loadDrops();
        p.sendMessage(ColorHelper.colored("&aPrzeladowano config!"));
        return false;
    }


}

