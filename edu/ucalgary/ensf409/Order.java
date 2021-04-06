/**
*@author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
*@author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
*@author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
*@author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
*@version    1.3
*@since      1.0
*/

package edu.ucalgary.ensf409;
import java.util.ArrayList;

/**
 * Order class represents an order object, which contains all of the FurnitureItem information
 *  used to complete a single order, as well as the total cost of that order. The array of
 *  furniture items is stored as a generic ArrayList, and the cost is stored as an integer.
 */
public class Order <T> {
    private ArrayList<T> items; //contains all FurnitureItems for the order
    private int cost = 0; //total cost of the order

    /**
     * Order constructor, recieves an array list of FurnitureItems used to initialze the
     *  items field. Initializes the cost field by calling calculateCost().
     * @param items ArrayList of FurnitureItems for the order.
     */
    public Order(ArrayList<T> items){
        this.items = items;
        calculateCost();
    }

    /**
     * Getter for Cost field.
     * @return Total cost of the Order.
     */
    public int getCost(){
        return this.cost;
    }

    /**
     * Getter for all ID's in the order, returned as an array of strings.
     * @return String array of ID's for all items in the order.
     */
    public String[] getIDs(){
        ArrayList<String> retString = new ArrayList<>();
        //add ID to array list for each item
        for(int i = 0; i < this.items.size(); i++) {
            retString.add(((FurnitureItem)this.items.get(i)).getID());
        }
        //return array list as an array
        return (String[]) retString.toArray(new String[0]);
    }

    /**
     * Method used by constructor to calculate total cost for the order.
     */
    public void calculateCost(){
        //cost starts as zero, add cost of each item in order.
        for(int i = 0; i < this.items.size(); i++){
            this.cost += ((FurnitureItem)this.items.get(i)).getPrice();
        }
    }
}