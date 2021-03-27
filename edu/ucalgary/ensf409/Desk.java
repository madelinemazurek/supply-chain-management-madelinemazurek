package edu.ucalgary.ensf409;

public class Desk extends FurnitureItem {
    private final char legs;
    private final char top;
    private final char drawer;

    public Desk (String id, String type, 
                char legs, char top, char drawer,
                int price, String manuId) {
        super(id, type, price, manuId);
        this.legs = legs;
        this.top = top;
        this.drawer = drawer;
    }

    public char getLegs() {
        return legs;
    }

    public char getTop() {
        return top;
    }

    public char getDrawer() {
        return drawer;
    }
}
