package pl.vertty.nomenhc.listeners.magic_case;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CaseManager;

@AllArgsConstructor
public class CaseKickQuitListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        left(e.getPlayer());
    }

    @EventHandler
    public void kick(PlayerKickEvent e) {
        left(e.getPlayer());
    }

    private void left(Player p) {
        if (CaseManager.isInCase(p))
            CaseManager.removeCase(p);
    }
}
