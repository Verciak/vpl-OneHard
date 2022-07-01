package pl.vertty.nomenhc.helpers;


import org.bukkit.entity.Player;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;


public class MessageHelper {

    public static void sendActionbar(Player player, String message) {
        ChatUtil.sendActionBar(player, message);
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        if (title == null) title = "";
        if (subtitle == null) subtitle = "";

       player.sendTitle(colored(title), colored(subtitle));
    }

}
