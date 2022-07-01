package pl.vertty.nomenhc.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.ItemBuilder;

import java.util.ArrayList;

public class PItemsCommand extends PlayerCommand {

    public PItemsCommand() {
        super("pitems", "pitems", "/pitems", "cmd.admin.pitems", new String[] { "" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        Inventory inv = Bukkit.createInventory(p, 6*9, ChatUtil.fixColor("&9Itemy premium"));
        ArrayList<String> lore_pandora = new ArrayList<>();
        lore_pandora.add(ChatUtil.fixColor(""));
        lore_pandora.add(ChatUtil.fixColor("&8>> &7Postaw na ziemi i zdobadz magiczne itemy!"));
        ItemBuilder pandora = new ItemBuilder(Material.DRAGON_EGG, 64);
        pandora.setTitle(ChatUtil.fixColor("&9PANDORA"));
        pandora.addLores(ChatUtil.fColor(lore_pandora));

        ArrayList<String> lore_cx = new ArrayList<>();
        lore_cx.add(ChatUtil.fixColor(""));
        lore_cx.add(ChatUtil.fixColor("&8>> &7Postaw na ziemi i zdobadz itemy!"));
        ItemBuilder cx = new ItemBuilder(Material.MOSSY_COBBLESTONE, 64);
        cx.setTitle(ChatUtil.fixColor("&9CobbleX"));
        cx.addLores(ChatUtil.fColor(lore_cx));

        ItemBuilder casee = new ItemBuilder(Material.CHEST, 64);
        casee.setTitle(ChatUtil.fixColor("&9Magiczna Skrzynka"));
        casee.addLore(ChatUtil.fixColor(""));
        casee.addLore(ChatUtil.fixColor("&8>> &7Postaw na ziemi, aby otworzyc &9Magiczna Skrzynke!"));
        casee.addEnchantment(Enchantment.DURABILITY, 10);


        inv.setItem(0, pandora.build());
        inv.setItem(1, cx.build());
        inv.setItem(2, casee.build());
        p.openInventory(inv);
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }


}

