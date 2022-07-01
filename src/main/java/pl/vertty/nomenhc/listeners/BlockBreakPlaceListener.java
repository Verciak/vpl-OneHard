package pl.vertty.nomenhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.Stone;
import pl.vertty.nomenhc.handlers.StoneManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.ItemBuilder;
import pl.vertty.nomenhc.helpers.LocationHelper;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;


@AllArgsConstructor
public class BlockBreakPlaceListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event){
        final Player player = event.getPlayer();
        final Guild pGuild = Guild.get(player.getName());
        final Guild guild = Guild.get(event.getBlock().getLocation());
        if(guild == null) return;
        if(guild.getMembers().contains(player.getName())
                && !LocationHelper.isBlockLocation(event.getBlock().getLocation(), guild.getCenterLocation())) return;
        if(LocationHelper.isBlockLocation(event.getBlock().getLocation(), guild.getCenterLocation())
                && pGuild != null
                && !guild.getUuid().equals(pGuild.getUuid())){
            if(guild.getNextConquerDate().getTime() < System.currentTimeMillis()){
                guild.setLives(guild.getLives() - 1);
                guild.setNextConquerDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
                if(guild.getLives() == 0) guild.delete("Brak zyc!");
                if(pGuild.getLives() < 5) pGuild.setLives(pGuild.getLives() + 1);
                plugin.getServer().getOnlinePlayers().forEach(target -> target.sendMessage(colored(
                        Messages.getInstance().guildRemoveLife)
                        .replace("{TAG}", guild.getTag())
                        .replace("{OTAG}", pGuild.getTag())
                ));
            }
            else player.sendMessage(colored(Messages.getInstance().guildRemoveLifeError));
        }
        if(!player.isOp()){
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event){
        final Player player = event.getPlayer();
        final Guild guild = Guild.get(event.getBlock().getLocation());
        if(guild == null) return;
        if(guild.getMembers().contains(player.getName())) return;
        if(!player.isOp()){
            event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getItemInHand() != null && event.getItemInHand().hasItemMeta() && event.getItemInHand().getItemMeta().hasDisplayName() && event.getItemInHand().getItemMeta().getDisplayName().contains(ChatUtil.fixColor("&9&lStoniarka"))) {
            StoneManager.create(event.getBlockPlaced().getLocation(), String.valueOf(20));
            event.getPlayer().playEffect(event.getBlock().getLocation(), Effect.MOBSPAWNER_FLAMES, 20);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        Stone s = StoneManager.getStone(event.getBlock().getLocation());
        if (s != null) {
            if (event.getPlayer().getItemInHand() != null && event.getPlayer().getItemInHand().getType().equals(Material.GOLD_PICKAXE)) {
                ItemBuilder stone_item = new ItemBuilder(Material.STONE, 1, (short) (byte) 0);
                stone_item.setTitle(ChatUtil.fixColor("&9&lStoniarka"));
                List<String> list = new ArrayList<>();
                list.add(ChatUtil.fixColor("&r"));
                list.add(ChatUtil.fixColor(" &8>> &7Postaw aby stworzyc stoniarke!"));
                list.add(ChatUtil.fixColor("&r"));
                list.add(ChatUtil.fixColor("&r"));
                list.add(ChatUtil.fixColor(" &8>> &cStoniarke da sie zniszczyc tylko &6zlotym kilofem!"));
                list.add(ChatUtil.fixColor("&r"));
                stone_item.addEnchantment(Enchantment.DIG_SPEED, 1);
                stone_item.addLores(list);
                this.plugin.getServer().getWorld("world").dropItemNaturally(event.getBlock().getLocation(), stone_item.build());
                StoneManager.delete(s);
                return;
            }
            Block b = event.getBlock();
            BukkitTask bukkitTask = new BukkitRunnable() {
                @Override
                public void run() {
                    b.setType(Material.STONE);
                }
            }.runTaskLater(this.plugin, 25L);


        }
    }

}
