package pl.vertty.nomenhc.objects.drop;


import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Drop {
    private final Integer name;

    private final double chance;


    private final int exp;

    private final String message;

    private final boolean fortune;

    private final int minAmount;

    private final int maxAmount;

    private final ItemStack what;

    private final Material from;

    private final Set<UUID> disabled;

    public Drop(int name, double chance, String string, ItemStack what, Material from, int maxAmount, int minAmount, boolean fortune, int exp) {
        this.disabled = new HashSet<>();
        this.name = name;
        this.chance = chance;
        this.exp = exp;
        this.message = string;
        this.fortune = fortune;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.from = from;
        this.what = what;
    }

    public void changeStatus(UUID uuid) {
        if (this.disabled.contains(uuid)) {
            this.disabled.remove(uuid);
        } else {
            this.disabled.add(uuid);
        }
    }

    public void setStatus(UUID uuid, boolean b) {
        if (b) {
            this.disabled.remove(uuid);
        } else this.disabled.add(uuid);
    }

    public boolean isDisabled(UUID uuid) {
        return this.disabled.contains(uuid);
    }

    public Integer getName() {
        return this.name;
    }

    public double getChance() {
        return this.chance;
    }

    public int getExp() {
        return this.exp;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isFortune() {
        return this.fortune;
    }


    public int getMinAmount() {
        return this.minAmount;
    }

    public int getMaxAmount() {
        return this.maxAmount;
    }

    public ItemStack getWhat() {
        return this.what;
    }

    public Material getFrom() {
        return this.from;
    }

    public Set<UUID> getDisabled() {
        return this.disabled;
    }

}

