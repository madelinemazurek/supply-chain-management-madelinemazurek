package edu.ucalgary.ensf409;

public class FurnitureItem {
    private final String id;
    private final String type;
    private final int price;
    private final String manuId;

    public FurnitureItem (String id, String type, int price, String manuId) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.manuId = manuId;
    }

    public String getID() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public String getManuID() {
        return manuId;
    }
}
