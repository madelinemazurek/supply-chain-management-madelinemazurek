/**
*@author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
*@author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
*@author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
*@author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
*@version    1.1
*@since      1.0
*/

package edu.ucalgary.ensf409;
import java.io.*;

/**
 * WriteText creates an output file for the orderform that corresponds to
 * the furniture items that are being purchased. It uses the furniture
 * category, type, and number, as well as the furniture ID of the used items
 * being purchased, and the overall purchase price.
 */
public class WriteText{
    private BufferedWriter writer;
    private String fileName = "orderform.txt";  //hardcoding filename
    private String category;
    private String type;
    private String number;
    private String[] itemIDs;
    private String price;
    private String output;

    /**
     * WriteText is a constructor that instantiates a WriteText object with all
     *  of the information from the order input and the subsequent database searches
     *  that is needed to create the output orderform.
     * @param category The furniture category (chair, desk, lamp, etc).
     * @param type The type of furniture within the furniture category (mesh, ergonomic, etc).
     * @param number The number of furniture items being ordered.
     * @param itemIDs The ID number of the furniture items from their database table.
     * @param price The total price of all furniture items purchased.
     */
    public WriteText(String category, String type, String number, String[] itemIDs, String price){
        this.category = category;
        this.type = type;
        this.number = number;
        this.itemIDs = itemIDs;
        this.price = price;
    }

    /**
     * writeOutput is a method that instantiates a BufferedWriter object and
     *  writes a string that contains the order form to an external text file
     *  called "orderform.txt". writeOutput calls formatOutput in order to
     *  obtain the formatted order form string.
     */
    public void writeOutput(){
        File out;   //declare a file object
        try{
            //instantiate file object with fileName
            out = new File(fileName);
            //instantiate a BufferedWriter object using the File object
            writer = new BufferedWriter(new FileWriter(out));
        }
        catch(IOException e){
            System.out.println("Error opening output file.");
            e.printStackTrace();
        }
        //format the output string
        formatOutput();

        try{
            //write the output string to the file
            writer.write(this.output);
        }
        catch(IOException e){
            System.out.println("Unable to write to output file.");
            e.printStackTrace();
        }
        finally{
            try{
                //try to close the file if the file was opened
                if(writer != null){
                    writer.close();
                }
            }
            catch(IOException e){
                //if file cannot be closed, exit
                System.out.println("Error closing output file.");
                e.printStackTrace();
            }
        }

    }

    /**
     * formatOutput formats the output string with the information about the
     *  order which is passed into the constructor. It inserts newline characters
     *  and additional supporting text to create a string that represents the final
     *  order form.
     */
    private void formatOutput(){
        //format the string to contain the appropriate characters and newlines
        this.output = "";
        this.output = "Furniture Order Form\n";
        this.output += "\n";
        this.output += "Faculty Name:\n";
        this.output += "Contact:\n";
        this.output += "Date:\n";
        this.output += "\n";
        this.output +=  "Original Request: " + type + " " + category + ", " + number + "\n";
        this.output += "\n";
        this.output += "Items Ordered\n";
        //iterate through the itemIDs array and insert them into the string
        for(int i = 0; i < this.itemIDs.length; i++){
            this.output += "ID: ";
            this.output += this.itemIDs[i];
            this.output += "\n";
        }
        this.output += "\n";
        this.output += "Total Price: $" + price;
    }

}