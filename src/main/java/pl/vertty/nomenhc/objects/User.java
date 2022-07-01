package pl.vertty.nomenhc.objects;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import org.bukkit.entity.Player;
import pl.vertty.nomenhc.handlers.UserManager;
import pl.vertty.nomenhc.helpers.ArmorStandHelper;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
import lombok.Data;
import org.bson.Document;
import pl.vertty.nomenhc.helpers.KitTimes;
import pl.vertty.nomenhc.helpers.PacketHelper;
import pl.vertty.nomenhc.objects.configs.Config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.UUID;

@Data
public class User extends UserManager {

    private final UUID uuid;
    private final String name;

    private int points,kills,deaths, kox, ref, pearl, snowball, arrow;
    private String turbo_drop, turbo_exp;
    private boolean Taken, canTake, turbo;

    private String drops;
    private boolean whitelist;
    private boolean whitelist_status;
    private ArrayList<KitTimes> kits;

    public ArrayList<KitTimes> getKits() {
        return this.kits;
    }

    public boolean canTake() {
        return this.canTake;
    }

    public boolean canTurbo() {
        return this.turbo;
    }
    public void setTurbo(boolean paramBoolean) {
        this.turbo = paramBoolean;
    }


    public void setTaken(boolean paramBoolean) {
        this.Taken = paramBoolean;
    }

    public void setTake(boolean paramBoolean) {
        this.canTake = paramBoolean;
    }

    public boolean isTaken() {
        return this.Taken;
    }

    private final Set<DeathAction> deathActions;

    public String getTurbo_drop() {
        return turbo_drop;
    }

    public void setTurbo_drop(String turbo_drop) {
        this.turbo_drop = turbo_drop;
    }

    public String getTurbo_exp() {
        return turbo_exp;
    }

    public void setTurbo_exp(String turbo_exp) {
        this.turbo_exp = turbo_exp;
    }

    public String getDrops() {
        return drops;
    }

    public void setDrops(String drops) {
        this.drops = drops;
    }

    public User(Player player){
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        this.points = Config.getInstance().playerStartPoints;
        this.kills = 0;
        this.deaths = 0;
        this.kox = 0;
        this.ref = 0;
        this.pearl = 0;
        this.snowball = 0;
        this.arrow = 0;
        this.turbo_drop = "0";
        this.turbo_exp = "0";
        this.canTake = false;
        this.Taken = false;
        this.turbo = false;
        this.deathActions = Sets.newConcurrentHashSet();
        this.drops = "";
        this.whitelist = false;
        this.whitelist_status = true;
        this.kits = new ArrayList<>();
        add(this);
        insert();
    }


    private void insert(){
        DatabaseHelper.accountCollection.insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void synchronize(){
        DatabaseHelper.accountCollection.findOneAndUpdate(new Document("uuid", uuid.toString()), new Document("$set", Document.parse(new Gson().toJson(this))));
    }

    public void delete() {
        DatabaseHelper.accountCollection.findOneAndDelete(new Document("uuid", uuid.toString()));
    }

    public void statsIncrement(int points, int kills, int deaths) {
        this.points = this.points + points;
        this.kills = this.kills + kills;
        this.deaths = this.deaths + deaths;
    }
}
