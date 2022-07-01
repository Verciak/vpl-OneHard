package pl.vertty.nomenhc.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.ItemHelper;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.drop.CobbleXDrop;
import pl.vertty.nomenhc.objects.drop.Drop;
import pl.vertty.nomenhc.objects.drop.PandoraDrop;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
public class CobbleXListener implements Listener {

    private final GuildPlugin plugin;

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.MOSSY_COBBLESTONE) {
            if(player.getItemInHand().getItemMeta().hasDisplayName()){
                if(player.getItemInHand().getItemMeta().getDisplayName().contains(ChatUtil.fixColor("&9CobbleX"))){

                        int number_drop = RandomHelper.getRandomInt(0, DropManager.drops_cx.size());
                        if(number_drop == DropManager.drops_cx.size()){
                            number_drop = number_drop -1;
                        }
                        CobbleXDrop drop = DropManager.drops_cx.get(number_drop);
                        event.getBlock().setType(Material.AIR);
                            int amount = drop.getAmount();
                            ChatUtil.sendMessage(player,"&8>> &7Otworzyles &9CobbleX &7i trafiles: &9{NAME} &8(&fx{AMOUNT}&8)".replace("{NAME}", ChatUtil.fixColor(drop.getMessage()))
                                    .replace("{AMOUNT}", String.valueOf(amount)));
                            ItemStack drops = drop.getWhat();
                            drops.setAmount(amount);
                            ChatUtil.giveItems(player, drops);


                }
            }

        }
    }
}
