package pl.vertty.nomenhc.objects;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.vertty.nomenhc.GuildPlugin;
import pl.vertty.nomenhc.handlers.GuildHandler;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
import pl.vertty.nomenhc.helpers.GuildHelper;
import pl.vertty.nomenhc.helpers.LocationSerializer;
import pl.vertty.nomenhc.objects.configs.Config;
import lombok.Data;
import org.bson.Document;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static pl.vertty.nomenhc.helpers.ColorHelper.colored;

@Data
public class Guild extends GuildHandler {

    private final UUID uuid;
    private final String tag;
    private final String name;

    private String owner;
    private Set<String> coOwner;
    private Set<String> masters;

    private Integer points;
    private Integer kills;
    private Integer deaths;

    private Integer lives;

    private final Date createDate;
    private Date expireDate;
    private Date nextConquerDate;
    private Date tntProtectionExpireDate;

    private Set<String> members;
    private Set<UUID> allies;

    private Set<String> membersInvites;
    private Set<UUID> alliesInvites;

    private Boolean guildPvp;
    private Boolean allyPvp;

    private Integer guildSize;
    private final double centerX;
    private final Integer centerY;
    private final double centerZ;
    private double homeX;
    private double homeY;
    private double homeZ;


    public boolean isInCentrum(final Location location, final int n, final int n2, final int n3) {
        final Location clone = this.getLocation().clone();
        return clone.getY() - n2 <= location.getY() && clone.getY() + n >= location.getY() && location.getX() <= clone.getX() + n3 && location.getX() >= clone.getX() - n3 && location.getZ() <= clone.getZ() + n3 && location.getZ() >= clone.getZ() - n3;
    }


    public Location getLocation() {
        return new Location(Bukkit.getWorld("world"), centerX, Config.getInstance().guildCenterY, centerZ);
    }


    public Guild(String tag, String name, Player owner){
        uuid = UUID.randomUUID();
        this.tag = tag;
        this.name = name;

        this.owner = owner.getName();
        coOwner = Sets.newConcurrentHashSet();
        masters = Sets.newConcurrentHashSet();

        points = Config.getInstance().guildDefaultPoints;
        kills = 0;
        deaths = 0;
        lives = Config.getInstance().guildDefaultLives;

        createDate = new Date(System.currentTimeMillis());
        expireDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 2));
        nextConquerDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));
        tntProtectionExpireDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24));

        members = Sets.newConcurrentHashSet();
        members.add(this.owner);

        membersInvites = Sets.newConcurrentHashSet();
        allies = Sets.newConcurrentHashSet();
        alliesInvites = Sets.newConcurrentHashSet();

        guildPvp = false;
        allyPvp = false;

        guildSize = Config.getInstance().guildDefaultSize;

        centerX = owner.getLocation().getX();
        centerY = Config.getInstance().guildCenterY;
        centerZ = owner.getLocation().getZ();


        homeX = owner.getLocation().getX();
        homeY = owner.getLocation().getY();
        homeZ = owner.getLocation().getZ();
        add(this);
        insert();
    }

    public boolean hasMember(String nickName) {
        return this.members.contains(nickName);
    }

    private void insert(){
        DatabaseHelper.guildCollection.insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void synchronize(){
        DatabaseHelper.guildCollection.findOneAndUpdate(new Document("uuid", uuid.toString()), new Document("$set", Document.parse(new Gson().toJson(this))));
    }

    public void delete(String reason) {
        DatabaseHelper.guildCollection.findOneAndDelete(new Document("uuid", uuid.toString()));
        remove(this);
        GuildHelper.removeRoom(this);
        for (final Hologram pe : HologramsAPI.getHolograms(GuildPlugin.getPlugin())) {
            if (pe.getLine(1).toString().contains("§2Gildia: §a" + getTag() + " " + getName())) {
                pe.delete();
            }
        }
        Bukkit.getOnlinePlayers().forEach(target -> target.sendMessage(colored("&cGildia &8[&4"+tag+"&8] &czostala usunieta! &8(&c"+reason+"&8)")));
    }

    public boolean isOnCuboid(Location location){
        return Math.abs(location.getX() - centerX) <= guildSize && Math.abs(location.getZ() - centerZ) <= guildSize;
    }

    public Location getCenterLocation(){
        return new Location(Bukkit.getWorld("world"), centerX,centerY,centerZ);
    }

    public Location getHomeLocation(){
        return new Location(Bukkit.getWorld("world"), homeX,homeY,homeZ);
    }

    public void statIncrement(int points, int kills, int deaths){
        this.points = this.points + points;
        this.kills = this.kills + kills;
        this.deaths = this.deaths + deaths;
    }

    public boolean isOnCuboid(String world, int x, int z) {
        if (!world.equals("world")) {
            return false;
        }
        int distancex = Math.abs(x - new LocationSerializer("world", (int) centerX,  centerY, (int) centerZ).getX());
        int distancez = Math.abs(z - new LocationSerializer("world", (int) centerX,  centerY, (int) centerZ).getZ());
        return distancex - 1 <= new LocationSerializer("world", (int) centerX,  centerY, (int) centerZ).getSize() && distancez - 1 <= new LocationSerializer("world", (int) centerX,  centerY, (int) centerZ).getSize();
    }

}
