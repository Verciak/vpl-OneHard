package pl.vertty.nomenhc.handlers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import pl.vertty.nomenhc.objects.drop.CobbleXDrop;
import pl.vertty.nomenhc.objects.drop.Drop;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.vertty.nomenhc.objects.drop.PandoraDrop;

public class DropManager {
    public static JSONObject drop;

    private static final Set<UUID> noCobble = new HashSet<>();
    private static final Set<UUID> noMsg = new HashSet<>();
    public static ArrayList<Drop> drops = new ArrayList<>();
    public static ArrayList<PandoraDrop> drops_pandora = new ArrayList<>();
    public static ArrayList<CobbleXDrop> drops_cx = new ArrayList<>();
    public static ArrayList<PandoraDrop> drops_case = new ArrayList<>();


    public static Drop getDrop(ItemStack itemStack) {
        for (Drop d : drops) {
            if (d.getName() == itemStack.getType().getId())
                return d;
        }
        return null;
    }

    public static PandoraDrop getDropCase(ItemStack itemStack) {
        for (PandoraDrop d : drops_case) {
            if (d.getName() == itemStack.getType().getId())
                return d;
        }
        return null;
    }

    public static void loadDrops() {
        DropManager.drops.clear();
        DropManager.drops_pandora.clear();
        DropManager.drops_cx.clear();
        DropManager.drops_case.clear();
        drop = ConfigLoader.load("plugins/vpl-OneHard/drops.json");

        JSONArray drops_cx = drop.getJSONObject("drops").getJSONObject("cx").getJSONArray("list");
        for (int i = 0; i < drops_cx.length(); i++) {
            JSONObject o = drops_cx.getJSONObject(i);
            ItemStack ib = new ItemStack(o.getInt("id"), 1, (short) o.getInt("meta"));
            DropManager.drops_cx.add(new CobbleXDrop(
                    o.getInt("id"),
                    o.getString("name"),
                    ib,
                    o.getInt("amount")));
        }
        Bukkit.getLogger().info("Wczytano " + DropManager.drops_cx.size() + " dropow z CobbleX");

        JSONArray drops_case = drop.getJSONObject("drops").getJSONObject("case").getJSONArray("list");
        for (int i = 0; i < drops_case.length(); i++) {
            JSONObject o = drops_case.getJSONObject(i);
            JSONArray enchats = o.getJSONArray("enchant");
            ItemStack ib = new ItemStack(o.getInt("id"), 1, (short) o.getInt("meta"));
            for (int i3 = 0; i3 < enchats.length(); i3++) {
                ib.addUnsafeEnchantment(Enchantment.getById(enchats.getJSONObject(i3).getInt("name")), enchats.getJSONObject(i3).getInt("level"));
            }
            DropManager.drops_case.add(new PandoraDrop(
                    o.getInt("id"),
                    o.getDouble("chance"),
                    o.getString("name"),
                    ib,
                    o.getJSONObject("amount").getInt("max"),
                    o.getJSONObject("amount").getInt("min")));
        }
        Bukkit.getLogger().info("Wczytano " + DropManager.drops_case.size() + " dropow z Magicznej Skrzynki");


        JSONArray drops_pandora = drop.getJSONObject("drops").getJSONObject("pandora").getJSONArray("list");
        for (int i = 0; i < drops_pandora.length(); i++) {
            JSONObject o = drops_pandora.getJSONObject(i);
            JSONArray enchats = o.getJSONArray("enchant");
            ItemStack ib = new ItemStack(o.getInt("id"), 1, (short) o.getInt("meta"));
            for (int i3 = 0; i3 < enchats.length(); i3++) {
                ib.addUnsafeEnchantment(Enchantment.getById(enchats.getJSONObject(i3).getInt("name")), enchats.getJSONObject(i3).getInt("level"));
            }
                DropManager.drops_pandora.add(new PandoraDrop(
                    o.getInt("id"),
                    o.getDouble("chance"),
                    o.getString("name"),
                    ib,
                    o.getJSONObject("amount").getInt("max"),
                    o.getJSONObject("amount").getInt("min")));
        }
        Bukkit.getLogger().info("Wczytano " + DropManager.drops_pandora.size() + " dropow z pandory");


        JSONArray drops = drop.getJSONObject("drops").getJSONObject("stone").getJSONArray("list");
        for (int i = 0; i < drops.length(); i++) {
            JSONObject o = drops.getJSONObject(i);
            DropManager.drops.add(new Drop(
                    o.getInt("id"),
                    o.getDouble("chance"),
                    o.getString("name"),
                    new ItemStack(o.getInt("id")),
                    Material.getMaterial(o.getInt("from_id")),
                    o.getJSONObject("amount").getInt("max"),
                    o.getJSONObject("amount").getInt("min"),
                    o.getBoolean("fortune"),
                    o.getInt("exp")));
        }
        Bukkit.getLogger().info("Wczytano " + DropManager.drops.size() + " dropow z stone");

    }

    public static boolean isNoCobble(UUID uuid) {
        return noCobble.contains(uuid);
    }

    public static void changeNoCobble(UUID uuid) {
        if (noCobble.contains(uuid)) {
            noCobble.remove(uuid);
        } else {
            noCobble.add(uuid);
        }
    }

    public static boolean isNoMsg(UUID uuid) {
        return noMsg.contains(uuid);
    }

    public static void changeNoMsg(UUID uuid) {
        if (noMsg.contains(uuid)) {
            noMsg.remove(uuid);
        } else {
            noMsg.add(uuid);
        }
    }

}

