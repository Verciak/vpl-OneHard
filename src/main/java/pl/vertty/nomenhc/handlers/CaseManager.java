package pl.vertty.nomenhc.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.ItemBuilder;
import pl.vertty.nomenhc.helpers.RandomHelper;
import pl.vertty.nomenhc.objects.CaseInv;
import pl.vertty.nomenhc.objects.drop.PandoraDrop;


public class CaseManager {
    private static ConcurrentHashMap<Player, CaseInv> cases;
    public static ItemBuilder mcase;

    public static List<ItemStack> drop = new ArrayList<>();

    static {
        for(PandoraDrop xd : DropManager.drops_case){
            ItemStack xdd = xd.getWhat();
            int amount;
            if(xd.getMaxAmount() == 1){
                amount = 1;
            }else {
                amount = RandomHelper.getRandomInt(xd.getMinAmount(), xd.getMaxAmount());
            }
            xdd.setAmount(amount);
            drop.add(xdd);
        }
        cases = new ConcurrentHashMap<>();
        mcase = new ItemBuilder(Material.CHEST);
        mcase.setTitle(ChatUtil.fixColor("&9Magiczna Skrzynka"));
        mcase.addLore(ChatUtil.fixColor(""));
        mcase.addLore(ChatUtil.fixColor("&8>> &7Postaw na ziemi, aby otworzyc &9Magiczna Skrzynke!"));
        mcase.addEnchantment(Enchantment.DURABILITY, 10);    }

    public static boolean isInCase(Player p) {
        return cases.containsKey(p);
    }

    public static CaseInv getCase(Player p) {
        return cases.get(p);
    }

    public static void addCase(final Player p, final CaseInv inv) {
        if (isInCase(p))
            return;
        cases.put(p, inv);
        (new BukkitRunnable() {
            public void run() {
                if (!CaseManager.isInCase(p)) {
                    cancel();
                    return;
                }
                if (!p.getInventory().containsAtLeast(CaseManager.mcase.build(), 1)) {
                    cancel();
                    return;
                }
                if (inv.getRool() >= inv.getRoolMax()) {
                    ItemStack win = inv.getInv().getItem(13);
                    if (win.getType() == Material.BEACON && RandomHelper.getChance(90.0D)) {
                        inv.setRool(inv.getRool() - 1);
                        return;
                    }
                    PandoraDrop drop = DropManager.getDropCase(win);

                    cancel();
                    p.getInventory().removeItem(CaseManager.mcase.build());
                    Bukkit.broadcastMessage(ChatUtil.fixColor("&8>> &7Gracz &9" + p.getName() + " &7otworzyl &9Magiczna Skrzynka &7i wylosowal &9" + drop.getMessage() + ((win.getAmount() > 1) ? (" &fx&f" + win.getAmount()) : "")));
                    ItemStack xdd = new ItemStack(win.getType(), win.getAmount(), win.getDurability());
                    if(win.getEnchantments() != null){
                        xdd.addEnchantments(win.getEnchantments());
                    }
                    ChatUtil.giveItems(p, xdd);
                    p.updateInventory();
                    CaseManager.removeCase(p);
                    CaseManager.firewark(p.getLocation());
                    (new BukkitRunnable() {
                        public void run() {
                            if (p.getOpenInventory().getTopInventory().getName().equalsIgnoreCase(ChatUtil.fixColor("&9Losowanie przedmiotu...")))
                                p.closeInventory();
                        }
                    }).runTaskLater(GuildPlugin.getPlugin(), 30L);
                    return;
                }
                inv.setRool(inv.getRool() + 1);
                if (!p.getOpenInventory().getTopInventory().getName().equalsIgnoreCase(ChatUtil.fixColor("&9Losowanie przedmiotu...")))
                    p.openInventory(inv.getInv());
                p.playSound(p.getLocation(), Sound.CLICK, 10.0F, 10.0F);
                ItemStack i = CaseManager.drop.get(RandomHelper.getRandomInt(0, CaseManager.drop.size() - 1));
                ItemStack i1 = inv.getInv().getItem(10);
                ItemStack i2 = inv.getInv().getItem(11);
                ItemStack i3 = inv.getInv().getItem(12);
                ItemStack i4 = inv.getInv().getItem(13);
                ItemStack i5 = inv.getInv().getItem(14);
                ItemStack i6 = inv.getInv().getItem(15);
                ItemStack i7 = inv.getInv().getItem(16);
                ItemStack i8 = inv.getInv().getItem(17);
                inv.getInv().setItem(9, i1);
                inv.getInv().setItem(10, i2);
                inv.getInv().setItem(11, i3);
                inv.getInv().setItem(12, i4);
                inv.getInv().setItem(13, i5);
                inv.getInv().setItem(14, i6);
                inv.getInv().setItem(15, i7);
                inv.getInv().setItem(16, i8);
                inv.getInv().setItem(17, i);
            }
        }).runTaskTimer((Plugin)GuildPlugin.getPlugin(), 3L, 3L);
    }

    public static void removeCase(Player p) {
        if (!isInCase(p))
            return;
        cases.remove(p);
    }

    public static void firewark(Location loc) {
        Firework fw = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        Random r = new Random();
        int rt = r.nextInt(4) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1)
            type = FireworkEffect.Type.BALL;
        if (rt == 2)
            type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3)
            type = FireworkEffect.Type.BURST;
        if (rt == 4)
            type = FireworkEffect.Type.CREEPER;
        if (rt == 5)
            type = FireworkEffect.Type.STAR;
        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);
        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
        fwm.addEffect(effect);
        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);
        fw.setFireworkMeta(fwm);
    }

    private static Color getColor(int i) {
        Color c = null;
        if (i == 1)
            c = Color.AQUA;
        if (i == 2)
            c = Color.BLACK;
        if (i == 3)
            c = Color.BLUE;
        if (i == 4)
            c = Color.FUCHSIA;
        if (i == 5)
            c = Color.GRAY;
        if (i == 6)
            c = Color.GREEN;
        if (i == 7)
            c = Color.LIME;
        if (i == 8)
            c = Color.MAROON;
        if (i == 9)
            c = Color.NAVY;
        if (i == 10)
            c = Color.OLIVE;
        if (i == 11)
            c = Color.ORANGE;
        if (i == 12)
            c = Color.PURPLE;
        if (i == 13)
            c = Color.RED;
        if (i == 14)
            c = Color.SILVER;
        if (i == 15)
            c = Color.TEAL;
        if (i == 16)
            c = Color.WHITE;
        if (i == 17)
            c = Color.YELLOW;
        return c;
    }

    public static ConcurrentHashMap<Player, CaseInv> getCases() {
        return cases;
    }
}

