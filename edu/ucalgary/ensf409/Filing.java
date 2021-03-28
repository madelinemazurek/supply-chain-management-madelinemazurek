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
 * Filing class denotes a filing item which is read in and initialized from the database.
 *  This contains fields for all of the specific components of a filing item
 *  (Rails, Drawers, Cabinet), which are used to make a new recycled filing.
 */
public class Filing extends FurnitureItem {
    private final char rails; //A character boolean (Y/N) for whether or not this filing cabinet has avaliable Rails
    private final char drawers; //A character boolean (Y/N) for whether or not this filing cabinet has avaliable Drawers
    private final char cabinet; //A character boolean (Y/N) for whether or not this filing cabinet has an avaliable Cabinet

    /**
     * Filing constructor. Takes in parameters for all fields and initializes 
     *  the member variables. Uses a super() constuctor call to initialize the fields in
     *  FurnitureItem class.
     * @param id The ID number for a furniture item (ex: C0914).
     * @param type The Type for a furniture item (ex: Mesh for a chair).
     * @param rails A character boolean (Y/N) for whether or not this filing cabinet has avaliable Rails.
     * @param drawers A character boolean (Y/N) for whether or not this filing cabinet has avaliable Drawers.
     * @param cabinet A character boolean (Y/N) for whether or not this filing cabinet has an avaliable Cabinet.
     * @param price The Price for a furniture item (ex: 50 ($)).
     * @param manuId The Manufacturer ID number for a furniture item (ex: 002).
     */
    public Filing (String id, String type, 
                char rails, char drawers, char cabinet,
                int price, String manuId) {
        super(id, type, price, manuId);
        this.rails = rails;
        this.drawers = drawers;
        this.cabinet = cabinet;
    }

    /**
     * Getter for Rails field.
     * @return A character boolean (Y/N) for whether or not this filing cabinet has avaliable Rails.
     */
    public char getRails() {
        return rails;
    }

    /**
     * Getter for Drawers field.
     * @return A character boolean (Y/N) for whether or not this filing cabinet has avaliable Drawers.
     */
    public char getDrawers() {
        return drawers;
    }

    /**
     * Getter for Cabinet field.
     * @return A character boolean (Y/N) for whether or not this filing cabinet has an avaliable Cabinet.
     */
    public char getCabinet() {
        return cabinet;
    }
}
