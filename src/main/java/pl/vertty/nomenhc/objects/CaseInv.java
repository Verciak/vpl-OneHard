package pl.vertty.nomenhc.objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import pl.vertty.nomenhc.helpers.RandomHelper;

public class CaseInv {
    private Player player;

    private int rool;

    private int roolMax;

    private Inventory inv;

    public CaseInv(Player player, Inventory inv) {
        this.player = player;
        this.rool = 0;
        this.roolMax = RandomHelper.getRandomInt(18, 27);
        this.inv = inv;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getRool() {
        return this.rool;
    }

    public void setRool(int rool) {
        this.rool = rool;
    }

    public int getRoolMax() {
        return this.roolMax;
    }

    public void setRoolMax(int roolMax) {
        this.roolMax = roolMax;
    }

    public Inventory getInv() {
        return this.inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }
}

