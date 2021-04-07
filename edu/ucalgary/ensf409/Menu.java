/**
*@author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
*@author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
*@author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
*@author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
*@version    1.4
*@since      1.0
*/

package edu.ucalgary.ensf409;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Class menu provides a command line user interface that permits a user to submit a request for
 *  a desired order to the system. Contains the main which facilitates the functionality of the
 *  program, produces the correct order form for the user or provides useful feedback if the order
 *  cannot be fulfilled. Updates the database if an order is completed.
 */
public class Menu {

    private String category; //the requested FurnitureItem category for the order
    private String type; //the requested type of the specific furniture item for the order
    private String numberOfItems; //the total number of items requested for the order

    //a DatabaseAccess object that provides the connection and interfacing with the inventory database
    private DatabaseAccess databaseObj; 
    //a SearchInventory object which facilitates the programs searching and retrieval of information
    //from the database to complete the order
    private SearchInventory searchInventoryObj;
    //a WriteText object that creates an orderform document for a completed order
    private WriteText writeTextObj; 

    /**
     * Main method creates a Menu object, which initializes the connection to the database and launches
     *  the command line user interface.
     * @param args Unused initial command line string arguments.
     */
    public static void main(String args[]) {
        Menu menu = new Menu();
    }

    /**
     * Default Menu constructor that will initialize the database connection and call printMenu()
     *  to launch the command line interface and start the user input process.
     */
    public Menu() {
        //default inputs are used for the DatabaseAccess fields as seen below
        this.databaseObj = new DatabaseAccess("jdbc:mysql://localhost/inventory","madeline","ensf409");
        databaseObj.initializeConnection();
        //begin user interfacing
        printMenu();
        //terminate program
        databaseObj.close();
    }

    /**
     * Getter for Category.
     * @return The requested FurnitureItem category for the order.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Getter for Type.
     * @return The requested type of the specific furniture item for the order.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Getter for NumberOfItems.
     * @return The total number of items requested for the order.
     */
    public String getNumberOfItems() {
        return this.numberOfItems;
    }
    
    /**
     * Method printMenu prints the menu to the terminal and prompts users for input to initialize the
     *  category, type, and numberOfItems data members. Valid input is checked for with a call to
     *  checkNotValidInput. Has added fucntionality to allow users to quit the process at any time or
     *  navigate back up the menu to change a previous entry. Once the user input has been read in for
     *  category, type, and numberOfItems, obtainFinalMessage() is called bdefore the method finishes.
     */
    private void printMenu() {
        //scanner used to read user input
        Scanner inputObj = new Scanner(System.in);

        boolean notValidInput = true; //boolean updated if a vailid iinput option is read
        boolean quitControl = true; //boolean updated when user requests to quit
        int menuControl = 1; //int used to navigate between different menu pages

        System.out.println("------------------------------------------------------------------------"
                         + "-----------------");
        System.out.print("Please fill out the following item information to generate an order form: ");

        //use quitControl to read and check for a quit
        //use menuControl to go back
        while(quitControl) {
            switch(menuControl) {
                //case 1 is used to obtain user input to initialize category
                case 1:
                    while(notValidInput) {
                        System.out.println("\n1) Enter the furniture category from the following " 
                                         + "options (or input q to quit): ");

                        //print out category options currently available
                        ArrayList<String> catOptions = databaseObj.fetchTables();
                        int i = 0;
                        for(i = 0; i < catOptions.size() - 2; i++) {
                            //exclude manufacturer, it is not a valid choice
                            if(!catOptions.get(i).equals("manufacturer")) {
                                System.out.print(catOptions.get(i)+", ");
                            }
                        }

                        //exclude manufacturer, it is not a valid choice
                        if(!catOptions.get(i).equals("manufacturer")){
                            System.out.print(catOptions.get(i) + ": ");
                        }
                        else {
                            System.out.print(": ");
                        }

                        //read in user input
                        this.category = inputObj.nextLine();
                        this.category = this.category.trim();
                        this.category = this.category.toLowerCase();

                        //check if user inputted quit key, quit program
                        if(category.equals("q")) {
                            quitControl = false;
                            break;
                        }

                        //check if user inputted manufacturer beacuse it is listed in the database
                        //but is not a valid category
                        if(category.equals("manufacturer")) {
                            quitControl = false;
                            break;
                        }

                        //check if input is valid
                        notValidInput = checkNotValidInput(1, category);
                    }
                    //valid input has been found, move onto next screen
                    if (!category.equals("q")) {
                        System.out.println("Your chosen category is: " + category);
                        notValidInput = true;
                        menuControl = 2;    
                    } 
                    break;
                
                //case 2 is used to obtain user input, and to initialize type
                case 2:    
                    boolean goBack = false; //boolean used to check if user requests to go back
                    while(notValidInput) {
                        System.out.println("\nInput ^ to navigate back to category entry if you need"
                                         + " to change your category input. Otherwise,");
                        System.out.println("2) Enter the furniture type from the following options"
                                         + " (or input q to quit): ");

                        //print out type options available for selected category
                        ArrayList<String> typeOptions = databaseObj.fetchTypes(category);
                        int i = 0;
                        for(i = 0; i < typeOptions.size() - 1; i++) {
                            System.out.print(typeOptions.get(i)+", ");
                        }
                        System.out.print(typeOptions.get(i) + ": ");
                        
                        //read in user input
                        this.type = inputObj.nextLine();
                        this.type = this.type.trim();
                        this.type = this.type.toLowerCase();

                        //check if user inputted quit key or navigate up key
                        if(type.equals("^")) {
                            goBack = true;
                            break;
                        }
                        if(type.equals("q")) {
                            quitControl = false;
                            break;
                        }

                        //check if user input is valid
                        notValidInput = checkNotValidInput(2, type);
                    }
                    //valid input has been found, move onto next or previous screen
                    notValidInput = true;
                    if(goBack) {
                        menuControl = 1;
                    } else if (!type.equals("q")) {
                        String tempType = type.substring(0, 1).toUpperCase();
                        tempType += type.substring(1, type.length());
                        System.out.println("Your chosen type is: " + tempType);
                        menuControl = 3;
                    }
                    break;

                //case 3 is used to obtain user input to initialize numberOfItems    
                case 3:
                    boolean goB = false; //boolean used to check if user requests to go back
                    while(notValidInput){
                        System.out.println("\nInput ^ to navigate back to type entry if you need"
                                         + " to change your type input. Otherwise,");
                        System.out.println("3) Enter the amount of items (or input q to quit): ");

                        //read in user input
                        this.numberOfItems = inputObj.nextLine();
                        this.numberOfItems = this.numberOfItems.trim();
                        this.numberOfItems = this.numberOfItems.toLowerCase();

                        //check if user inputted quit key or navigate up key
                        if(numberOfItems.equals("^")) {
                            goB = true;
                            break;
                        }
                        if(numberOfItems.equals("q")) {
                            quitControl = false;
                            break;
                        }

                        //check if user input is valid
                        notValidInput = checkNotValidInput(3, numberOfItems);
                    }
                    //valid input has been found, move onto next or previous screen
                    notValidInput = true;
                    if(goB) {
                        menuControl = 2;
                    } else if (!numberOfItems.equals("q")) {
                        System.out.println("The number of items selected is: " + numberOfItems);
                        menuControl = 4;
                    }
                    break;
                
                //case 4 is used once category, type, and numberOfItems have been initialized to call
                //obtainOutputMessage and then finish the program
                case 4:
                    obtainOutputMessage();
                    quitControl = false;
                    break;
            }
        }
        //user interfacing is complete, close the scanner
        inputObj.close();
    }

     /**
     * Method checkNotValidInput verifies whether or not a valid input has been provided by the user,
     *  compares against the current status of the database. Checks for different cases depending on which
     *  page the menu is currently on.
     *  To check valid input for category, a 1 is passed for i. Input is then compared to the table names
     *  in the database and if a match is found the input is considered valid.
     *  To check valid input for type a 2 is passed for i. Input is then compared to the names of the type
     *  column in the table that has previously been initialized in printMenu() (cannot get to this point
     *  without having already provided a proper category/table name). If a match is found the input is
     *  considered valid.
     *  To check valid input for numberOfItems a 3 is passed for i. Input is then converted to an int, which
     *  must be greater than zero for it to be considered valid. If it cannot be converted, the input is
     *  considered invalid.
      * @param i Int input field that specifies what check to carry out on user input.
      * @param input The String read in from the user input scanner.
      * @return True if a vailid input was found, false otherwise.
      */
    private boolean checkNotValidInput(int i, String input) {
        switch(i) {
            //check if category inputted is valid by comparing it to a list of table names in the database 
            case 1: 
                //find all category options currently available
                ArrayList<String> categories = databaseObj.fetchTables();
                for(int j = 0; j < categories.size(); j++) {
                    if(input.equals(categories.get(j))) {
                        return false;
                    }
                }
                //valid category was not found
                System.out.println("Please input a valid category.");
                return true;

            //will only get here if category entered is correct so we already know what table we are in.
            //Use this info to check if a valid type has been entered for the category
            case 2:
                //find all type options currently available in table
                ArrayList<String> types = databaseObj.fetchTypes(category);
                for(int j = 0; j < types.size(); j++) {
                    if(input.equals(types.get(j).toLowerCase())) {
                        return false;
                    }
                }
                //valid type was not found
                System.out.println("Please input a valid type.");
                return true;

            //check if number of items inputted is valid positive integer
            case 3:
                try {
                    int inputAmount = Integer.parseInt(input);
                    if(inputAmount < 1) {
                        System.out.println("Please enter a valid number of items to be ordered.");
                        return true;
                    }
                    return false;
                } catch(NumberFormatException e) {
                    System.out.println("Please enter a valid number of items to be ordered.");
                    return true;
                }
            
            //invalid input for i
            default:
                return false;
        }
    }

    /**
     * Method obtainOutputMessage will run a search on the current state of the inventory to see if
     *  the requested order can be fulfilled. 
     */
    private void obtainOutputMessage() {
        //initialzing the searchInventory will run the search and store the required information
        //inside the searchInventoryObj fields
        this.searchInventoryObj = new SearchInventory(category, type, Integer.parseInt(numberOfItems), 
                                                      databaseObj);
        //if an order is possible based on current inventory orderFound will be true
        if(!searchInventoryObj.getOrderFound()) {
                
            //if here it means that the request could not be fulfilled so print out a message and return
            System.out.print("\nOrder cannot be fulfilled based on current inventory. Suggested"
                             + " manufacturers are ");
                
            //print out the suggested manufacturers that match the given category
            ArrayList<String> manuIdArray = databaseObj.fetchManufacturerName(category);
            int j = 0;
            for(j = 0; j < manuIdArray.size() - 1; j++) {
                if(j == manuIdArray.size() - 2) {
                    //at second last id
                    System.out.print(manuIdArray.get(j) + ", and ");
                }
                else {
                    System.out.print(manuIdArray.get(j) + ", ");
                }
            }
            //at last index of manuIdArray
            System.out.print(manuIdArray.get(j) + ".");
        }
        else {
            //if here it means that the request could be fulfilled so program will write a text file and
            //will print what item IDs to order, as well as the price of the order
            writeTextObj = new WriteText(category, type, numberOfItems,
                                         searchInventoryObj.getBestOrder().getIDs(),
                                         String.valueOf(searchInventoryObj.getBestOrder().getCost()));
            writeTextObj.writeOutput();

            //delete the items from the inventory --> pass table name and IDs to databaseObj
            databaseObj.deleteFromTable(category, searchInventoryObj.getBestOrder().getIDs());

            //print out IDs of order and the price
            String[] idArray = searchInventoryObj.getBestOrder().getIDs();
            int i = 0;
            System.out.println("Purchase ");

            for(i = 0; i < idArray.length - 1; i++) {
                if(i == idArray.length - 2) {
                    //at second last id
                    System.out.print(idArray[i] + " and ");
                }
                else {
                    System.out.print(idArray[i] + ", ");
                }
            }
            //at last index of idArray
            System.out.print(idArray[i] + " for $" + searchInventoryObj.getBestOrder().getCost() + ".");
        }
    }
}