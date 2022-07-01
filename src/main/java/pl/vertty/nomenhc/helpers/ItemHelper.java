package pl.vertty.nomenhc.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemHelper {

    public static int getAmount(Material material, Player p, short durability) {
        int amount = 0;
        ItemStack[] contents;
        for (int length = (contents = p.getInventory().getContents()).length, i = 0; i < length; i++) {
            ItemStack itemStack = contents[i];
            if (itemStack != null && itemStack.getType() == material && itemStack.getDurability() == durability)
                amount += itemStack.getAmount();
        }
        return amount;
    }

    public static void addItemsToPlayer(Player player, List<ItemStack> items) {
        PlayerInventory inv = player.getInventory();
        HashMap<Integer, ItemStack> notStored = inv.addItem(items.toArray(new ItemStack[items.size()]));
        for (Map.Entry<Integer, ItemStack> en : notStored.entrySet())
            player.getWorld().dropItemNaturally(player.getLocation(), en.getValue());
    }

    public static void removeItems(Material material, Player p, short durability, int amount) {
        ItemStack[] contents;
        for (int length = (contents = p.getInventory().getContents()).length, i = 0; i < length; i++) {
            ItemStack itemStack = contents[i];
            if (amount > 0 && itemStack != null && itemStack.getType() == material && itemStack.getDurability() == durability)
                if (itemStack.getAmount() > amount) {
                    itemStack.setAmount(itemStack.getAmount() - amount);
                    amount = 0;
                } else {
                    amount -= itemStack.getAmount();
                    p.getInventory().removeItem(itemStack);
                }
        }
    }

}
