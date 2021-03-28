/**
*@author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
*@author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
*@author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
*@author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
*@version    1.2
*@since      1.0
*/

package edu.ucalgary.ensf409;

/**
 * FurnitureItem class denotes an isntance of a furniture item, read in from the database.
 *  This contains fields for all of the shared information between any type of furniture object
 *  (ID number, Type, Price and ManuID). All specific types of furniture obects are represented by
 *  individual classes which extend this class.
 */
public class FurnitureItem {
    private final String id; //the ID number for a furniture item (ex: C0914)
    private final String type; //the Type for a furniture item (ex: Mesh for a chair)
    private final int price; //the Price for a furniture item (ex: 50 ($))
    private final String manuId; //the Manufacturer ID number for a furniture item (ex: 002)

    /**
     * FurnitureItem constructor. Takes in parameters for all fields and initializes 
     *  the member variables.
     * @param id The ID number for a furniture item (ex: C0914).
     * @param type The Type for a furniture item (ex: Mesh for a chair).
     * @param price The Price for a furniture item (ex: 50 ($)).
     * @param manuId The Manufacturer ID number for a furniture item (ex: 002).
     */
    public FurnitureItem (String id, String type, int price, String manuId) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.manuId = manuId;
    }

    /**
     * Getter for ID field.
     * @return The ID number for a furniture item (ex: C0914).
     */
    public String getID() {
        return id;
    }

    /**
     * Getter for Type field.
     * @return The Type for a furniture item (ex: Mesh for a chair).
     */
    public String getType() {
        return type;
    }

    /**
     * Getter for Price field.
     * @return The Price for a furniture item (ex: 50 ($)).
     */
    public int getPrice() {
        return price;
    }

    /**
     * Getter for Manufacturer ID number field.
     * @return The Manufacturer ID number for a furniture item (ex: 002).
     */
    public String getManuID() {
        return manuId;
    }
}