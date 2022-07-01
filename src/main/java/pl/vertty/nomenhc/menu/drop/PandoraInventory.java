package pl.vertty.nomenhc.menu.drop;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.GlassColor;
import pl.vertty.nomenhc.helpers.ItemBuilder;
import pl.vertty.nomenhc.objects.drop.PandoraDrop;

import java.text.DecimalFormat;
import java.util.Arrays;


public class PandoraInventory {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void setLargeGui(Inventory inv) {
        int[] black = {
                1, 2, 3, 4, 5, 6, 7, 9, 17,
                18,26,27,35,36,44,46,47,48,49,50,51,52 };
        int[] blue = { 0, 8, 45, 53 };
        for (int b : black)
            inv.setItem(b, GlassColor.get(GlassColor.BLACK).setTitle(ChatUtil.fixColor("&r")).build());
        for (int b : blue)
            inv.setItem(b, GlassColor.get(GlassColor.BLUE).setTitle(ChatUtil.fixColor("&r")).build());
    }

    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9*6, ChatUtil.fixColor("&r&9&lMENU DROPU"));
        setLargeGui(inv);

        for (PandoraDrop drop : DropManager.drops_pandora) {

            ItemStack dropItem = drop.getWhat();
            ItemMeta meta = dropItem.getItemMeta();
            double chance = drop.getChance();
            meta.setDisplayName(ChatUtil.fixColor("&r&9&l" + drop.getMessage()));
            meta.setLore(Arrays.asList(ChatUtil.fixColor(new String[]{
                    "&r&8>> &7Szansa: &9" + df.format(chance) + "%",
                    "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9" + drop.getMaxAmount(),
            })));
            dropItem.setItemMeta(meta);
            inv.addItem(dropItem);
        }
        ItemBuilder back = new ItemBuilder(Material.BARRIER, 1);
        back.setTitle(ChatUtil.fixColor("&r&4&lPOWROT"));
        back.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby wrocic!"})));
        inv.setItem(49, back.build());
        player.openInventory(inv);
    }
}