package edu.ucalgary.ensf409;
import java.util.*;

public class SearchInventory {
    private String furnitureCategory; //table to be searched
    private String model; //type of item (ex: kneeling chair)
    private int numItems; //number of items that the order must fulfill

    //private (some furniture item)[] items; //array of all items of model type in inventory
    private ArrayList <Order> allOrders; //all possible orders that satisfy requirements
    private Order bestOrder; //the cheapest order that satisfies the requirements
    private boolean orderFound = false; // true if an order can be fulfilled, false otherwise

    public SearchInventory (String furnitureCategory, String model, int numItems, DatabaseAccess db) {
        this.furnitureCategory = furnitureCategory;
        this.model = model;
        this.numItems = numItems;

        //call databaseAccess method to initialize items array
        //items = db.objectConstructor(String furnitureCategory, String model);

        //generateAllSets()
        //selectBestOrder()
    }

    //public void generateAllSets()

        //applicableTableRows is what is returned from databaseAccess class and contains the reduced table which only includes the rows with the right type
        private <T> void generateAllSets(){

            //n is the total number of subsets (2^n)
            int n = (int) Math.pow(2,items.length);
    
            //create all subsets 
            for(int i = 1; i < n; i++){
                
                ArrayList<T> subset= new ArrayList<>();
    
                for(int j = 0; j < items.length;j++){
                    //if the jth bit of i is 1 we want to include that in the subset
                    if((i & (1<<j)) != 0){
                        subset.add(items[j]);
                    }
                }
                //check if if the subset generated is valid
                checkValidSet(subset);
            }
        }

    //runs through all possible sets (power set of items) calling checkValidSet() for each

    //method checkValidSet returns void
    //calls check_____Set
    //if check______Set returns true, create order for set and add order to allOrders

    //boolean checkChairSet( -set of type chair- ){ }
    //boolean checkDeskSet( -set of type chair- ){ }
    //boolean checkFilingSet( -set of type chair- ){ }
    //boolean checkLampSet( -set of type chair- ){ }

    // selectBestOrder will search for the cheapest order from allOrders,
    // update bestOrder, and will change orderFound to true if allOrders is not empty
    // void selectBestOrder() { }
}