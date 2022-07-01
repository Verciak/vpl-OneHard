package pl.vertty.nomenhc.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.HopperMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.CombatManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.Combat;
import pl.vertty.nomenhc.objects.configs.Config;
import pl.vertty.nomenhc.objects.configs.Messages;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryOpenListener implements Listener {

    private final GuildPlugin plugin;

    public void register(){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void FrameRotate(PlayerInteractEntityEvent e) {
        final Player p = e.getPlayer();
        final Combat combat = CombatManager.getCombat(p);
        if (combat != null && combat.hasFight()) {
            Entity entity = e.getRightClicked();
            if(entity instanceof HopperMinecart) {
                if (Config.getInstance().HOPPER_MINECART_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().HOPPER_MINECART_MESSAGE);
                }
            }
        }
    }

    @EventHandler
    public void open(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        final Combat combat = CombatManager.getCombat(p);
        if (combat != null && combat.hasFight()) {
            if (e.getMaterial() == Material.ANVIL) {
                if (Config.getInstance().ANVIL_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().ANVIL_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.BEACON) {
                if (Config.getInstance().BEACON_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().BEACON_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.BREWING_STAND) {
                if (Config.getInstance().BREWING_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().BREWING_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.CHEST) {
                if (Config.getInstance().CHEST_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().CHEST_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.DISPENSER) {
                if (Config.getInstance().DISPENSER_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().DISPENSER_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.DROPPER) {
                if (Config.getInstance().DROPPER_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().DROPPER_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.ENCHANTMENT_TABLE) {
                if (Config.getInstance().ENCHANTING_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().ENCHANTING_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.ENDER_CHEST) {
                if (Config.getInstance().ENDER_CHEST_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().ENDER_CHEST_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.FURNACE) {
                if (Config.getInstance().FURNACE_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().FURNACE_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.HOPPER) {
                if (Config.getInstance().HOPPER_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().HOPPER_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.WORKBENCH) {
                if (Config.getInstance().WORKBENCH_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().WORKBENCH_MESSAGE);
                }
            }
            if (e.getMaterial() == Material.TRAPPED_CHEST) {
                if (Config.getInstance().TRAPPED_CHEST_STATUS) {
                    e.setCancelled(true);
                    ChatUtil.sendMessage(p, Messages.getInstance().TRAPPED_CHEST_MESSAGE);
                }
            }
        }
    }

}
