/**
*@author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
*@author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
*@author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
*@author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
*@version    1.5
*@since      1.0
*/

package edu.ucalgary.ensf409;
import java.util.*;

/**
 * SearchInventory Class revieves the user input for a requested order
 *  (furnitureCategory, model, numItems), and searches the database for the cheapest way to
 *  complete this order. After searching, the fields for bestOrder and orderFound will be initialized,
 *  reflecting what the best possible order is (if one exists) and if completing an order is posiible at
 *  all given the current database and the requested order.
 */
public class SearchInventory <T> {
    private String furnitureCategory; //table in database to be searched
    private int numItems; //number of items that the order must fulfill

    //array of all items of model type in the current inventory
    private ArrayList<T> items = new ArrayList<>(); 

    //array of all possible orders that satisfy requirements not considering cost,
    //simply every possible order
    private ArrayList <Order<FurnitureItem>> allOrders = new ArrayList<>(); 
                                                                            
    private Order<FurnitureItem> bestOrder; //the cheapest order that satisfies the requirements
    private boolean orderFound = false; // true if an order can be fulfilled, false otherwise

    /**
     * SearchInventory constructor recieves all information for a requested order
     *  (furnitureCategory, model, numItems), as well as the DatabaseAccess object that is used
     *  to retrieve the desired information from the database. Initializes all fields by calling
     *  generateAllSets() and selectBestOrder(), which initialize the bestOrder and orderFound
     *  fields after performing their desired functionality.
     * @param furnitureCategory Table in database to be searched.
     * @param model Type of FurnitureItem to be searched for.
     * @param numItems Number of items that the order must fulfill.
     * @param db DatabaseAccess object used to access the database.
     */
    public SearchInventory (String furnitureCategory, String model, int numItems, DatabaseAccess db) {
        this.furnitureCategory = furnitureCategory;
        this.numItems = numItems;

        //call databaseAccess method to initialize items array,
        //contains the reduced table, which only includes the rows with the right type
        T[] tempArray = db.objectConstructor(furnitureCategory, model);
        for(int i = 0; i < tempArray.length; i++){
            this.items.add(tempArray[i]);
        }

        generateAllSets(); //creates a powerset from the set of all eligible items, 
        selectBestOrder(); //finds the cheapest of all orders that fulfill requirements
    }

    /**
     * Getter for OrderFound field.
     * @return True if an order can be fulfilled, false otherwise.
     */
    public boolean getOrderFound() {
        return this.orderFound;
    }

    /**
     * Getter for BestOrder field.
     * @return The cheapest order that satisfies all of the requirements.
     */
    public Order<FurnitureItem> getBestOrder() {
        return bestOrder;
    }

    /**
     * Method generateAllSets runs through all possible sets (power set of items) calling
     *  checkValidSet() for each. See commenting for a rigorous expaination of the implentation.
     * @param <T> Generic type for any FurnitureItem object.
     */
    private <T> void generateAllSets(){

        //We calculate the total number of subsets from the set of all matching furniture types.
        //n is the total number of subsets (2^n)
        int n = (int) Math.pow(2,items.size());
    
        //create all subsets 
        //In this outside loop, we iterate from 1 to the size of the powerset
        //we iterate from 1 because we want to ignore the empty set (the subset containing no elements)
        for(int i = 1; i < n; i++){
            ArrayList<T> subset= new ArrayList<>();
            //in the inner loop, we iterate from zero to the size of the original set
            //we imagine i as a binary number, and we will use the set bits to determine 
            //which elements to include in this element of the powerset
            for(int j = 0; j < items.size();j++){
                //we will create a bitmask to determine which of the bits of i are set
                //we create the bitmask by left shifting a 1 by j bits, and then bitwise ANDing i and
                //the bit-shifted 1. If the result of the bitwise AND isn't zero, we know that the
                //bitmask's 1 overlapped with a 1 in i therefore, we include it in the subset
                if((i & (1<<j)) != 0){
                    //we iterate through every bit of i using this left shifting bit mask to determine
                    //all elements that should be contained in the subset
                    subset.add((T)items.get(j));
                }
            }
            //check if if the subset generated is valid
            checkValidSet(subset);
        }
    }

    /**
     * Method checkValidSet calls check_____Set based on which type of FurnitureItem is requested
     *  for the order. If check______Set returns true, create order for set and add order to allOrders.
     * @param <T>  Generic type for any FurnitureItem object.
     * @param subset Any subset of the items array.
     */
    private <T> void checkValidSet(ArrayList<T> subset) {
        if(furnitureCategory.toLowerCase().equals("chair")) {
            if(checkChairSet((Chair[])subset.toArray(new Chair[0])) == true) {
                //if subset fulfills a vaild order of chairs, create order and add to allOrders.
                Order<FurnitureItem> theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("desk")) {
            if(checkDeskSet((Desk[])subset.toArray(new Desk[0])) == true) {
                //if subset fulfills a vaild order of desks, create order and add to allOrders.
                Order<FurnitureItem> theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("filing")) {
            if(checkFilingSet((Filing[])subset.toArray(new Filing[0])) == true) {
                //if subset fulfills a vaild order of filing cabinets, create order and add to allOrders.
                Order<FurnitureItem> theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("lamp")) {
            if(checkLampSet((Lamp[])subset.toArray(new Lamp[0])) == true) {
                //if subset fulfills a vaild order of lamps, create order and add to allOrders.
                Order<FurnitureItem> theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        }
    }

    /**
     * Checks through the chair subset, seeing if there are enough instances 
     *  of each individual field (each type of component) in order for this subset 
     *  to be a complete order.
     * @param subset An array of chair objects to be analysed.
     * @return True if this set satisfies a valid order, false otherwise.
     */
    private boolean checkChairSet(Chair[] subset){ 
        int numLegs = 0;
        int numArms = 0;
        int numSeats = 0;
        int numCushions = 0;
        //count all instances of each field
        for(int i = 0; i < subset.length; i++) {
            if(subset[i].getLegs() == 'Y') {
                numLegs++;
            }
            if(subset[i].getArms() == 'Y') {
                numArms++;
            }
            if(subset[i].getSeat() == 'Y') {
                numSeats++;
            }
            if(subset[i].getCushion() == 'Y') {
                numCushions++;
            }
        }
        //must have at least enough instances of each component to satify an order
        if((numLegs >= numItems)&&(numArms >= numItems)
         &&(numSeats >= numItems)&&(numCushions >= numItems)) {
            return true;
        }
        return false;
    }

    /**
     * Checks through the desk subset, seeing if there are enough instances 
     *  of each individual field (each type of component) in order for this subset 
     *  to be a complete order.
     * @param subset An array of desk objects to be analysed.
     * @return True if this set satisfies a valid order, false otherwise.
     */
    private boolean checkDeskSet(Desk[] subset){ 
        int numLegs = 0;
        int numTops = 0;
        int numDrawers = 0;
        //count all instances of each field
        for(int i = 0; i < subset.length; i++) {
            if(subset[i].getLegs() == 'Y') {
                numLegs++;
            }
            if(subset[i].getTop() == 'Y') {
                numTops++;
            }
            if(subset[i].getDrawer() == 'Y') {
                numDrawers++;
            }
        }
        //must have at least enough instances of each component to satify an order
        if((numLegs >= numItems)&&(numTops >= numItems)
         &&(numDrawers >= numItems)) {
            return true;
        }
        return false;
    }

    /**
     * Checks through the filing subset, seeing if there are enough instances 
     *  of each individual field (each type of component) in order for this subset 
     *  to be a complete order.
     * @param subset An array of filing objects to be analysed.
     * @return True if this set satisfies a valid order, false otherwise.
     */
    private boolean checkFilingSet(Filing[] subset){ 
        int numRails = 0;
        int numDrawers = 0;
        int numCabinets = 0;
        //count all instances of each field
        for(int i = 0; i < subset.length; i++) {
            if(subset[i].getRails() == 'Y') {
                numRails++;
            }
            if(subset[i].getDrawers() == 'Y') {
                numDrawers++;
            }
            if(subset[i].getCabinet() == 'Y') {
                numCabinets++;
            }
        }
        //must have at least enough instances of each component to satify an order
        if((numRails >= numItems)&&(numDrawers >= numItems)
         &&(numCabinets >= numItems)) {
            return true;
        }
        return false;
    }

    /**
     * Checks through the lamp subset, seeing if there are enough instances 
     *  of each individual field (each type of component) in order for this subset 
     *  to be a complete order.
     * @param subset An array of lamp objects to be analysed.
     * @return True if this set satisfies a valid order, false otherwise.
     */
    private boolean checkLampSet(Lamp[] subset){ 
        int numBases = 0;
        int numBulbs = 0;
        //count all instances of each field
        for(int i = 0; i < subset.length; i++) {
            if(subset[i].getBase() == 'Y') {
                numBases++;
            }
            if(subset[i].getBulb() == 'Y') {
                numBulbs++;
            }
        }
        //must have at least enough instances of each component to satify an order
        if((numBases >= numItems)&&(numBulbs >= numItems)) {
            return true;
        }
        return false;
    }

    /**
     * Method selectBestOrder will search for the cheapest order from allOrders,
     *  will update bestOrder, and will change orderFound to true if allOrders is not empty.
     */
    private void selectBestOrder() { 
        //no valid orders are possible with given inventory
        if(allOrders.size() == 0) {
            return;
        }

        //at least one valid order has been found.
        this.orderFound = true;

        //find the location of the cheapest order in allOrders
        int cheapestIndex = 0;
        for(int i = 1; i < allOrders.size(); i++) {
            if (allOrders.get(i).getCost() < allOrders.get(cheapestIndex).getCost()) {
                cheapestIndex = i;
            }
        }

        //set bestOrder to the cheapest order
        this.bestOrder = allOrders.get(cheapestIndex);
    }
}