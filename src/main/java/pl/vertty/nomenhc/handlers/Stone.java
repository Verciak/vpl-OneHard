package pl.vertty.nomenhc.handlers;

import lombok.Getter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
@Getter
public class Stone {

    private final double x;
    private final double y;
    private final double z;

    private final String reset;

    private final Location location;

    public Stone(String reset, Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.reset = reset;
        this.location = location;
        insert();
    }

    public Stone(double x, double y, double z, Document d) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.reset = String.valueOf(d.getLong("reset"));
        this.location = new Location(Bukkit.getWorld("world"), d.getDouble("x"), d.getDouble("y"), d.getDouble("z"));
        if (!Bukkit.getServer().getWorld(this.location.getWorld().getName()).getBlockAt(this.location).getType().equals(Material.STONE))
            Bukkit.getServer().getWorld(this.location.getWorld().getName()).getBlockAt(this.location).setType(Material.STONE);
    }
    private void insert() {
        DatabaseHelper.stoneCollection.insertOne((new Document()).append("reset", this.reset).append("x", this.location.getX()).append("y", this.location.getY()).append("z", this.location.getZ()));
    }

    public void delete() {
        DatabaseHelper.stoneCollection.findOneAndDelete(new Document("location", this.location));
    }

    public String getReset() {
        return this.reset;
    }

    public Location getLocation() {
        return this.location;
    }
}

