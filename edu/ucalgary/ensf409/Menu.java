//to do:
// make it so navigation doesn't delete  previous entries
// make it so can check over entries before submiting form request

//*********************** for category check manufacturer is valid input --> should not be!!!!!!!!!!!!!!!!1

package edu.ucalgary.ensf409;

import java.util.Scanner;
import java.util.ArrayList;

public class Menu {

    private String category;
    private String type;
    private String numberOfItems;

    private DatabaseAccess databaseObj;
    private SearchInventory searchInventoryObj;
    private WriteText writeTextObj;

    /**
     * Default constructor that will initialize dataBaseObj and call printMenu() to start the user input process which will initialize the other data members
     */
    public Menu(){
        this.databaseObj = new DatabaseAccess("C:/Users/jaras/Desktop/ENSF409/Hackathon","scm","ensf409");
        printMenu();
    }

    /**
     * 
     * @return the category of furniture
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * 
     * @return the type of furniture
     */
    public String getType(){
        return this.type;
    }

    /**
     * 
     * @return the amount of furniture items wanted
     */
    public String getNumberOfItems(){
        return this.numberOfItems;
    }
    
    /**
     * Prints the menu to the terminal and prompts users for input to initialize the category, type, and numberOfItems data members.
     * Valid input is checked for with a call to checkNotValidInput.
     * Has added fucntionality to allow users to quit the process at any time or navigate back up the menu to change a previous entry.
     * Once the user input has been read in for category, type, and numberOfItems, obtainFinalMessage() is called bdefore the method finishes
     */
    private void printMenu(){

        Scanner inputObj = new Scanner(System.in);

        boolean notValidInput = true;
        boolean quitControl = true;
        int menuControl = 1;

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.print("Please fill out the following item information to generate an order form: ");


        //use quitControl to read in if quit
        //use menuControl to go back
        while(quitControl){
            switch(menuControl){

                //case 1 is used to obtain user input to initialize category
                case 1:
                    while(notValidInput){
                        System.out.print("\n1) Enter the furniture category from the following options (or input q to quit): ");

                         //print out options available
                         ArrayList<String> catOptions = databaseObj.fetchTables();
                         int i = 0;
                         for(i = 0; i < catOptions.size()-1;i++){
                             System.out.print(catOptions.get(i)+", ");
                         }
                         System.out.print(catOptions.get(i));

                         //read in user input
                        this.category = inputObj.nextLine();

                        //check if user inputted quit key
                        if(category.equals("q")){
                            quitControl = false;
                            break;
                        }
                        //check is input is valid
                        notValidInput = checkNotValidInput(1,category);
                    }
                    notValidInput=true;
                    menuControl=2;
                    break;
                
                //case 2 is used to obtain user input to initialize type
                case 2:    
                    while(notValidInput){
                        System.out.println("Input ^ to navigate back to category entry if you need to change your category input. Otherwise,");
                        System.out.println("2) Enter the furniture type from the following options (or input q to quit): ");

                        //print out options available
                        ArrayList<String> typeOptions = databaseObj.fetchTypes(category);
                        int i = 0;
                        for(i = 0; i < typeOptions.size()-1;i++){
                            System.out.print(typeOptions.get(i)+", ");
                        }
                        System.out.print(typeOptions.get(i));
                        
                        //read in user input
                        this.type = inputObj.nextLine();

                        //check if user inputted quit key or navigate up key
                        if(category.equals("^")){
                            menuControl = 1;
                            break;
                        }
                        if(category.equals("q")){
                            quitControl = false;
                            break;
                        }

                        //check if input is valid
                        notValidInput = checkNotValidInput(2,type);
                    }
                    notValidInput = true;
                    menuControl = 3;
                    break;

                //case 3 is used to obtain user input to initialize numberOfItems    
                case 3:    
                    while(notValidInput){
                        System.out.println("Input ^ to navigate back to type entry if you need to change your type input. Otherwise,");
                        System.out.print("\n3) Enter the amount of items (or input q to quit): ");

                        //read in user input
                        this.numberOfItems = inputObj.nextLine();

                        //check if user inputted quit key or navigate up key
                        if(numberOfItems.equals("^")){
                            menuControl = 2;
                            break;
                        }
                        if(category.equals("q")){
                            quitControl = false;
                            break;
                        }

                        //check if input is valid
                        notValidInput = checkNotValidInput(3,numberOfItems);
                    }
                    menuControl = 4;
                    break;
                
                //case 4 is used once category, type, and numberOfItems have been initialized to call obtainOutputMessage and then finish the program
                case 4:
                    obtainOutputMessage();
                    quitControl = false;
                    break;
            }
        }
        
    }

    //check against dataBase to see if type and category are there

    /**
     * 
     * @param i specifies what check to carry out on input
     * @param input is the String read in from the user input.
     * @return false if input is valid and true if input is not valid.
     * 
     * To check valid input for category a 1 is passed for i. 
     * input is then compared to the table names in the database and if a match is found the input is considered valid.
     * 
     * To check valid input for type a 2 is passed for i.
     * input is then compared to the names of the type column in the table that has previously been initialized in printMenu() (cannot get to this point without having already
     * provided a proper category/table name). If a match is found the input is considered valid.
     * 
     * To check valid input for numberOfItems a 3 is passed for i.
     * input is then converted to an int and as long as it converts to an int that is greater than 0 it is valid. If it cannot be converted the input is considered invalid.
     */
    private boolean checkNotValidInput(int i,String input){

        switch(i){

            //check if category inputted is valid by comparing it to a list of table names in the database 
            case 1: 
                ArrayList<String> categories = databaseObj.fetchTables();
                for(int j = 0; j < categories.size();j++){
                    if(input.equals(categories.get(j))){
                        return false;
                    }
                }
                System.out.println("Please input a valid category.");
                return true;

            // will only get here if category entered is correct so already know what table we are in -> use this info to check if valid type for the category    
            case 2:
                ArrayList<String> types = databaseObj.fetchTypes(category);
                for(int j = 0; j < types.size();j++){
                    if(input.equals(types.get(j))){
                        return false;
                    }
                }
                System.out.println("Please input a valid type.");
                return true;

            case 3:
                try{
                    int inputAmount = Integer.parseInt(input);
                    if(inputAmount<1){
                        System.out.println("Please enter a valid number of items to be ordered.");
                        return true;
                    }
                    return false;
                    break;

                }catch(NumberFormatException e){
                    System.out.println("Please enter a valid number of items to be ordered.");
                    return true;
                }
        }

    }

    //should tell if could create file or not
    /**
     * 
     */
    private void obtainOutputMessage(){
        this.searchInventoryObj = new SearchInventory(category,type,Integer.parseInt(numberOfItems),databaseObj);
            //call method here to search the database for the furniture-> should return true if could generate furniture and false if request is impossible
            //use getter to see if request failed or not a call a file depending off it fails
    }

}
