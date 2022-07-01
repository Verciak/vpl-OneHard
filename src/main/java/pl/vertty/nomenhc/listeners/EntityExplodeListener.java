package pl.vertty.nomenhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.Guild;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class EntityExplodeListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityExplode(EntityExplodeEvent event){


        new ArrayList<Location>(){{
            final int centerX = (int) event.getEntity().getLocation().getX(),
                    centerY = (int) event.getEntity().getLocation().getY(),
                    centerZ = (int) event.getEntity().getLocation().getZ();

            for (int x = centerX - 5; x <= centerX + 5; x++)
                for (int y = (centerY - 5); y < (centerY + 5); y++)
                    for (int z = centerZ - 5; z <= centerZ + 5; z++)
                        if ((centerX - x) * (centerX - x) + (centerZ - z) * (centerZ - z) + ((centerY - y) * (centerY - y)) < 5 * 5)
                            add(new Location(Bukkit.getWorld("world"), x, y, z));
        }}.forEach(block -> {
            final Guild guild = Guild.get(block);
            if(guild == null){
                event.setCancelled(true);
                return;
            }
            if(guild.isInCentrum(block,8,1,7)){
                event.setCancelled(true);
                return;
            }
            if(guild.getTntProtectionExpireDate().getTime() >= System.currentTimeMillis() || block.getBlock().getType() == Material.SPONGE || block.getBlock().getType() == Material.BEDROCK) return;
            final Block material = block.getBlock();
            if (material.getType() == Material.BEDROCK) {
                return;
            } else if (material.getType() == Material.ENDER_CHEST || material.getType() == Material.OBSIDIAN) {
                if (RandomHelper.getChance(40)) {
                    block.getBlock().setType(Material.AIR);
                }
                return;
            } else if (material.getType() == Material.WATER || material.getType() == Material.LAVA) {
                if (RandomHelper.getChance(60)) {
                    block.getBlock().setType(Material.AIR);
                }
                return;
            } else {
                if (RandomHelper.getChance(90)) {
                    block.getBlock().setType(Material.AIR);
                }
            }
        });
    }

}
