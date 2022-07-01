package pl.vertty.nomenhc.helpers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.vertty.nomenhc.menu.SchowekInventory;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.configs.Config;

public class SchowekHelper {

    public static void onKox(User u, Player p){
        if (u.getKox() <= 0) {
            p.closeInventory();
            SchowekInventory.open(p);
            p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz calego limitu!"));
            return;
        }
        if (u.getKox() > Config.getInstance().kox) {
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, Config.getInstance().kox, (short)1));
            u.setKox(u.getKox() - Config.getInstance().kox);
            p.closeInventory();
            SchowekInventory.open(p);
        } else {
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short)1));
            u.setKox(u.getKox() - 1);
            p.closeInventory();
            SchowekInventory.open(p);
        }
    }

    public static void onRef(User u, Player p){
        if (u.getRef() <= 0) {
            p.closeInventory();
            SchowekInventory.open(p);
            p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz calego limitu!"));
            return;
        }
        if (u.getRef() > Config.getInstance().ref) {
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, Config.getInstance().ref, (short)0));
            u.setRef(u.getRef() - Config.getInstance().ref);
            p.closeInventory();
            SchowekInventory.open(p);
        } else {
            p.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 1, (short)0));
            u.setRef(u.getRef() - 1);
            p.closeInventory();
            SchowekInventory.open(p);
        }
    }

    public static void onPearl(User u, Player p){
        if (u.getPearl() <= 0) {
            p.closeInventory();
            SchowekInventory.open(p);
            p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz calego limitu!"));
            return;
        }
        if (u.getPearl() > Config.getInstance().pearl) {
            p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, Config.getInstance().pearl, (short)0));
            u.setPearl(u.getPearl() - Config.getInstance().pearl);
            p.closeInventory();
            SchowekInventory.open(p);
        } else {
            p.getInventory().addItem(new ItemStack(Material.ENDER_PEARL, 1, (short)0));
            u.setPearl(u.getPearl() - 1);
            p.closeInventory();
            SchowekInventory.open(p);
        }
    }

    public static void onSnowball(User u, Player p){
        if (u.getSnowball() <= 0) {
            p.closeInventory();
            SchowekInventory.open(p);
            p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz calego limitu!"));
            return;
        }
        if (u.getSnowball() > Config.getInstance().snowball) {
            p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, Config.getInstance().snowball, (short)0));
            u.setSnowball(u.getSnowball() - Config.getInstance().snowball);
            p.closeInventory();
            SchowekInventory.open(p);
        } else {
            p.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 1, (short)0));
            u.setSnowball(u.getSnowball() - 1);
            p.closeInventory();
            SchowekInventory.open(p);
        }
    }

    public static void onArrow(User u, Player p){
        if (u.getArrow() <= 0) {
            p.closeInventory();
            SchowekInventory.open(p);
            p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz calego limitu!"));
            return;
        }
        if (u.getArrow() > Config.getInstance().arrow) {
            p.getInventory().addItem(new ItemStack(Material.ARROW, Config.getInstance().arrow, (short)0));
            u.setArrow(u.getArrow() - Config.getInstance().arrow);
            p.closeInventory();
            SchowekInventory.open(p);
        } else {
            p.getInventory().addItem(new ItemStack(Material.ARROW, 1, (short)0));
            u.setArrow(u.getArrow() - 1);
            p.closeInventory();
            SchowekInventory.open(p);
        }
    }

}
