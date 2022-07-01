package pl.vertty.nomenhc.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.SchowekHelper;
import pl.vertty.nomenhc.menu.SchowekInventory;
import pl.vertty.nomenhc.menu.drop.*;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.drop.Drop;

@AllArgsConstructor
public class InventoryClickListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String inv = e.getInventory().getTitle();
        User u = UserManager.get(p.getName());
        if(e.getCurrentItem() == null){
            return;
        }
        ItemMeta itemMeta = e.getCurrentItem().getItemMeta();
        if(itemMeta == null){
            return;
        }
        String item = itemMeta.getDisplayName();
        if(inv.contains(ChatUtil.fixColor("&9Losowanie przedmiotu..."))){
            e.setCancelled(true);
        }
        if (inv.contains(ChatUtil.fixColor("&r&9&lMENU SCHOWEK"))) {
            e.setCancelled(true);
            if(item.contains(ChatUtil.fixColor("&f&lKOXY: &9&l"))){
                if (u.getKox() <= 0) {
                    p.closeInventory();
                    SchowekInventory.open(p);
                    p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz koxow do wyplaty!"));
                    e.setCancelled(true);
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
            if(item.contains(ChatUtil.fixColor("&f&lREFILE: &9&l"))){
                if (u.getRef() <= 0) {
                    p.closeInventory();
                    SchowekInventory.open(p);
                    p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz refili do wyplaty!"));
                    e.setCancelled(true);
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
            if(item.contains(ChatUtil.fixColor("&f&lPERLY: &9&l"))){
                if (u.getPearl() <= 0) {
                    p.closeInventory();
                    SchowekInventory.open(p);
                    p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz perel do wyplaty!"));
                    e.setCancelled(true);
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
            if(item.contains(ChatUtil.fixColor("&f&lSNIEZKI: &9&l"))){
                if (u.getSnowball() <= 0) {
                    p.closeInventory();
                    SchowekInventory.open(p);
                    p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz sniezek do wyplaty!"));
                    e.setCancelled(true);
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
            if(item.contains(ChatUtil.fixColor("&f&lSTRZALY: &9&l"))){
                if (u.getArrow() <= 0) {
                    p.closeInventory();
                    SchowekInventory.open(p);
                    p.sendMessage(ChatUtil.fixColor("&4&lBLAD: &7Nie posiadasz strzal do wyplaty!"));
                    e.setCancelled(true);
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
            if(item.contains(ChatUtil.fixColor("&9&lDOBIERZ DO LIMITU"))){
                SchowekHelper.onKox(u,p);
                SchowekHelper.onRef(u,p);
                SchowekHelper.onPearl(u,p);
                SchowekHelper.onSnowball(u,p);
                SchowekHelper.onArrow(u,p);

            }
        }
        if (inv.contains(ChatUtil.fixColor("&r&9&lMENU DROPU"))) {
            e.setCancelled(true);
            if(item.contains(ChatUtil.fixColor("&r&9&lDROP Z COBBLEX"))){
                CobbleXInventory.open(p);
            }
            if(item.contains(ChatUtil.fixColor("&r&9&lDROP Z MAGICZNEJ SKRZYNKI"))){
                MagicCaseInventory.open(p);
            }
            if (item.contains(ChatUtil.fixColor("&r&9&lDROP Z KAMIENIA"))) {
                StoneInventory.open(p);
            }
            if(item.contains(ChatUtil.fixColor("&r&9&lDROP Z PANDORY"))){
                PandoraInventory.open(p);
            }
            if (item.contains(ChatUtil.fixColor("&r&4&lPOWROT"))) {
                DropInventory.open(p);
            }
            if (DropManager.getDrop(e.getCurrentItem()) != null) {
                DropManager.getDrop(e.getCurrentItem()).changeStatus(p.getUniqueId());
                StoneInventory.open(p);
            }
            if (item.startsWith(ChatUtil.fixColor("&r&8&7Cobblestone"))) {
                DropManager.changeNoCobble(p.getUniqueId());
                StoneInventory.open(p);
                return;
            }
            if (item.startsWith(ChatUtil.fixColor("&r&8&7Wiadomosci"))) {
                DropManager.changeNoMsg(p.getUniqueId());
                StoneInventory.open(p);
                return;
            }
            if (item.startsWith(ChatUtil.fixColor("&r&8&aWlacz caly drop!"))) {
                for (Drop drop : DropManager.drops) {
                    drop.setStatus(p.getUniqueId(), true);
                    StoneInventory.open(p);
                }
            }
            if (item.startsWith(ChatUtil.fixColor("&r&8&cWylacz caly drop!"))) {
                for (Drop drop : DropManager.drops) {
                    drop.setStatus(p.getUniqueId(), false);
                    StoneInventory.open(p);
                }
            }
        }
    }

}
