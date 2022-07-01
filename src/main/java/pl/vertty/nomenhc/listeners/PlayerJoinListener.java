package pl.vertty.nomenhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.BanHandler;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.Ban;
import pl.vertty.nomenhc.objects.Combat;
import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.objects.configs.Messages;
import pl.vertty.nomenhc.objects.drop.Drop;


@AllArgsConstructor
public class PlayerJoinListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPreJoin(PlayerJoinEvent event) {
        for(Player p : Bukkit.getOnlinePlayers()){
            User u = UserManager.get(p.getName());
            if(u.isWhitelist_status()){
                if(!u.isWhitelist()){
                    event.getPlayer().kickPlayer(ChatUtil.fixColor(("{N}" +
                            "&8====================================={N}" +
                            " &cNa serwerze trwaja prace techniczne{N}" +
                            "  &cDiscord: &4discord.gg/onehardpl{N}" +
                            "&8====================================={N}").replace("{N}", "\n")));
                }
            }
        }
        Ban b = BanHandler.getBan(event.getPlayer().getName());
        if (b != null) {
            if (!b.isExpired()) {
                event.getPlayer().kickPlayer(ChatUtil.fixColor(BanHandler.getReason(false, b)));
                return;
            }
            b.delete();
            BanHandler.bans.remove(b.getName());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event){
        final Player player = event.getPlayer();
        final User account = User.get(player.getUniqueId());
        if(account == null){
            new User(player);
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        e.setJoinMessage("");
        final Combat combat = CombatManager.getCombat(p);
        if (combat == null) {
            CombatManager.createCombat(p);
        }
    }
}
