//to do:
// make it so navigation doesn't delete  previous entries
// make it so can check over entries before submiting form request

package edu.ucalgary.ensf409;

import java.util.Scanner;

public class Menu {

    private String category;
    private String type;
    private String numberOfItems;

    private DataBaseAccess dataBaseObj;
    private CreateFurniture furnitureCreationObj;
    private WriteText writeTextObj;

    /**
     * Default constructor that will initialize dataBaseObj and call printMenu() to start the user input process which will initialize the other data members
     */
    public Menu(){
        this.dataBaseObj = new dataBaseObj("C:/Users/jaras/Desktop/ENSF409/Hackathon","JaredA","Blackfoot69.");
        printMenu();
    }

    /**
     * 
     * @return the category of furniture
     */
    public String getCategory(){
        return this. category;
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
     * Prints the menu to the terminal and prompts users for input to initialize the category, type, and numberOfItems data members
     * Has added fucntionality to allow users to quit the process at any time or navigate back up the menu to change a previous entry.
     */
    public void printMenu(){

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

                case 1:
                    while(notValidInput){
                        System.out.print("\n1) Enter the furniture category from the following options (or input q to quit): ");

                         //print out options available
                         String[] catOptions = dataBaseObj.fetchTables();
                         int i = 0;
                         for(i = 0; i < catOptions.length-1;i++){
                             System.out.print(catOptions[i]+", ");
                         }
                         System.out.print(catOptions[i]);

                        this.category = inputObj.nextLine();
                        if(category = "q"){
                            quitControl = false;
                            break;
                        }
                        notValidInput = checkNotValidInput(1,category);
                    }
                    notValidInput=true;
                    menuControl=2;
                    break;

                case 2:    
                    while(notValidInput){
                        System.out.println("Input ^ to navigate back to category entry if you need to change your category input. Otherwise,");
                        System.out.println("2) Enter the furniture type from the following options (or input q to quit): ");

                        //print out options available
                        String[] typeOptions = dataBaseObj.fetchTypes(category);
                        int i = 0;
                        for(i = 0; i < typeOptions.length-1;i++){
                            System.out.print(typeOptions[i]+", ");
                        }
                        System.out.print(typeOptions[i]);
        
                        this.type = inputObj.nextLine();
                        if(category = "^"){
                            menuControl = 1;
                            break;
                        }
                        if(category = "q"){
                            quitControl = false;
                            break;
                        }
                        notValidInput = checkNotValidInput(2,type);
                    }
                    notValidInput = true;
                    menuControl = 3;
                    break;

                case 3:    
                    while(notValidInput){
                        System.out.println("Input ^ to navigate back to type entry if you need to change your type input. Otherwise,");
                        System.out.print("\n3) Enter the amount of items (or input q to quit): ");

                        this.numberOfItems = inputObj.nextLine();

                        if(numberOfItems = "^"){
                            menuControl = 2;
                            break;
                        }
                        if(category = "q"){
                            quitControl = false;
                            break;
                        }
                        notValidInput = checkNotValidInput(3,numberOfItems);
                    }
                    menuControl = 4;
                    break;
                
                case 4:
                    obtainOutputMessage();
                    quitControl = false;
                    break;
            }
        }
        
    }

    //check against dataBase to see if type and category are there
    private boolean checkNotValidInput(int i,String input){

        switch(i){

            //check if category inputted is valid by comparing it to a list of table names in the database 
            case 1: 
                String[] categories = dataBaseObj.fetchTables();
                for(int j = 0; j < categories.length;j++){
                    if(input = categories[j]){
                        return false;
                    }
                }
                System.out.println("Please input a valid category.");
                return true;

            // will only get here if category entered is correct so already know what table we are in -> use this info to check if valid type for the category    
            case 2:
                String[] types = dataBaseObj.fetchTypes(category);
                for(int j = 0; j < types.length;j++){
                    if(input = types[j]){
                        return false;
                    }
                }
                System.out.println("Please input a valid type.");
                return true;

            case 3:
                int inputAmount = Integer.ParseInt(input);
                if(inputAmount<1){
                    System.out.println("Please enter a valid number of items to be ordered.");
                    return true;
                }
                return false;
                break;
        }

    }

    //should tell if could create file or not
    /**
     * 
     */
    private void obtainOutputMessage(){
        this.furnitureCreationObj = new CreateFurniture(category,type,Integer.parseInt(numberOfItems));
        
    }

}
