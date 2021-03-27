package edu.ucalgary.ensf409;

public class Lamp extends FurnitureItem {
    private final char base;
    private final char bulb;

    public Lamp (String id, String type, 
                char base, char bulb,
                int price, String manuId) {
        super(id, type, price, manuId);
        this.base = base;
        this.bulb = bulb;
    }

    public char getBase() {
        return base;
    }

    public char getBulb() {
        return bulb;
    }
}