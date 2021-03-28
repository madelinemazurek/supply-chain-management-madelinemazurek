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

    private ArrayList<T> items = new ArrayList<>(); //array of all items of model type in the current inventory
    private ArrayList <Order<FurnitureItem>> allOrders = new ArrayList<>(); //array of all possible orders that satisfy requirements
                                                                            //not considering cost, simply every possible order
    private Order<FurnitureItem> bestOrder; //the cheapest order that satisfies the requirements
    private boolean orderFound = false; // true if an order can be fulfilled, false otherwise

    /**
     * SearchInventory constructor recieves all information for a requested order (furnitureCategory, model, numItems),
     *  as well as the DatabaseAccess object that is used to retrieve the desired information from the database.
     *  Initializes all fields by calling generateAllSets() and selectBestOrder(), which initialize the bestOrder and
     *  orderFound fields after performing their desired functionality.
     * @param furnitureCategory Table in database to be searched.
     * @param model Type of FurnitureItem to be searched for.
     * @param numItems Number of items that the order must fulfill.
     * @param db DatabaseAccess object used to access the database.
     */
    public SearchInventory (String furnitureCategory, String model, int numItems, DatabaseAccess db) {
        this.furnitureCategory = furnitureCategory;
        this.numItems = numItems;

        //call databaseAccess method to initialize items array
        T[] tempArray = db.objectConstructor(furnitureCategory, model);
        for(int i = 0; i < tempArray.length; i++){
            this.items.add(tempArray[i]);
        }

        generateAllSets();
        selectBestOrder();
    }

    public boolean getOrderFound() {
        return this.orderFound;
    }

    public Order<FurnitureItem> getBestOrder() {
        return bestOrder;
    }

    //public void generateAllSets()
    //runs through all possible sets (power set of items) calling checkValidSet() for each

    //applicableTableRows is what is returned from databaseAccess class and contains the 
    //reduced table which only includes the rows with the right type
    private <T> void generateAllSets(){

        //n is the total number of subsets (2^n)
        int n = (int) Math.pow(2,items.size());
    
        //create all subsets 
        for(int i = 1; i < n; i++){
                
            ArrayList<T> subset= new ArrayList<>();
    
            for(int j = 0; j < items.size();j++){
                //if the jth bit of i is 1 we want to include that in the subset
                if((i & (1<<j)) != 0){
                    subset.add((T)items.get(j));
                }
            }
            //check if if the subset generated is valid
            checkValidSet(subset);
        }
    }

    //method checkValidSet returns void
    //calls check_____Set based on which type of FurnitureItem is requested
    //if check______Set returns true, create order for set and add order to allOrders
    private <T> void checkValidSet(ArrayList<T> subset) {
        if(furnitureCategory.toLowerCase().equals("chair")) {
            if(checkChairSet((Chair[])subset.toArray(new Chair[0])) == true) {
                Order<FurnitureItem> theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("desk")) {
            if(checkDeskSet((Desk[])subset.toArray(new Desk[0])) == true) {
                Order<FurnitureItem> theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("filing")) {
            if(checkFilingSet((Filing[])subset.toArray(new Filing[0])) == true) {
                Order<FurnitureItem> theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("lamp")) {
            if(checkLampSet((Lamp[])subset.toArray(new Lamp[0])) == true) {
                Order<FurnitureItem> theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        }
    }

    /**
     * Checks through the chair subset, seing if there are enough instances 
     *  of each individual field (each type of component) in order for this subset 
     *  to be a complete order.
     */
    private boolean checkChairSet(Chair[] subset){ 
        int numLegs = 0;
        int numArms = 0;
        int numSeats = 0;
        int numCushions = 0;
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
        if((numLegs >= numItems)&&(numArms >= numItems)
         &&(numSeats >= numItems)&&(numCushions >= numItems)) {
            return true;
        }
        return false;
    }

    private boolean checkDeskSet(Desk[] subset){ 
        int numLegs = 0;
        int numTops = 0;
        int numDrawers = 0;
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
        if((numLegs >= numItems)&&(numTops >= numItems)
         &&(numDrawers >= numItems)) {
            return true;
        }
        return false;
    }

    private boolean checkFilingSet(Filing[] subset){ 
        int numRails = 0;
        int numDrawers = 0;
        int numCabinets = 0;
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
        if((numRails >= numItems)&&(numDrawers >= numItems)
         &&(numCabinets >= numItems)) {
            return true;
        }
        return false;
    }

    private boolean checkLampSet(Lamp[] subset){ 
        int numBases = 0;
        int numBulbs = 0;
        for(int i = 0; i < subset.length; i++) {
            if(subset[i].getBase() == 'Y') {
                numBases++;
            }
            if(subset[i].getBulb() == 'Y') {
                numBulbs++;
            }
        }
        if((numBases >= numItems)&&(numBulbs >= numItems)) {
            return true;
        }
        return false;
    }

    // selectBestOrder will search for the cheapest order from allOrders,
    // update bestOrder, and will change orderFound to true if allOrders is not empty
    private void selectBestOrder() { 
        if(allOrders.size() == 0) {
            return;
        }

        this.orderFound = true;

        int cheapestIndex = 0;
        for(int i = 1; i < allOrders.size(); i++) {
            if (allOrders.get(i).getCost() < allOrders.get(cheapestIndex).getCost()) {
                cheapestIndex = i;
            }
        }

        this.bestOrder = allOrders.get(cheapestIndex);
    }
}