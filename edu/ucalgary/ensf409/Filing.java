package edu.ucalgary.ensf409;

public class Filing extends FurnitureItem {
    private final char rails;
    private final char drawers;
    private final char cabinet;

    public Filing (String id, String type, 
                char rails, char drawers, char cabinet,
                int price, String manuId) {
        super(id, type, price, manuId);
        this.rails = rails;
        this.drawers = drawers;
        this.cabinet = cabinet;
    }

    public char getRails() {
        return rails;
    }

    public char getDrawers() {
        return drawers;
    }

    public char getCabinet() {
        return cabinet;
    }
}
