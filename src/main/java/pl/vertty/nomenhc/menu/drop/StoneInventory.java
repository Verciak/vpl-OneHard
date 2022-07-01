package pl.vertty.nomenhc.menu.drop;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.GlassColor;
import pl.vertty.nomenhc.helpers.ItemBuilder;
import pl.vertty.nomenhc.objects.drop.Drop;

import java.text.DecimalFormat;
import java.util.Arrays;

public class StoneInventory {

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

        for (Drop drop : DropManager.drops) {

            ItemBuilder dropItem = new ItemBuilder(Material.getMaterial(drop.getName()), 1);
            dropItem.setTitle(ChatUtil.fixColor("&r&9&l" + drop.getMessage()));
            double chance = drop.getChance();
            if(player.hasPermission("sponsor")){
                chance += 1;
                dropItem.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Szansa: &9" + df.format(chance) + "% &8[&l&bSPONSOR &a+1.0%&r&8]",
                        "&r&8>> &7Doswiadczenie: &9" + drop.getExp(),
                        "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9" + drop.getMaxAmount(),
                        "&r&8>> &7Szczescie: &9" + (drop.isFortune() ? "&a✔" : "&c✖"),
                        "&r&8>> &7Status: " + (drop.isDisabled(player.getUniqueId()) ? "&c✖" : "&a✔")
                })));
            }else if(player.hasPermission("svip")){
                chance += 0.50;
                dropItem.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Szansa: &9" + df.format(chance) + "% &8[&l&6SVIP &a+0.5%&r&8]",
                        "&r&8>> &7Doswiadczenie: &9" + drop.getExp(),
                        "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9" + drop.getMaxAmount(),
                        "&r&8>> &7Szczescie: &9" + (drop.isFortune() ? "&a✔" : "&c✖"),
                        "&r&8>> &7Status: " + (drop.isDisabled(player.getUniqueId()) ? "&c✖" : "&a✔")
                })));
            }else if(player.hasPermission("vip")){
                chance += 0.25;
                dropItem.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Szansa: &9" + df.format(chance) + "% &8[&l&eVIP &a+0.25%&r&8]",
                        "&r&8>> &7Doswiadczenie: &9" + drop.getExp(),
                        "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9" + drop.getMaxAmount(),
                        "&r&8>> &7Szczescie: &9" + (drop.isFortune() ? "&a✔" : "&c✖"),
                        "&r&8>> &7Status: " + (drop.isDisabled(player.getUniqueId()) ? "&c✖" : "&a✔")
                })));
            }else {
                dropItem.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{
                        "&r&8>> &7Szansa: &9" + df.format(chance) + "%",
                        "&r&8>> &7Doswiadczenie: &9" + drop.getExp(),
                        "&r&8>> &7Ilosc: &9" + drop.getMinAmount() + "&8-&9" + drop.getMaxAmount(),
                        "&r&8>> &7Szczescie: &9" + (drop.isFortune() ? "&a✔" : "&c✖"),
                        "&r&8>> &7Status: " + (drop.isDisabled(player.getUniqueId()) ? "&c✖" : "&a✔")
                })));
            }
            if(!drop.isDisabled(player.getUniqueId())){
                dropItem.addEnchantment(Enchantment.DURABILITY, 10);

            }
            ItemStack item = dropItem.build();
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
            inv.addItem(item);
        }

        ItemBuilder cbl = new ItemBuilder(Material.COBBLESTONE);
        cbl.setTitle(ChatUtil.fixColor("&r&8&7Cobblestone &8(" + (DropManager.isNoCobble(player.getUniqueId()) ? "&c✖" : "&a✔") + "&8)"));
        if(!DropManager.isNoCobble(player.getUniqueId())){
            cbl.addEnchantment(Enchantment.DURABILITY, 10);
        }
        ItemStack item = cbl.build();
        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        inv.addItem(item);


        ItemBuilder msg = new ItemBuilder(Material.PAPER);
        msg.setTitle(ChatUtil.fixColor("&r&8&7Wiadomosci &8(" + (DropManager.isNoMsg(player.getUniqueId()) ? "&c✖" : "&a✔") + "&8)"));
        if(!DropManager.isNoMsg(player.getUniqueId())){
            msg.addEnchantment(Enchantment.DURABILITY, 10);
        }

        ItemBuilder on = new ItemBuilder(Material.INK_SACK, 1, DyeColor.LIME.getDyeData());
        on.setTitle(ChatUtil.fixColor("&r&8&aWlacz caly drop!"));
        ItemBuilder off = new ItemBuilder(Material.INK_SACK, 1, DyeColor.RED.getDyeData());
        off.setTitle(ChatUtil.fixColor("&r&8&cWylacz caly drop!"));

        ItemBuilder back = new ItemBuilder(Material.BARRIER, 1);
        back.setTitle(ChatUtil.fixColor("&r&4&lPOWROT"));
        back.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby wrocic!"})));
        inv.setItem(49, back.build());
        inv.setItem(37, on.build());
        inv.setItem(38, off.build());
        inv.setItem(42, msg.build());
        inv.setItem(43, item);
        inv.setItem(22, new ItemStack(Material.AIR));
        player.openInventory(inv);

    }
}
