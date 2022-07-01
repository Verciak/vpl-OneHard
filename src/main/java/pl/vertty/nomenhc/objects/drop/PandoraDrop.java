package pl.vertty.nomenhc.objects.drop;

import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PandoraDrop {
    private final Integer name;

    private final double chance;

    private final String message;

    private final int minAmount;

    private final int maxAmount;

    private final ItemStack what;

    public PandoraDrop(int name, double chance, String string, ItemStack what, int maxAmount, int minAmount) {
        this.name = name;
        this.chance = chance;
        this.message = string;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.what = what;
    }

    public Integer getName() {
        return this.name;
    }

    public double getChance() {
        return this.chance;
    }

    public String getMessage() {
        return this.message;
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
}

