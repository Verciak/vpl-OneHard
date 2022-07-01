package pl.vertty.nomenhc.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.drop.Drop;


@AllArgsConstructor
public class DropListener implements Listener {

    private final GuildPlugin plugin;

    public void register() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        if (event.getBlock().getType() == Material.STONE) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
                if (!DropManager.isNoCobble(player.getUniqueId())) {
                    player.getInventory().addItem(new ItemStack(4, 1, (short) 0));
                }
                User user = UserManager.get(player.getUniqueId());
                int exp = 3;
                if (!user.getTurbo_exp().contains("0")) {
                    exp += 10;
                } else {
                    exp = 3;
                }
                player.giveExp(exp);
                for (Drop d : DropManager.drops) {
                    ItemStack itemDrop = d.getWhat().clone();
                    if (d.isDisabled(player.getUniqueId())) {
                        return;
                    }
                    double chance = d.getChance();
                    if (player.hasPermission("sponsor")) {
                        chance += 1;
                    } else if (player.hasPermission("svip")) {
                        chance += 0.50;
                    } else if (player.hasPermission("vip")) {
                        chance += 0.25;
                    } else {
                        chance = d.getChance();
                    }
                    if (!user.getTurbo_drop().contains("0")) {
                        chance += 1.5;
                    }
                    if (!RandomHelper.getChance(chance)) {
                        continue;
                    }
                    Enchantment enchantment = Enchantment.getById(player.getInventory().getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS));
                    if (enchantment != null) {
                        int anInt = RandomHelper.getRandomInt(d.getMinAmount(), d.getMaxAmount());
                        itemDrop.setAmount(anInt);
                    }
                    if (!d.isDisabled(player.getUniqueId())) {
                        ChatUtil.giveItems(player, itemDrop);

                        int texp = d.getExp();
                        if (!user.getTurbo_exp().contains("0")) {
                            texp += 10;
                        } else {
                            texp = d.getExp();
                        }
                        player.giveExp(texp);
                    }
                    if (!DropManager.isNoMsg(player.getUniqueId()) && !d.getDisabled().contains(player.getUniqueId())) {

                        ChatUtil.sendActionBar(player, ("&8* &7Trafiles na &l&9{ITEM} &fx{AMOUNT} &r&8*")
                                .replace("{ITEM}", d.getMessage())
                                .replace("{AMOUNT}", Integer.toString(itemDrop.getAmount()))
                        );
                    }
                }
            }
        }
    }
}
