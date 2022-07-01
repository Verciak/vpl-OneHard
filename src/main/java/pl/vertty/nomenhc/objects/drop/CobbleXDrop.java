package pl.vertty.nomenhc.objects.drop;

import org.bukkit.inventory.ItemStack;

public class CobbleXDrop {
    private final Integer name;

    private final String message;

    private final int Amount;

    private final ItemStack what;

    public CobbleXDrop(int name, String string, ItemStack what, int Amount) {
        this.name = name;
        this.message = string;
        this.Amount = Amount;
        this.what = what;
    }

    public Integer getName() {
        return this.name;
    }

    public String getMessage() {
        return this.message;
    }

    public int getAmount() {
        return this.Amount;
    }

    public ItemStack getWhat() {
        return this.what;
    }
}

