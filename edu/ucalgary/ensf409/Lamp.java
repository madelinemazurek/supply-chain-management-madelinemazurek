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
 * Lamp class denotes a lamp item which is read in and initialized from the database.
 *  This contains fields for all of the specific components of a lamp item
 *  (Base, Bulb), which are used to make a new recycled lamp.
 */
public class Lamp extends FurnitureItem {
    private final char base; //A character boolean (Y/N) for whether or not this lamp has an avaliable Base
    private final char bulb; //A character boolean (Y/N) for whether or not this lamp has an avaliable Bulb

    /**
     * Lamp constructor. Takes in parameters for all fields and initializes 
     *  the member variables. Uses a super() constuctor call to initialize the fields in
     *  FurnitureItem class.
     * @param id The ID number for a furniture item (ex: C0914).
     * @param type The Type for a furniture item (ex: Mesh for a chair).
     * @param base A character boolean (Y/N) for whether or not this lamp has an avaliable Base.
     * @param bulb A character boolean (Y/N) for whether or not this lamp has an avaliable Bulb.
     * @param price The Price for a furniture item (ex: 50 ($)).
     * @param manuId The Manufacturer ID number for a furniture item (ex: 002).
     */
    public Lamp (String id, String type, 
                char base, char bulb,
                int price, String manuId) {
        super(id, type, price, manuId);
        this.base = base;
        this.bulb = bulb;
    }

    /**
     * Getter for Base field.
     * @return A character boolean (Y/N) for whether or not this lamp has an avaliable Base.
     */
    public char getBase() {
        return base;
    }

    /**
     * Getter for Bulb field.
     * @return A character boolean (Y/N) for whether or not this lamp has an avaliable Bulb.
     */
    public char getBulb() {
        return bulb;
    }
}