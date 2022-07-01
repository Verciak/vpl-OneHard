package pl.vertty.nomenhc.commands.admin;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.vertty.nomenhc.commands.PlayerCommand;
import pl.vertty.nomenhc.handlers.DropManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.helpers.ItemBuilder;
import pl.vertty.nomenhc.objects.drop.Drop;

public class CaseCommand extends PlayerCommand {

    public CaseCommand() {
        super("case", "case", "/case <case> <all/gracz> ilosc", "cmd.admin.case", new String[] { "case" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        Player o;
        if (args.length < 3)
            return ChatUtil.sendMessage(p, "/case <case> <all/gracz> ilosc");
        if (!ChatUtil.isInteger(args[2]))
            return ChatUtil.sendMessage(p, "&4Blad: &2To nie jest liczba!");
        int size = Integer.parseInt(args[2]);
        String str;
        switch ((str = args[0]).hashCode()) {
            case 3046192:
                if (!str.equals("case"))
                    break;
                if (args[1].equalsIgnoreCase("all")) {
                    for (Player all : Bukkit.getOnlinePlayers())
                        giveCase(all, size);
                    return ChatUtil.sendMessage(p, "&8>> &7Caly serwer otrzymal &9Magiczna Skrzynke&7!");
                }
                o = Bukkit.getPlayer(args[1]);
                if (!o.isOnline())
                    return ChatUtil.sendMessage(p, "&4Blad: &2Gracz jest offline!");
                giveCase(o, size);
                return ChatUtil.sendMessage(p, "&8>> &7Dales graczowi &f" + o.getName() + " &9Magiczna Skrzynke &fx&f" + size);
        }
        return ChatUtil.sendMessage(p, "/case <case> <all/gracz> ilosc");
    }


    private void giveCase(Player p, int size) {
        ItemBuilder i = new ItemBuilder(Material.CHEST, size);
        i.setTitle(ChatUtil.fixColor("&9Magiczna Skrzynka"));
        i.addLore(ChatUtil.fixColor(""));
        i.addLore(ChatUtil.fixColor("&8>> &7Postaw na ziemi, aby otworzyc &9Magiczna Skrzynke!"));
        i.addEnchantment(Enchantment.DURABILITY, 10);
        ChatUtil.giveItems(p, i.build());
        p.updateInventory();
        ChatUtil.sendMessage(p, "&8>> &7Otrzymales &9Magiczna Szkrzynke &fx&f" + size);
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p0, String[] p1) {
        return false;
    }


}

