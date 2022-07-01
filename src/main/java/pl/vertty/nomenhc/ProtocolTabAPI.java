package pl.vertty.nomenhc;

import org.bukkit.entity.Player;
import pl.vertty.nomenhc.manager.ProtocolTab;

import java.util.UUID;

public final class ProtocolTabAPI {

    public static ProtocolTab getTablist(Player player) {
        return GuildPlugin.getPlugin().getManager().getTablist(player);
    }

    public static ProtocolTab getTablist(UUID uuid) {
        return GuildPlugin.getPlugin().getManager().getTablist(uuid);
    }

}
