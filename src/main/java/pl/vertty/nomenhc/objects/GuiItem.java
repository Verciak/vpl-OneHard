package pl.vertty.nomenhc.objects;

import pl.vertty.nomenhc.helpers.ItemBuilder;

import java.util.List;

public class GuiItem {
    private Integer slot;

    private ItemBuilder itemBuilder;

    private List<String> lore;

    public GuiItem(Integer slot, ItemBuilder itemBuilder, List<String> lore) {
        this.slot = slot;
        this.lore = lore;
        this.itemBuilder = itemBuilder.removeLores();
    }

    public ItemBuilder getItemBuilder() {
        return this.itemBuilder;
    }

    public void setItemBuilder(ItemBuilder itemBuilder) {
        this.itemBuilder = itemBuilder;
    }

    public Integer getSlot() {
        return this.slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public List<String> getLore() {
        return this.lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}

