package pl.vertty.nomenhc.listeners;

import lombok.AllArgsConstructor;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.User;

import java.util.Collection;


@AllArgsConstructor
public class AsyncChatListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        User u = UserManager.get(p.getName());
        e.setCancelled(true);
        for(Player pa : Bukkit.getOnlinePlayers()){
            String guild = "";
            if(GuildHandler.get(p.getName()) != null){
                Guild pGuild = GuildHandler.get(p.getName());
                Guild lGuild = GuildHandler.get(pa.getName());
                final String guildMessageColor;
                if(lGuild == null){
                    guildMessageColor = "&c";
                }else{
                    guildMessageColor = pGuild == null ? "&c" : pGuild.getAllies().contains(lGuild.getUuid()) ? "&9" : pGuild.getUuid().equals(lGuild.getUuid()) ? "&2" : "&c";
                }
                guild = guildMessageColor + GuildHandler.get(p.getName()).getTag() + " ";
            }
            String prefix = "";
            String color = "";
            if(pa.hasPermission("root")){
                prefix = ChatUtil.fixColor("&4ROOT ");
                color = ChatUtil.fixColor("&f");
            }
            else if (pa.hasPermission("admin")) {
                prefix = ChatUtil.fixColor("&cADMIN ");
                color = ChatUtil.fixColor("&f");
            }
            else if (pa.hasPermission("moderator")) {
                prefix = ChatUtil.fixColor("&2MOD ");
                color = ChatUtil.fixColor("&f");
            }
            else if (pa.hasPermission("helper")) {
                prefix = ChatUtil.fixColor("&3HELPER ");
                color = ChatUtil.fixColor("&f");
            }
            else if (pa.hasPermission("sponsor")) {
                prefix = ChatUtil.fixColor("&bSPONSOR ");
                color = ChatUtil.fixColor("&f");
            }
            else if (pa.hasPermission("svip")) {
                prefix = ChatUtil.fixColor("&6SVIP ");
                color = ChatUtil.fixColor("&f");
            }
            else if (pa.hasPermission("yt")) {
                prefix = ChatUtil.fixColor("&cY&fT ");
                color = ChatUtil.fixColor("&f");
            }
            else if (pa.hasPermission("vip")) {
                prefix = ChatUtil.fixColor("&eVIP ");
                color = ChatUtil.fixColor("&f");
            } else {
                prefix = "";
                color = ChatUtil.fixColor("&7");
            }
            ChatUtil.sendMessage(pa, "{PREFIX}{GUILD}&7{POINTS} {COLOR}{NAME} &8>> {COLOR}{MESSAGE}"
                    .replace("{NAME}", p.getName())
                    .replace("{MESSAGE}", e.getMessage())
                    .replace("{POINTS}", String.valueOf(u.getPoints()))
                    .replace("{COLOR}", color)
                    .replace("{PREFIX}", prefix)
                    .replace("{GUILD}", guild));
        }
    }


}
