package pl.vertty.nomenhc.listeners.magic_case;

import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CaseManager;

@AllArgsConstructor
public class CaseDropListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void drop(PlayerDropItemEvent e) {
        if (CaseManager.isInCase(e.getPlayer()))
            e.setCancelled(true);
    }
}
