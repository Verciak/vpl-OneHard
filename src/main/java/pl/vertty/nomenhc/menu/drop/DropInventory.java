package pl.vertty.nomenhc.menu.drop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.GlassColor;
import pl.vertty.nomenhc.helpers.ItemBuilder;

import java.util.Arrays;

public class DropInventory {


    public static void open(Player player){
        Inventory inv = Bukkit.createInventory(null, 9*6, ChatUtil.fixColor("&r&9&lMENU DROPU"));
        setLargeGui(inv);
        ItemBuilder stone = new ItemBuilder(Material.STONE, 1);
        stone.setTitle(ChatUtil.fixColor("&r&9&lDROP Z KAMIENIA"));
        stone.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby sprawdzic drop z kamienia!"})));

        ItemBuilder mossy = new ItemBuilder(Material.MOSSY_COBBLESTONE, 1);
        mossy.setTitle(ChatUtil.fixColor("&r&9&lDROP Z COBBLEX"));
        mossy.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby sprawdzic drop z CobbleX!"})));

        ItemBuilder magic_case = new ItemBuilder(Material.CHEST, 1);
        magic_case.setTitle(ChatUtil.fixColor("&r&9&lDROP Z MAGICZNEJ SKRZYNKI"));
        magic_case.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby sprawdzic drop z Magicznej Skrzynki!"})));

        ItemBuilder pandora = new ItemBuilder(Material.DRAGON_EGG, 1);
        pandora.setTitle(ChatUtil.fixColor("&r&9&lDROP Z PANDORY"));
        pandora.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &7Kliknij, aby sprawdzic drop z Pandory!"})));

        inv.setItem(20, pandora.build());
        inv.setItem(24, magic_case.build());
        inv.setItem(32, mossy.build());
        inv.setItem(30, stone.build());

        player.openInventory(inv);
    }

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

}
//
//    private final Player player;
//
//    public DropInventory(Player p) {
//        super(null, ChatUtil.fixColor("&r&9&lMENU DROPU"));
//        this.player = p;
//        refreshGui();
//        p.addWindow(this);
//    }
//
//    protected void onSlotChange(FakeSlotChangeEvent e) {
//        int slot = e.getAction().getSlot();
//        Player p = e.getPlayer();
//        e.setCancelled(true);
//        if(e.getAction().getSlot() == 30){
//            new StoneInventory(p);
//        }
//        if(e.getAction().getSlot() == 20){
//            new PandoraInventory(p);
//        }
//        if(e.getAction().getSlot() == 32){
//            new CobbleXInventory(p);
//        }
//        if(e.getAction().getSlot() == 24){
//            new MagicCaseInventory(p);
//        }
//    }
//
//}
