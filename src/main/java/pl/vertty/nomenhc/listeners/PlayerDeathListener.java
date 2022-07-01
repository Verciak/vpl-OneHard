package pl.vertty.nomenhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.DeathAction;
import pl.vertty.nomenhc.objects.Guild;
import lombok.AllArgsConstructor;
import pl.vertty.nomenhc.helpers.ColorHelper;

import java.util.Date;

@AllArgsConstructor
public class PlayerDeathListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event){
        final Player player = event.getEntity(), killer = event.getEntity().getKiller();
        if(killer == null) return;
        final User pAccount = User.get(player.getUniqueId()), kAccount = User.get(killer.getUniqueId());
        final Guild pGuild = Guild.get(player.getName()), kGuild = Guild.get(killer.getName());
        if(pAccount == null || kAccount == null) return;

        int accountPointsAdd = (int) (30 + (pAccount.getPoints() - kAccount.getPoints()) * 0.2),
                accountPointsTake = -((accountPointsAdd / 6) * 2);

        final DeathAction deathAction = new DeathAction(
                killer.getUniqueId(),
                player.getUniqueId(),
                killer.getName(),
                killer.getName(),
                accountPointsAdd,
                accountPointsTake,
                new Date(System.currentTimeMillis())
        );

        pAccount.getDeathActions().add(deathAction);
        kAccount.getDeathActions().add(deathAction);

        pAccount.statsIncrement(accountPointsTake,0,1);
        kAccount.statsIncrement(accountPointsAdd,1,0);

        int guildPointsAdd = 30, guildPointsTake = -30;

        if(pGuild != null && kGuild != null && (pGuild.getUuid().equals(kGuild.getUuid()) || pGuild.getAllies().contains(kGuild.getUuid()))){
            guildPointsAdd = -guildPointsAdd;
            guildPointsTake = 0;
        }

        if(pGuild != null) pGuild.statIncrement(guildPointsTake, 0, 1);
        if(kGuild != null) kGuild.statIncrement(guildPointsAdd, 1, 0);

        Bukkit.getOnlinePlayers().forEach(target -> {
            target.sendMessage(ColorHelper.colored("&fGracz "+(pGuild == null ? "" : "&8[&4"+pGuild.getTag()+"&8]")+" &4" + player.getName() + " &8(&c"+accountPointsTake+"&8) &fzostal zabity przez "+(kGuild == null ? "" : "&8[&4"+kGuild.getTag()+"&8]")+" &4" + killer.getName() +" &8(&a+"+accountPointsAdd+"&8)"));
        });
    }

    @EventHandler
    public void onDeaths(final PlayerDeathEvent e) {
        e.setDeathMessage(null);
        final Player p = e.getEntity();
        final Combat combat = CombatManager.getCombat(p);
        if (combat == null) {
            return;
        }
        combat.setLastAttactTime(0L);
        combat.setLastAsystTime(0L);
        combat.setLastAsystPlayer(null);
        combat.setLastAttactkPlayer(null);
    }

}
