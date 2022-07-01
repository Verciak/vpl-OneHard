package pl.vertty.nomenhc.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.GlassColor;
import pl.vertty.nomenhc.helpers.ItemBuilder;
import pl.vertty.nomenhc.objects.User;

import java.util.Arrays;

public class SchowekInventory {


    public static void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 9 * 6, ChatUtil.fixColor("&r&9&lMENU SCHOWEK"));
        User u = UserManager.get(player.getName());
        setLargeGui(inv);
        ItemBuilder kox = new ItemBuilder(Material.GOLDEN_APPLE, 1, (short) 1);
        kox.setTitle(ChatUtil.fixColor("&f&lKOXY: &9&l" + u.getKox()));
        kox.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &aKliknij, aby uzupelnic limit!"})));

        ItemBuilder ref = new ItemBuilder(Material.GOLDEN_APPLE, 1, (short) 0);
        ref.setTitle(ChatUtil.fixColor("&f&lREFILE: &9&l" + u.getRef()));
        ref.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &aKliknij, aby uzupelnic limit!"})));

        ItemBuilder pearl = new ItemBuilder(Material.ENDER_PEARL, 1, (short) 0);
        pearl.setTitle(ChatUtil.fixColor("&f&lPERLY: &9&l" + u.getPearl()));
        pearl.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &aKliknij, aby uzupelnic limit!"})));

        ItemBuilder snowball = new ItemBuilder(Material.SNOW_BALL, 1, (short) 0);
        snowball.setTitle(ChatUtil.fixColor("&f&lSNIEZKI: &9&l" + u.getSnowball()));
        snowball.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &aKliknij, aby uzupelnic limit!"})));

        ItemBuilder arrow = new ItemBuilder(Material.ARROW, 1, (short) 0);
        arrow.setTitle(ChatUtil.fixColor("&f&lSTRZALY: &9&l" + u.getArrow()));
        arrow.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &aKliknij, aby uzupelnic limit!"})));

        ItemBuilder all = new ItemBuilder(Material.HOPPER, 1, (short) 0);
        all.setTitle(ChatUtil.fixColor("&9&lDOBIERZ DO LIMITU"));
        all.addLores(Arrays.asList(ChatUtil.fixColor(new String[]{"&r", "&r&8>> &aKliknij, aby uzupelnic limit!"})));


        inv.setItem(20, kox.build());
        inv.setItem(24, ref.build());
        inv.setItem(31, pearl.build());
        inv.setItem(30, arrow.build());
        inv.setItem(32, snowball.build());
        inv.setItem(40, all.build());

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