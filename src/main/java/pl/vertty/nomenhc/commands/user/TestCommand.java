package pl.vertty.nomenhc.commands.user;


import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.help.HelpTopic;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ChatUtil;
import pl.vertty.nomenhc.objects.User;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getServer;

public class TestCommand extends pl.vertty.nomenhc.commands.PlayerCommand {

    public static List<String> tops = new ArrayList<>();
    public TestCommand() {
        super("test", "Komenda test", "test", "", new String[] { "drop" });
    }

    @Override
    public boolean onCommand(final Player p, final String[] args) {
        if(p.getInventory().getItemInHand().getType().getId() == 0){
            p.sendMessage("§cDEBIL NIE MASZ NIC W RECE");
            return false;
        }
        p.sendMessage("§8========================");
        p.sendMessage("§7ID ITEMU: §9" + p.getInventory().getItemInHand().getType().getId());
        p.sendMessage("§7META ITEMU: §9" + p.getInventory().getItemInHand().getData());
        p.sendMessage("§8========================");
        return false;
    }

    @Override
    public boolean onCommand(ConsoleCommandSender p, String[] args) {
        return false;
    }


    public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortValue(LinkedHashMap<K, V> map) {
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

}
