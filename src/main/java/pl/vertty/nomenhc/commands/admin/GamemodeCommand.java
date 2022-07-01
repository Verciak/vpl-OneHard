package pl.vertty.nomenhc.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.commands.Command;
import pl.vertty.nomenhc.helpers.ChatUtil;

public class GamemodeCommand extends Command
{
    public GamemodeCommand() {
        super("gamemode", "Zmiana trybu gry graczy", "/gamemode [gracz] <tryb>", "cmd.admin.gm", new String[] { "gm", "gmode" });
    }

    @Override
    public boolean onExecute(final CommandSender sender, final String[] args) {
        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "&cPoprawne uzycie komendy: &7/gamemode [gracz] <tryb>");
            return true;
        }
        final Player p = (Player)sender;
        if (args.length == 1) {
            p.setGameMode(GameMode.getByValue(Integer.parseInt(args[0])));
            ChatUtil.sendMessage(sender, "&8» &7Twoj tryb gamemode zostal zmieniony na &b" + args[0] + "&7!");
            return false;
        }
        final Player o = Bukkit.getPlayer(args[1]);
        if (o == null) {
            ChatUtil.sendMessage(sender, "&c&lBlad: &7Gracz jest offline");
            return false;
        }
        o.setGameMode(GameMode.getByValue(Integer.parseInt(args[0])));
        ChatUtil.sendMessage(o, "&8» &7Twoj tryb gamemode zostal zmieniony na &b" + args[0] + "&7 przez &3" + sender.getName() + "&7!");
        ChatUtil.sendMessage(sender, "&8» &7Zmieniles tryb gamemode graczowi &3" + o.getName() + " &7na &b" + args[0] + "&7!");
        return true;
    }
}

