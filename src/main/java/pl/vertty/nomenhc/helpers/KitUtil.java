package pl.vertty.nomenhc.helpers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.ConfigLoader;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.objects.GuiItem;
import pl.vertty.nomenhc.objects.Kit;
import pl.vertty.nomenhc.objects.User;

import java.util.ArrayList;

public class KitUtil implements Listener {
    public static ArrayList<Kit> kits = new ArrayList<>();
    public static JSONObject object;

    GuildPlugin plugin;

    public KitUtil(GuildPlugin plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        object = ConfigLoader.load("plugins/vpl-OneHard/kits.json");

        JSONArray kitsJson = object.getJSONArray("kits");
        for (int i = 0; i < kitsJson.length(); i++) {
            JSONObject kitsJsonObj = kitsJson.getJSONObject(i);
            JSONObject item = kitsJsonObj.getJSONObject("gui_item");
            JSONObject item1 = item.getJSONObject("item");
            ItemBuilder ib = new ItemBuilder(Material.getMaterial(item1.getInt("item_id")), 1, (byte)item1.getInt("item_meta_id"));
            JSONArray kitGuiJsonEnch = item.getJSONArray("enchants");
            for (int i1 = 0; i1 < kitGuiJsonEnch.length(); i1++)
                ib.addEnchantment(Enchantment.getByName(kitGuiJsonEnch.getJSONObject(i1).getString("name")), kitGuiJsonEnch.getJSONObject(i1).getInt("level"));
            ib.setTitle(ChatUtil.fixColor(item.getString("name")));
            ib.addLores(JSONConverter.getString(item.getJSONArray("lore")));
            ArrayList<ItemBuilder> ibs = new ArrayList<>();
            JSONArray kitsItems = kitsJsonObj.getJSONArray("items");
            for (int i3 = 0; i3 < kitsItems.length(); i3++) {
                JSONObject obj = kitsItems.getJSONObject(i3);
                JSONObject obj_item = obj.getJSONObject("item");
                ItemBuilder is = new ItemBuilder(Material.getMaterial(obj_item.getInt("item_id")), obj_item.getInt("amount"), (byte)obj_item.getInt("item_meta_id"));
                is.setTitle(ChatUtil.fixColor(obj.getString("name")));
                is.addLores(JSONConverter.getString(obj.getJSONArray("lore")));
                JSONArray enchant = obj.getJSONArray("enchants");
                for (int j = 0; j < enchant.length(); j++)
                    is.addEnchantment(Enchantment.getByName(enchant.getJSONObject(j).getString("name")), enchant.getJSONObject(j).getInt("level"));
                ibs.add(is);
            }
            kits.add(new Kit(kitsJsonObj
                    .getString("name"), kitsJsonObj
                    .getString("perm"),
                    kitsJsonObj.getInt("time") * 1000,kitsJsonObj.getInt("slot"), new GuiItem(
                    0, ib, JSONConverter.getString(item.getJSONArray("lore"))), ibs));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (event.getInventory().getName() != null && ChatColor.stripColor(event.getInventory().getName()).contains("MENU KITOW")) {
            event.setResult(Event.Result.DENY);
            event.setCancelled(true);
            User u = UserManager.get(event.getWhoClicked().getName());
            ((Player)event.getWhoClicked()).updateInventory();
            if (event.getCurrentItem() == null)
                return;
            if (event.getCurrentItem().getType() == null)
                return;
            for (Kit k : kits) {
                if (k.getGui_item().getItemBuilder().build().equals(event.getCurrentItem())) {
                    if (!event.getWhoClicked().hasPermission(k.getPerm()) && !event.getWhoClicked().hasPermission("dkcode.kit.*"))
                        return;
                    ((Player)event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
                    event.getWhoClicked().closeInventory();
                    openKit((Player)event.getWhoClicked(), k);
                    return;
                }
            }
        }
        if (event.getInventory().getName() != null)
            for (Kit k : kits) {
                if (ChatColor.stripColor(event.getInventory().getName()).equals(k.getName())) {
                    event.setResult(Event.Result.DENY);
                    event.setCancelled(true);
                    User u = UserManager.get(event.getWhoClicked().getName());
                    ((Player)event.getWhoClicked()).updateInventory();
                    if (event.getCurrentItem() == null)
                        return;
                    if (event.getCurrentItem().getType() == null)
                        return;
                    if (event.getCurrentItem().getType().equals(Material.INK_SACK)) {
                        for (KitTimes kt : u.getKits()) {
                            if (kt.getName().equals(k.getName()) && Long.parseLong(kt.getNext()) >= System.currentTimeMillis()) {
                                ((Player)event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.CHEST_CLOSE, 1.0F, 1.0F);
                                ChatUtil.sendMessage(event.getWhoClicked(), "&4Blad: &6Kit mozesz odebrac za: &7" + DataUtil.secondsToString(Long.parseLong(kt.getNext())));
                                return;
                            }
                        }
                        giveKit((Player)event.getWhoClicked(), u, k);
                        ((Player)event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
                        event.getWhoClicked().closeInventory();
                        return;
                    }
                    if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                        ((Player)event.getWhoClicked()).playSound(event.getWhoClicked().getLocation(), Sound.SUCCESSFUL_HIT, 1.0F, 1.0F);
                        event.getWhoClicked().closeInventory();
                        open((Player)event.getWhoClicked());
                        return;
                    }
                    return;
                }
            }
    }

    private void giveKit(Player p, User u, Kit k) {
        u.getKits().add(new KitTimes(k.getName(), String.valueOf(System.currentTimeMillis() + k.getTime())));
        u.synchronize();
        for (ItemBuilder mt : k.getItems()) {
            p.getInventory().addItem(mt.build());
        }
    }

    public static void openKit(Player p, Kit k) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatUtil.fixColor("&b" + k.getName()));
        setLargeGui(inv);
        for (ItemBuilder i : k.getItems()) {
            inv.addItem(i.build());
        }
        inv.setItem(47, (new ItemBuilder(Material.BARRIER, 1)).setTitle("&CWyjdz").build());
        inv.setItem(51, (new ItemBuilder(Material.INK_SACK, 1, (byte)10)).setTitle("&aOdbierz Kita").build());
        p.openInventory(inv);
    }

    public static void open(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9*6, ChatUtil.fixColor("&9MENU KITOW"));
        setLargeGui(inv);
        for (Kit kit : kits) {
            ItemBuilder it = kit.getGui_item().getItemBuilder();
            ArrayList<String> lore = new ArrayList<>();
            it.removeLores();
            for (String s : kit.getGui_item().getLore())
                lore.add(s
                        .replace("{STATUS}", !can(p, kit) ? "&cBrak pozwolen!" : (!canTime(p, kit) ? "&cKit niedostepny czasowo!" : "&2Odbierz zestaw!"))
                        .replace("{TIME}", !can(p, kit) ? "&4NIGDY" : (!canTime(p, kit) ? ("&6" + DataUtil.secondsToString(getTime(p, kit))) : "&aTERAZ")));
            inv.setItem(kit.getSlot(), it.addLores(lore).build());
        }
        p.openInventory(inv);
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

    private static long getTime(Player p, Kit kit) {
        User u = UserManager.get(p.getName());
        for (KitTimes kt : u.getKits()) {
            if (kt.getName().equals(kit.getName()) && Long.parseLong(kt.getNext()) >= System.currentTimeMillis())
                return Long.parseLong(kt.getNext());
        }
        return System.currentTimeMillis();
    }

    private static boolean canTime(Player p, Kit kit) {
        User u = UserManager.get(p.getName());
        for (KitTimes kt : u.getKits()) {
            if (kt.getName().equals(kit.getName()) && Long.parseLong(kt.getNext()) >= System.currentTimeMillis())
                return false;
        }
        return true;
    }

    private static boolean can(Player p, Kit kit) {
        User u = UserManager.get(p.getName());
        return (p.hasPermission(kit.getPerm()) || p.hasPermission("dkcode.kit.*"));
    }
}

