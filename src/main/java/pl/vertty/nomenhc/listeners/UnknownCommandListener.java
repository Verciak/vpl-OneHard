package pl.vertty.nomenhc.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.ColorHelper;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
@AllArgsConstructor
public class UnknownCommandListener implements Listener
{

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }


    public static ArrayList<String> registeredCommands;

    @EventHandler
    private void onPlayerCommandPreprocessEvent(final PlayerCommandPreprocessEvent e) {
        final Player p = e.getPlayer();
        boolean valid = false;
        if(p.isOp()){
            return;
        }
        for (final HelpTopic cmd : GuildPlugin.getPlugin().getServer().getHelpMap().getHelpTopics()) {
            final String[] args = e.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("/" + cmd.getName().replace("/", "")) || args[0].equalsIgnoreCase("/:" + cmd.getName().replace("/", ""))) {
                valid = true;
                break;
            }

        }
        for (final String cmd : UnknownCommandListener.registeredCommands) {
            final String[] args = e.getMessage().split(" ");
            if (args[0].equalsIgnoreCase("/" + cmd) || args[0].equalsIgnoreCase("/:" + cmd)) {
                valid = true;
                break;
            }
        }
        if (!valid) {
            p.sendMessage(ColorHelper.colored("&4Komenda nie istnieje!"));
            e.setCancelled(true);
        }
    }

    static {
        UnknownCommandListener.registeredCommands = new ArrayList<>();
    }
}

