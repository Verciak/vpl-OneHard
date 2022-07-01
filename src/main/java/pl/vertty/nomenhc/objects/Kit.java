package pl.vertty.nomenhc.objects;

import pl.vertty.nomenhc.helpers.ItemBuilder;

import java.util.ArrayList;

public class Kit {
    private String name;

    private String perm;

    private Integer time;
    private final Integer slot;

    private GuiItem gui_item;

    private ArrayList<ItemBuilder> items;

    public Kit(String name, String perm, Integer time, Integer slot, GuiItem item, ArrayList<ItemBuilder> items) {
        this.name = name;
        this.perm = perm;
        this.time = time;
        this.slot = slot;
        this.gui_item = item;
        this.items = items;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTime() {
        return this.time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getSlot() {
        return this.slot;
    }

    public GuiItem getGui_item() {
        return this.gui_item;
    }

    public void setGui_item(GuiItem gui_item) {
        this.gui_item = gui_item;
    }

    public ArrayList<ItemBuilder> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<ItemBuilder> items) {
        this.items = items;
    }

    public String getPerm() {
        return this.perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }
}

