package pl.vertty.nomenhc.helpers;

public class KitTimes {
    private String name;

    private String next;

    public KitTimes(String name, String next) {
        this.name = name;
        this.next = next;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNext() {
        return this.next;
    }

    public void setNext(String next) {
        this.next = next;
    }
}
