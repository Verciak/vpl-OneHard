package pl.vertty.nomenhc.listeners.magic_case;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CaseManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.ItemBuilder;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.CaseInv;

@AllArgsConstructor
public class CasePlaceListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (b.getType() == Material.CHEST && p.getItemInHand().getItemMeta().getDisplayName().equals(CaseManager.mcase.build().getItemMeta().getDisplayName())) {
            e.setCancelled(true);
            if (CaseManager.isInCase(p)) {
                ChatUtil.sendMessage(p, "&7Losujesz juz item... Zwolnij!");
                return;
            }
            Inventory inv = Bukkit.createInventory(p, 27, ChatUtil.fixColor("&9Losowanie przedmiotu..."));
            ItemBuilder backgroup = (new ItemBuilder(Material.VINE)).setTitle(ChatUtil.fixColor("&8*"));
            ItemBuilder win = (new ItemBuilder(Material.TRIPWIRE_HOOK)).setTitle(ChatUtil.fixColor("&aTWOJA WYGRANA"));
            int i;
            for (i = 0; i <= 3; i++)
                inv.setItem(i, backgroup.build());
            inv.setItem(4, win.build());
            for (i = 5; i <= 8; i++)
                inv.setItem(i, backgroup.build());
            for (i = 18; i <= 21; i++)
                inv.setItem(i, backgroup.build());
            inv.setItem(22, win.build());
            for (i = 23; i <= 26; i++)
                inv.setItem(i, backgroup.build());
            ItemStack i1 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            ItemStack i2 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            ItemStack i3 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            ItemStack i4 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            ItemStack i5 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            ItemStack i6 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            ItemStack i7 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            ItemStack i8 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            ItemStack i9 = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
            inv.setItem(9, i1);
            inv.setItem(10, i2);
            inv.setItem(11, i3);
            inv.setItem(12, i4);
            inv.setItem(13, i5);
            inv.setItem(14, i6);
            inv.setItem(15, i7);
            inv.setItem(16, i8);
            inv.setItem(17, i9);
            p.openInventory(inv);
            CaseInv caseInv = new CaseInv(p, inv);
            CaseManager.addCase(p, caseInv);
        }
    }
}

