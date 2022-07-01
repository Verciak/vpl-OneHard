package pl.vertty.nomenhc.helpers;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.configs.Config;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class GuildHelper {


    public static void createRoom(Guild guild){
        final Location center = new Location(Bukkit.getWorld("world"), guild.getCenterX(),guild.getCenterY(),guild.getCenterZ());

        Location location = new Location(Bukkit.getWorld("world"), center.getX(), center.getY(), center.getZ());
        Location c = guild.getCenterLocation();
        for (Location loc : SpaceUtil.getSquare(c, 8, 9)) {
            loc.getBlock().setType(Material.AIR);
        }
        GuildSchematic.pasteSchematic(Bukkit.getPlayer(guild.getOwner()));
        Block block = location.getBlock();
        block.setType(Material.SPONGE);
        clearItems();
    }

    public static void removeRoom(Guild guild){
        for (final Hologram pe : HologramsAPI.getHolograms(GuildPlugin.getPlugin())) {
            if (pe.getLine(1).toString().contains("§7GILDIA: §9" + guild.getTag() + " §8- §9" + guild.getName())) {
                pe.delete();
            }
        }
        final Location center = new Location(Bukkit.getWorld("world"),guild.getCenterX(),guild.getCenterY() - 1,guild.getCenterZ());
        for (Location loc : SpaceUtil.getSquare(center, 8, 9)) {
            loc.getBlock().setType(Material.AIR);
        }
        clearItems();
    }

    public static boolean isTooCloseToGuild(Location location){
        return GuildHandler.getGuilds().stream().anyMatch(guild -> LocationHelper.getDistanceMin(location,guild.getCenterLocation()) < ((Config.getInstance().guildDefaultSize * 2) + 15));
    }

    public static Set<Player> getGuildOnlinePlayers(Guild guild){
        return Bukkit.getOnlinePlayers().stream()
                .filter(player -> guild.getMembers().contains(player.getName()))
                .collect(Collectors.toSet());
    }

    public static void clearItems(){
        World world = Bukkit.getWorld("world");
        List<Entity> entList = world.getEntities();
        for(Entity current : entList){
            if (current instanceof Item){
                current.remove();
            }
        }
    }

}
