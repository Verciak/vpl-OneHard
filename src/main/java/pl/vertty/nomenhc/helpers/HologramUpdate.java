package pl.vertty.nomenhc.helpers;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.objects.Guild;


public class HologramUpdate {
    public static void update(final Guild g) {
        for (final Hologram pe : HologramsAPI.getHolograms(GuildPlugin.getPlugin())) {
            if (pe.getLine(1).toString().contains("§7GILDIA: §9" + g.getTag() + " §8- §9" + g.getName())) {
                pe.delete();
            }
        }
        final Location l;

        if(Bukkit.getPlayer(g.getOwner()) == null){
            l = new Location(Bukkit.getWorlds().get(0), g.getCenterX(), g.getCenterY() +4, g.getCenterZ());
        } else {
            l = new Location(Bukkit.getWorlds().get(0), g.getCenterX(), g.getCenterY() + 4.5, g.getCenterZ());
        }
        final Hologram h = HologramsAPI.createHologram(GuildPlugin.getPlugin(), l);

        
        h.appendTextLine("§8###########################");
        h.appendTextLine("§7GILDIA: §9" + g.getTag() + " §8- §9" + g.getName());
        h.appendTextLine("§7Punkty: §9" + g.getPoints());
        h.appendTextLine("§7Lider: §9" + g.getOwner());
        h.appendTextLine("§7Czlonkowie: §9" + g.getMembers().size());
        h.appendTextLine("§7Zycia: " + multiplyLives(g.getLives()));
        h.appendTextLine("§7Rozmiar: §9" + g.getGuildSize() + "x" + g.getGuildSize());
        h.appendTextLine("§7Ochrona TNT: §9" + (g.getTntProtectionExpireDate().getTime() >= System.currentTimeMillis() ? DataUtil.secondsToString(g.getCreateDate().getTime() + TimeUtil.HOUR.getTime(24)) : "§cBRAK"));
        h.appendTextLine("§7Wygasa za: §9" + DataUtil.secondsToString(g.getExpireDate().getTime()));
        h.appendTextLine("§8###########################");
        if(Bukkit.getPlayer(g.getOwner()) == null){
            return;
        }
        ItemStack playerhead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta playerheadmeta = (SkullMeta) playerhead.getItemMeta();
        Player player = Bukkit.getPlayer(g.getOwner());
        playerheadmeta.setOwner(player.getName());
        playerheadmeta.setDisplayName(player.getName());
        playerhead.setItemMeta(playerheadmeta);
        h.insertItemLine(10, playerhead);

    }

    public static String multiplyLives(int lives){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < lives; i++){
            stringBuilder.append("§c❤");
        }
        return stringBuilder.toString();
    }
}