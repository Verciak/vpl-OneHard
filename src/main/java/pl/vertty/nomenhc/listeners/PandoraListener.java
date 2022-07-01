package pl.vertty.nomenhc.listeners;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.ItemHelper;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.drop.Drop;
import pl.vertty.nomenhc.objects.drop.PandoraDrop;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class PandoraListener implements Listener {

    private final GuildPlugin plugin;

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.DRAGON_EGG) {
            if(player.getItemInHand().getItemMeta().hasDisplayName()){
                if(player.getItemInHand().getItemMeta().getDisplayName().contains(ChatUtil.fixColor("&9PANDORA"))){
                    for(PandoraDrop drop : DropManager.drops_pandora){
                        event.getBlock().setType(Material.AIR);
                        if(RandomHelper.getChance(drop.getChance())){
                            int amount;
                            if(drop.getMaxAmount() == 1){
                                amount = 1;
                            }else {
                                amount = RandomHelper.getRandomInt(drop.getMinAmount(), drop.getMaxAmount());

                            }
                            Bukkit.broadcastMessage(ChatUtil.fixColor("&8>> &7Gracz &9{PLAYER} &7otworzyl &9PANDORE &7i trafil: &9{NAME} &8- &7{CHANCE} &8(&fx{AMOUNT}&8)").replace("{PLAYER}", player.getName()).replace("{NAME}", ChatUtil.fixColor(drop.getMessage()))
                                    .replace("{CHANCE}", String.valueOf(drop.getChance()))
                                    .replace("{AMOUNT}", String.valueOf(amount)));
                            ItemStack drops = drop.getWhat();
                            drops.setAmount(amount);;
                            ChatUtil.giveItems(player, drops);
                        }
                    }
                }
            }

        }
    }
}
