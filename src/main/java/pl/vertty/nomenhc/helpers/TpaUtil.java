package pl.vertty.nomenhc.helpers;


import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class TpaUtil {
    private static final HashMap<Player, Player> lastReceiverBySender = new HashMap<>();

    private static final HashMap<Player, Player> lastSenderByReceiver = new HashMap<>();

    private static final HashMap<Player, Long> lastSenderRequestTime = new HashMap<>();

    private static final HashMap<Player, BukkitTask> playerTeleportLocation = new HashMap<>();

    public static void acceptRequest(Player sender, Player receiver) {
        lastReceiverBySender.remove(sender);
        lastSenderByReceiver.remove(receiver);
        lastSenderRequestTime.remove(sender);
    }

    public static void denyRequest(Player sender, Player receiver) {
        lastReceiverBySender.remove(sender);
        lastSenderByReceiver.remove(receiver);
        lastSenderRequestTime.remove(sender);
    }

    public static void sentRequest(Player sender, Player receiver) {
        lastReceiverBySender.put(sender, receiver);
        lastSenderByReceiver.put(receiver, sender);
        lastSenderRequestTime.put(sender, System.currentTimeMillis());
    }

    public static HashMap<Player, Player> getLastReceiverBySender() {
        return lastReceiverBySender;
    }

    public static HashMap<Player, Player> getLastSenderByReceiver() {
        return lastSenderByReceiver;
    }

    public static HashMap<Player, Long> getLastSenderRequestTime() {
        return lastSenderRequestTime;
    }

    public static HashMap<Player, BukkitTask> getPlayerTeleportLocation() {
        return playerTeleportLocation;
    }
}

