package pl.vertty.nomenhc.helpers;

import org.bukkit.Location;
import pl.vertty.nomenhc.objects.configs.Config;


public class LocationHelper {

    public static boolean isBlockLocation(Location location1, Location location2){
        return location1.getX() == location2.getX() && location1.getY() == location2.getY() && location1.getZ() == location2.getZ();
    }

    public static double getDistanceMin(Location location1, Location location2){
        final double xDiff = Math.abs(location1.getX() - location2.getX()),
                zDiff = Math.abs(location1.getZ() - location2.getZ());
        return Math.min(xDiff,zDiff);
    }

    public static double getDistanceMax(Location location1, Location location2){
        final double xDiff = Math.abs(location1.getX() - location2.getX()),
                zDiff = Math.abs(location1.getZ() - location2.getZ());
        return Math.max(xDiff,zDiff);
    }

    public static boolean getBorderDistance(final Location loc) {
        return Math.abs(Config.getInstance().worldBorder - loc.getX()) >= Config.getInstance().guildBorderDistance && Math.abs(Config.getInstance().worldBorder - loc.getZ()) >= Config.getInstance().guildBorderDistance && Math.abs(-Config.getInstance().worldBorder - loc.getX()) >= Config.getInstance().guildBorderDistance && Math.abs(-Config.getInstance().worldBorder - loc.getZ()) >= Config.getInstance().guildBorderDistance && Math.abs(-Config.getInstance().worldBorder - loc.getX()) >= Config.getInstance().guildBorderDistance && Math.abs(Config.getInstance().worldBorder - loc.getZ()) >= Config.getInstance().guildBorderDistance && Math.abs(Config.getInstance().worldBorder - loc.getX()) >= Config.getInstance().guildBorderDistance && Math.abs(-Config.getInstance().worldBorder - loc.getZ()) >= Config.getInstance().guildBorderDistance;
    }

}
