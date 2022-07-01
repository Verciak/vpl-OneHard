package pl.vertty.nomenhc.handlers;



import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.objects.configs.Config;

public class SpawnManager {

    static Location spawn = new Location(Bukkit.getWorld("world"),Config.getInstance().spawnx, 0, Config.getInstance().spawnz);

    public static Location getLocation() {
        return spawn;
    }

    public static boolean isNonPvpArea(final Location loc) {
        final Location l2 = loc.getWorld().getSpawnLocation();
        final int distancex = (int) Math.abs(loc.getX() - l2.getX());
        final int distancez = (int) Math.abs(loc.getZ() - l2.getZ());
        return distancex <= Config.getInstance().spawndistance && distancez <= Config.getInstance().spawndistance;
    }

    public static void knockLinePvP(final Player p) {
        final double x = p.getLocation().getX() - p.getWorld().getSpawnLocation().getX();
        final double z = p.getLocation().getZ() - p.getWorld().getSpawnLocation().getZ();
        p.setVelocity(p.getLocation().getDirection().multiply(0.2));
    }
}
