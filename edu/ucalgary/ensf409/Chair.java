/**
*@author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
*@author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
*@author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
*@author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
*@version    1.1
*@since      1.0
*/

package edu.ucalgary.ensf409;

/**
 * Chair class denotes a chair item which is read in and initialized from the database.
 *  This contains fields for all of the specific components of a chair item
 *  (Legs, Arms, Seat, Cushion), which are used to make a new recycled chair.
 */
public class Chair extends FurnitureItem {
    private final char legs; //A character boolean (Y/N) for whether or not this chair has avaliable Legs
    private final char arms; //A character boolean (Y/N) for whether or not this chair has avaliable Arms
    private final char seat; //A character boolean (Y/N) for whether or not this chair has avaliable Seats
    private final char cushion; //A character boolean (Y/N) for whether or not this chair has avaliable Cushions

    /**
     * Chair constructor. Takes in parameters for all fields and initializes 
     *  the member variables. Uses a super() constuctor call to initialize the fields in
     *  FurnitureItem class.
     * @param id The ID number for a furniture item (ex: C0914).
     * @param type The Type for a furniture item (ex: Mesh for a chair).
     * @param legs A character boolean (Y/N) for whether or not this chair has avaliable Legs.
     * @param arms A character boolean (Y/N) for whether or not this chair has avaliable Arms.
     * @param seat A character boolean (Y/N) for whether or not this chair has avaliable Seats.
     * @param cushion A character boolean (Y/N) for whether or not this chair has avaliable Cushions.
     * @param price The Price for a furniture item (ex: 50 ($)).
     * @param manuId The Manufacturer ID number for a furniture item (ex: 002).
     */
    public Chair (String id, String type, 
                char legs, char arms, char seat, char cushion, 
                int price, String manuId) {
        super(id, type, price, manuId);
        this.legs = legs;
        this.arms = arms;
        this.seat = seat;
        this.cushion = cushion;
    }

    /**
     * Getter for Legs field.
     * @return A character boolean (Y/N) for whether or not this chair has avaliable Legs.
     */
    public char getLegs() {
        return legs;
    }

    /**
     * Getter for Arms field.
     * @return A character boolean (Y/N) for whether or not this chair has avaliable Arms.
     */
    public char getArms() {
        return arms;
    }

    /**
     * Getter for Seat field.
     * @return A character boolean (Y/N) for whether or not this chair has avaliable Seats.
     */
    public char getSeat() {
        return seat;
    }

    /**
     * Getter for Cushion field.
     * @return A character boolean (Y/N) for whether or not this chair has avaliable Cushions.
     */
    public char getCushion() {
        return cushion;
    }
}
