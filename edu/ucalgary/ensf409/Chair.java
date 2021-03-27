package edu.ucalgary.ensf409;

public class Chair extends FurnitureItem {
    private final char legs;
    private final char arms;
    private final char seat;
    private final char cushion;

    public Chair (String id, String type, 
                char legs, char arms, char seat, char cushion, 
                int price, String manuId) {
        super(id, type, price, manuId);
        this.legs = legs;
        this.arms = arms;
        this.seat = seat;
        this.cushion = cushion;
    }

    public char getLegs() {
        return legs;
    }

    public char getArms() {
        return arms;
    }

    public char getSeat() {
        return seat;
    }

    public char getCushion() {
        return cushion;
    }
}
