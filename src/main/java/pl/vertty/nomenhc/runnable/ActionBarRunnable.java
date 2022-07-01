package pl.vertty.nomenhc.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.*;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.Teleport;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;

import java.text.DecimalFormat;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;


@AllArgsConstructor
public class ActionBarRunnable implements Runnable {

    private final GuildPlugin plugin;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public void register(){
        plugin.getServer().getScheduler().runTaskTimer(plugin,this,0, 20);
    }

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            User u = UserManager.get(player.getUniqueId());
            if (u != null) {
                int koxAmount = ItemHelper.getAmount(Material.GOLDEN_APPLE, player, (short) 1);
                int refAmount = ItemHelper.getAmount(Material.GOLDEN_APPLE, player, (short) 0);
                int pearlAmount = ItemHelper.getAmount(Material.ENDER_PEARL, player, (short) 0);
                int snowAmount = ItemHelper.getAmount(Material.SNOW_BALL, player, (short) 0);
                int arrowAmount = ItemHelper.getAmount(Material.ARROW, player, (short) 0);

                if (koxAmount > Config.getInstance().kox) {
                    int kf = koxAmount - Config.getInstance().kox;
                    ItemHelper.removeItems(Material.GOLDEN_APPLE, player, (short) 1, kf);
                    u.setKox(u.getKox() + kf);
                    player.sendMessage(ChatUtil.fixColor("&7Zabrano &9{AMOUNT} &7koxow do schowka").replace("{AMOUNT}", String.valueOf(kf)));
                }
                if (refAmount > Config.getInstance().ref) {
                    int rf = refAmount - Config.getInstance().ref;
                    ItemHelper.removeItems(Material.GOLDEN_APPLE, player, (short) 0, rf);
                    u.setRef(u.getRef() + rf);
                    player.sendMessage(ChatUtil.fixColor("&7Zabrano &9{AMOUNT} &7refili do schowka").replace("{AMOUNT}", String.valueOf(rf)));
                }
                if (pearlAmount > Config.getInstance().pearl) {
                    int pf = pearlAmount - Config.getInstance().pearl;
                    ItemHelper.removeItems(Material.ENDER_PEARL, player, (short) 0, pf);
                    u.setPearl(u.getPearl() + pf);
                    player.sendMessage(ChatUtil.fixColor("&7Zabrano &9{AMOUNT} &7perel do schowka").replace("{AMOUNT}", String.valueOf(pf)));
                }
                if (snowAmount > Config.getInstance().snowball) {
                    int pf = snowAmount - Config.getInstance().snowball;
                    ItemHelper.removeItems(Material.SNOW_BALL, player, (short) 0, pf);
                    u.setSnowball(u.getSnowball() + pf);
                    player.sendMessage(ChatUtil.fixColor("&7Zabrano &9{AMOUNT} &7sniezek do schowka").replace("{AMOUNT}", String.valueOf(pf)));
                }
                if (arrowAmount > Config.getInstance().arrow) {
                    int pf = arrowAmount - Config.getInstance().arrow;
                    ItemHelper.removeItems(Material.ARROW, player, (short) 0, pf);
                    u.setArrow(u.getArrow() + pf);
                    player.sendMessage(ChatUtil.fixColor("&7Zabrano &9{AMOUNT} &7strzal do schowka").replace("{AMOUNT}", String.valueOf(pf)));
                }
            }


            final Combat ua = CombatManager.getCombat(player);
            final Guild pGuild = Guild.get(player.getName()),
                    lGuild = Guild.get(player.getLocation());
            final Teleport teleport = Teleport.get(player.getUniqueId());

            StringBuilder builder = new StringBuilder();
            if (ua.hasFight()) {
                if (Config.getInstance().wiadomosci_actionbar) {
                    append(builder, Messages.getInstance().wiadomosci_actionbarpvp.replace("{TIME}", (DataUtil.secondsToString(ua.getLastAttactTime()).isEmpty() ? "0s" : (DataUtil.secondsToString(ua.getLastAttactTime())))));
                }
            } else {
                if (ua.wasFight()){
                    if(!ua.hasFight()){
                        append(builder, Messages.getInstance().wiadomosci_actionbarendpvp);
                        ChatUtil.sendMessage(player, ChatUtil.fixColor(Messages.getInstance().wiadomosci_actionbarendpvpchat));
                        ua.setLastAttactTime(0L);
                        ua.setLastAsystTime(0L);
                        ua.setLastAttactkPlayer(null);
                        ua.setLastAsystPlayer(null);
                    }
                }
            }
            if (lGuild != null) {
                final String guildMessageColor = pGuild == null ? "4" : pGuild.getAllies().contains(lGuild.getUuid()) ? "9" : pGuild.getUuid().equals(lGuild.getUuid()) ? "2" : "4";
                final double guildCenterDist = LocationHelper.getDistanceMax(player.getLocation(), lGuild.getCenterLocation());
                append(builder, Messages.getInstance().guildActionBar_terrain
                        .replace("{COLOR}", guildMessageColor)
                        .replace("{TAG}", lGuild.getTag())
                        .replace("{DISTANCE}", String.valueOf(df.format(guildCenterDist)))
                );
            }
            if (teleport != null) {
                append(builder, Messages.getInstance().teleport.replace("{TIME}", DateHelper.format(teleport.getEnd())));
            }
            MessageHelper.sendActionbar(player, colored(builder.toString()));
        });
    }

    private void append(StringBuilder builder, String string){
        if(builder.length() != 0) builder.append(Messages.getInstance().actionbar_append);
        builder.append(string);
    }

}
