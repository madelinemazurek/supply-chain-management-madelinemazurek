package edu.ucalgary.ensf409;
import java.util.*;

public class SearchInventory <T> {
    private String furnitureCategory; //table to be searched
    private int numItems; //number of items that the order must fulfill

    private ArrayList<T> items; //array of all items of model type in inventory
    private ArrayList <Order> allOrders; //all possible orders that satisfy requirements
    private Order bestOrder; //the cheapest order that satisfies the requirements
    private boolean orderFound = false; // true if an order can be fulfilled, false otherwise

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

    public Order getBestOrder() {
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
    //calls check_____Set
    //if check______Set returns true, create order for set and add order to allOrders
    private <T> void checkValidSet(ArrayList<T> subset) {
        if(furnitureCategory.toLowerCase().equals("chair")) {
            if(checkChairSet((Chair[])subset.toArray()) == true) {
                Order theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("desk")) {
            if(checkDeskSet((Desk[])subset.toArray()) == true) {
                Order theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("filing")) {
            if(checkFilingSet((Filing[])subset.toArray()) == true) {
                Order theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        } else if(furnitureCategory.toLowerCase().equals("lamp")) {
            if(checkLampSet((Lamp[])subset.toArray()) == true) {
                Order theOrder = new Order(subset);
                allOrders.add(theOrder);
            }
        }
    }

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