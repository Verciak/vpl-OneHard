package pl.vertty.nomenhc.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.objects.Teleport;
import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.helpers.ColorHelper;


@AllArgsConstructor
public class TeleportCancelListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event){
        final Player player = event.getPlayer();
        final Location locFrom = event.getFrom(), locTo = event.getTo();
        if (locFrom.getX() == locTo.getX() && locFrom.getZ() == locTo.getZ()) return;
        final Teleport teleport = Teleport.get(player.getUniqueId());
        if(teleport == null) return;
        if(!teleport.isTeleporting()) return;
        teleport.getTask().cancel();
        Teleport.remove(player.getUniqueId());
        player.sendMessage(ColorHelper.colored("&cTeleportacja zostala przerwana!"));
    }

}
