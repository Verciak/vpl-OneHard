package pl.vertty.nomenhc.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Guild;

@AllArgsConstructor

public class GuildBreakCenterListener implements Listener {
    private final GuildPlugin plugin;

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(event.getBlock().getLocation());
        if (guild == null) return;
        if (guild.isInCentrum(event.getBlock().getLocation(), 8, 1, 7)) {
            event.setCancelled(true);
            ChatUtil.sendActionBar(player, "&cNie mozesz usunac bloku z centrum gildii!");
        }
    }

    @EventHandler
    public void onFireSpread(BlockSpreadEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onLianes(BlockGrowEvent event) {
        event.setCancelled(true);
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(event.getBlock().getLocation());
        if (guild == null) return;
        if (guild.isInCentrum(event.getBlock().getLocation(), 8, 1, 7)) {
            event.setCancelled(true);
            ChatUtil.sendActionBar(player, "&cNie możesz tu umieszczać bloków!");
        }
    }

}
