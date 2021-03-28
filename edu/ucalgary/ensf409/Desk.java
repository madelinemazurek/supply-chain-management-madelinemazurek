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
 * Desk class denotes a desk item which is read in and initialized from the database.
 *  This contains fields for all of the specific components of a desk item
 *  (Legs, Top, Drawer), which are used to make a new recycled desk.
 */
public class Desk extends FurnitureItem {
    private final char legs; //A character boolean (Y/N) for whether or not this desk has avaliable Legs
    private final char top; //A character boolean (Y/N) for whether or not this desk has an avaliable Top
    private final char drawer; //A character boolean (Y/N) for whether or not this desk has an avaliable Drawer

    /**
     * Desk constructor. Takes in parameters for all fields and initializes 
     *  the member variables. Uses a super() constuctor call to initialize the fields in
     *  FurnitureItem class.
     * @param id The ID number for a furniture item (ex: C0914).
     * @param type The Type for a furniture item (ex: Mesh for a chair).
     * @param legs A character boolean (Y/N) for whether or not this desk has avaliable Legs.
     * @param top A character boolean (Y/N) for whether or not this desk has an avaliable Top.
     * @param drawer A character boolean (Y/N) for whether or not this desk has an avaliable Drawer.
     * @param price The Price for a furniture item (ex: 50 ($)).
     * @param manuId The Manufacturer ID number for a furniture item (ex: 002).
     */
    public Desk (String id, String type, 
                char legs, char top, char drawer,
                int price, String manuId) {
        super(id, type, price, manuId);
        this.legs = legs;
        this.top = top;
        this.drawer = drawer;
    }

    /**
     * Getter for Legs field.
     * @return A character boolean (Y/N) for whether or not this desk has avaliable Legs.
     */
    public char getLegs() {
        return legs;
    }

    /**
     * Getter for Top field.
     * @return A character boolean (Y/N) for whether or not this desk has an avaliable Top.
     */
    public char getTop() {
        return top;
    }

    /**
     * Getter for Drawer field.
     * @return A character boolean (Y/N) for whether or not this desk has an avaliable Drawer.
     */
    public char getDrawer() {
        return drawer;
    }
}
