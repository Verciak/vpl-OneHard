package pl.vertty.nomenhc.helpers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Cooldown {

    private final Map<Player, Long> cooldownMap = new HashMap<>();

    public void setCooldown(Player playerUUID, int seconds) {
        cooldownMap.put(playerUUID, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(seconds));
    }

    public boolean hasCooldown(Player playerUUID) {
        if (!cooldownMap.containsKey(playerUUID)) return false;
        if (System.currentTimeMillis() < cooldownMap.get(playerUUID)) return true;
        cooldownMap.remove(playerUUID);
        return false;
    }
}