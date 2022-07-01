package pl.vertty.nomenhc.handlers;

import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import org.bson.Document;
import org.bukkit.Location;

public class StoneManager {
    static ArrayList<Stone> stone = new ArrayList<>();

    Block<Document> load;

    public static Stone getStone(Location location) {
        for (Stone s : stone) {
            if(s.getX() == location.getX() && s.getY() == location.getY() && s.getZ() == location.getZ())
                return s;
        }
        return null;
    }

    public static ArrayList<Stone> getStone() {
        return stone;
    }

    public static void delete(Stone s) {
        stone.remove(s);
        s.delete();
    }

    public static void create(Location loc, String reset) {
        if (getStone(loc) == null)
            stone.add(new Stone(reset, loc));
    }
}

