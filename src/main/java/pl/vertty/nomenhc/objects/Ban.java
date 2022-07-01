package pl.vertty.nomenhc.objects;

import com.google.gson.Gson;
import pl.vertty.nomenhc.helpers.DatabaseHelper;
import org.bson.Document;

public class Ban {
    private final String name;

    private final String uuid;

    private final String ip;

    private final String reason;

    private final String admin;

    private final String exire;


    public Ban(String uuid, String name, String ip, String reason, String admin, long expire) {
        this.name = name;
        this.uuid = uuid;
        this.ip = ip;
        this.reason = reason;
        this.admin = admin;
        this.exire = String.valueOf(expire);
        insert();
    }


    private void insert() {
        DatabaseHelper.bansCollecion.insertOne(Document.parse(new Gson().toJson(this)));
    }

    public void delete() {
        DatabaseHelper.bansCollecion.findOneAndDelete(new Document("name", this.name));
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public String getIp() {
        return this.ip;
    }

    public String getReason() {
        return this.reason;
    }

    public String getAdmin() {
        return this.admin;
    }

    public boolean isExpired() {
        return (Long.parseLong(this.exire) != 0L && Long.parseLong(this.exire) < System.currentTimeMillis());
    }

    public long getExire() {
        return Long.parseLong(this.exire);
    }
}
