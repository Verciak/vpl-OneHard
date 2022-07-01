package pl.vertty.nomenhc.listeners;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.ProtocolTabAPI;
import pl.vertty.nomenhc.helpers.Ticking;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.manager.ProtocolTab;
import pl.vertty.nomenhc.objects.Guild;
import pl.vertty.nomenhc.objects.User;
import pl.vertty.nomenhc.packet.PacketToprankCommand;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PlayerTabJoinListener implements Listener {
    public static List<String> tops = new ArrayList<>();
    public static String[] x = new String[12];
    public static PacketToprankCommand pa;


    private final ThreadLocal<SimpleDateFormat> dateFormat = ThreadLocal.withInitial(
            () -> new SimpleDateFormat("HH:mm:ss"));

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (int i = 0; i < 80; i++) {
            ProtocolTabAPI.getTablist(player).setSlot(i, ProtocolTab.BLANK_TEXT);
        }

        ProtocolTabAPI.getTablist(player).setHeader("   &9&lOneHard.pl\n      &7Serwer EasyHC");

        ProtocolTabAPI.getTablist(player).setSlot(0, "");
        ProtocolTabAPI.getTablist(player).setSlot(1, "    &8» &9&lTOPOWI GRACZE &8«");
        ProtocolTabAPI.getTablist(player).setSlot(2, "");
        ProtocolTabAPI.getTablist(player).setSlot(21, "    &8» &9&lTOPOWE GILDIE &8«");
        ProtocolTabAPI.getTablist(player).setSlot(22, "");
        ProtocolTabAPI.getTablist(player).setSlot(39, "");
        ProtocolTabAPI.getTablist(player).setSlot(40, "");
        ProtocolTabAPI.getTablist(player).setSlot(41, "     &8» &9&lINFORMACJE &8«");
        ProtocolTabAPI.getTablist(player).setSlot(42, "");
        ProtocolTabAPI.getTablist(player).setSlot(44, "&8» &7Dzien edycji: &91");
        ProtocolTabAPI.getTablist(player).setSlot(47, "");
        ProtocolTabAPI.getTablist(player).setSlot(50, "        &8» &9&lKOMENDY &8«");
        ProtocolTabAPI.getTablist(player).setSlot(52, "&8» &9/pomoc");
        ProtocolTabAPI.getTablist(player).setSlot(53, "&8» &9/vip");
        ProtocolTabAPI.getTablist(player).setSlot(54, "&8» &9/svip");
        ProtocolTabAPI.getTablist(player).setSlot(55, "&8» &9/sponsor");
        ProtocolTabAPI.getTablist(player).setSlot(56, "&8» &9/drop");
        ProtocolTabAPI.getTablist(player).setSlot(57, "&8» &9/gildie");
        ProtocolTabAPI.getTablist(player).setSlot(58, "&8» &9/craftingi");


        ProtocolTabAPI.getTablist(player).setSlot(61, "      &8» &9&lTWOJ PROFIL &8«");
        ProtocolTabAPI.getTablist(player).setSlot(63, "&8» &7Twoj nick: &9"+player.getName());

        ProtocolTabAPI.getTablist(player).setSlot(68, "      &8» &9&lTWOJA GILDIA &8«");


        ProtocolTabAPI.getTablist(player).update();
        runTask(player);
    }


    private void runTask(Player player) {
        GuildPlugin.getPlugin().getServer().getScheduler().runTaskTimerAsynchronously(GuildPlugin.getPlugin(), () -> {
            if (player == null) {
                // tutaj jak gracz jest nullem, wymaga gracza online
                // polecam zostawic samo return to nie bedzie zadnych problemow
                return;
            }
            for (User u : UserManager.getAccounts()){
                tops.clear();
                tops.add(u.getName() + "|&|" + u.getPoints());
            }
            pa = new PacketToprankCommand();
            pa.sender = player.getName();
            pa.points = new ArrayList<String>();
            pa.guild = new ArrayList<String>();
            for (User u : UserManager.getAccounts()){
                pa.points.add(u.getName() + "|&|" + u.getPoints());
            }
            for(Guild g : GuildHandler.getGuilds()){
                pa.guild.add(g.getTag() + "|&|" + g.getPoints());
            }

            ProtocolTabAPI.getTablist(player).setSlot(3, sortPoints()[1]);
            ProtocolTabAPI.getTablist(player).setSlot(4, sortPoints()[2]);
            ProtocolTabAPI.getTablist(player).setSlot(5, sortPoints()[3]);
            ProtocolTabAPI.getTablist(player).setSlot(6, sortPoints()[4]);
            ProtocolTabAPI.getTablist(player).setSlot(7, sortPoints()[5]);
            ProtocolTabAPI.getTablist(player).setSlot(8, sortPoints()[6]);
            ProtocolTabAPI.getTablist(player).setSlot(9, sortPoints()[7]);
            ProtocolTabAPI.getTablist(player).setSlot(10, sortPoints()[8]);
            ProtocolTabAPI.getTablist(player).setSlot(11, sortPoints()[9]);
            ProtocolTabAPI.getTablist(player).setSlot(12, sortPoints()[10]);
            ProtocolTabAPI.getTablist(player).setSlot(13, sortPoints()[11]);
            ProtocolTabAPI.getTablist(player).setSlot(14, sortPoints()[12]);
            ProtocolTabAPI.getTablist(player).setSlot(15, sortPoints()[13]);
            ProtocolTabAPI.getTablist(player).setSlot(16, sortPoints()[14]);
            ProtocolTabAPI.getTablist(player).setSlot(17, sortPoints()[15]);
            ProtocolTabAPI.getTablist(player).setSlot(18, sortPoints()[16]);

            ProtocolTabAPI.getTablist(player).setSlot(23, sortGuilds()[1]);
            ProtocolTabAPI.getTablist(player).setSlot(24, sortGuilds()[2]);
            ProtocolTabAPI.getTablist(player).setSlot(25, sortGuilds()[3]);
            ProtocolTabAPI.getTablist(player).setSlot(26, sortGuilds()[4]);
            ProtocolTabAPI.getTablist(player).setSlot(27, sortGuilds()[5]);
            ProtocolTabAPI.getTablist(player).setSlot(28, sortGuilds()[6]);
            ProtocolTabAPI.getTablist(player).setSlot(29, sortGuilds()[7]);
            ProtocolTabAPI.getTablist(player).setSlot(30, sortGuilds()[8]);
            ProtocolTabAPI.getTablist(player).setSlot(31, sortGuilds()[9]);
            ProtocolTabAPI.getTablist(player).setSlot(32, sortGuilds()[10]);
            ProtocolTabAPI.getTablist(player).setSlot(33, sortGuilds()[11]);
            ProtocolTabAPI.getTablist(player).setSlot(34, sortGuilds()[12]);
            ProtocolTabAPI.getTablist(player).setSlot(35, sortGuilds()[13]);
            ProtocolTabAPI.getTablist(player).setSlot(36, sortGuilds()[14]);
            ProtocolTabAPI.getTablist(player).setSlot(37, sortGuilds()[15]);
            ProtocolTabAPI.getTablist(player).setSlot(38, sortGuilds()[16]);
            int ping = ((CraftPlayer)player).getHandle().ping;
            ProtocolTabAPI.getTablist(player).setSlot(45, "&8» &7TPS: &9" + Ticking.getTPS());
            ProtocolTabAPI.getTablist(player).setSlot(46, "&8» &7Ping: &9" + ping);
            ProtocolTabAPI.getTablist(player).setSlot(48, "&8» &7Graczy online: &9"+ Bukkit.getOnlinePlayers().size()+"&8/&91000");
            User ua = UserManager.get(player.getName());
            ProtocolTabAPI.getTablist(player).setSlot(64, "&8» &7Punkty: &9"+ua.getPoints());
            ProtocolTabAPI.getTablist(player).setSlot(65, "&8» &7Zabojstwa: &9"+ua.getKills());
            ProtocolTabAPI.getTablist(player).setSlot(66, "&8» &7Smierci: &9"+ua.getDeaths());

            Guild guild = GuildHandler.get(player.getName());
            if(guild != null){
                ProtocolTabAPI.getTablist(player).setSlot(70, "&8» &7TAG: &9"+guild.getTag());
                ProtocolTabAPI.getTablist(player).setSlot(71, "&8» &7Nazwa: &9"+guild.getName());
                ProtocolTabAPI.getTablist(player).setSlot(72, "&8» &7Lider: &9"+guild.getOwner());
                ProtocolTabAPI.getTablist(player).setSlot(73, "&8» &7Punkty: &9"+guild.getPoints());
                ProtocolTabAPI.getTablist(player).setSlot(74, "&8» &7Zabojstwa: &9"+guild.getKills());
                ProtocolTabAPI.getTablist(player).setSlot(75, "&8» &7Smierci: &9"+guild.getKills());
                ProtocolTabAPI.getTablist(player).setSlot(76, "&8» &7Zycia: &9"+guild.getLives());
                ProtocolTabAPI.getTablist(player).setSlot(77, "&8» &7Czlonkowie: &9"+guild.getMembers().size());
                ProtocolTabAPI.getTablist(player).setSlot(78, "&8» &7Sojusze: &9"+guild.getAllies().size());
            } else {
                ProtocolTabAPI.getTablist(player).setSlot(70, "&8» &cNIE POSIADASZ GILDII");
                ProtocolTabAPI.getTablist(player).setSlot(71, "");
                ProtocolTabAPI.getTablist(player).setSlot(72, "");
                ProtocolTabAPI.getTablist(player).setSlot(73, "");
                ProtocolTabAPI.getTablist(player).setSlot(74, "");
                ProtocolTabAPI.getTablist(player).setSlot(75, "");
                ProtocolTabAPI.getTablist(player).setSlot(76, "");
                ProtocolTabAPI.getTablist(player).setSlot(77, "");
                ProtocolTabAPI.getTablist(player).setSlot(78, "");

            }


            ProtocolTabAPI.getTablist(player).setFooter("&7WWW: &9ONEHARD.PL &8| &7DISCORD: &9DC.ONEHARD.PL &8| &7FB: &9FB.COM/ONEHARDPL");
            ProtocolTabAPI.getTablist(player).setSlot(43, "&8» &7Godzina: &9" + dateFormat.get().format(
                    new Date(System.currentTimeMillis())));
            ProtocolTabAPI.getTablist(player).update();
            //refresh co sekunde
        }, 20L, 20L);
    }
    public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortValue(LinkedHashMap<K, V> map) {
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private String[] sortPoints() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

        for (final String s : pa.points) {
            if(s != null){
                final String[] split = s.split(Pattern.quote("|&|"));
                map.put(split[0], Integer.valueOf(split[1]));
            }
        }
        LinkedHashMap<String, Integer> linkedHashMap = sortValue(map);
        final String[] x = new String[20];

        int length = x.length;
        for (int i = 0; i < length; i++) {
            x[i] = i > 0 && i <= length - 2 ? "&7"+ i +". &fBrak" : "";
        }

        int b = 1;
        for (final Map.Entry<String, Integer> s : linkedHashMap.entrySet()) {
            if(b == 1){
                x[b] = "&7" + b + ". &c" + s.getKey() + " &8(&9" + s.getValue() + "&8)";
            }else if (b == 2){
                x[b] = "&7" + b + ". &6" + s.getKey() + " &8(&9" + s.getValue() + "&8)";
            }
            else if (b == 3){
                x[b] = "&7" + b + ". &e" + s.getKey() + " &8(&9" + s.getValue() + "&8)";
            }else {
                x[b] = "&7" + b + ". &f" + s.getKey() + " &8(&9" + s.getValue() + "&8)";
            }
            ++b;

        }
        return x;
    }

    private String[] sortGuilds() {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();

        for (final String s : pa.guild) {
            final String[] split = s.split(Pattern.quote("|&|"));
            map.put(split[0], Integer.valueOf(split[1]));
        }
        LinkedHashMap<String, Integer> linkedHashMap = sortValue(map);
        final String[] x = new String[20];

        int length = x.length;
        for (int i = 0; i < length; i++) {
            x[i] = i > 0 && i <= length - 2 ? "&7"+ i +". &fBrak" : "";
        }

        int b = 1;
        for (final Map.Entry<String, Integer> s : linkedHashMap.entrySet()) {
            if(b == 1){
                x[b] = "&7" + b + ". &c" + s.getKey() + " &8(&9" + s.getValue() + "&8)";
            }else if (b == 2){
                x[b] = "&7" + b + ". &6" + s.getKey() + " &8(&9" + s.getValue() + "&8)";
            }
            else if (b == 3){
                x[b] = "&7" + b + ". &e" + s.getKey() + " &8(&9" + s.getValue() + "&8)";
            }else {
                x[b] = "&7" + b + ". &f" + s.getKey() + " &8(&9" + s.getValue() + "&8)";
            }
            ++b;

        }
        return x;
    }

}
