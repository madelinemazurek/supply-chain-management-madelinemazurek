package edu.ucalgary.ensf409;
import java.io.*;

public class WriteText{
    private BufferedWriter writer;
    private String fileName = "orderform.txt";
    private String[] itemIDs;
    private String price;
    private boolean canComplete;
    private String output;

    public WriteText(String[] itemIDs, String price, boolean canComplete){
        this.itemIDs = itemIDs;
        this.price = price;
        this.canComplete = canComplete;
    }

    private void writeOutput(){
        File out;
        try{
            out = new File(fileName);
            writer = new BufferedWriter(new FileWriter(out));
        }
        catch(IOException e){
            System.out.println("Error opening output file.");
            e.printStackTrace();
        }
        if(canComplete){
            formatOutputComplete();
        }
    }

    private void formatOutputComplete(){
        output = "Furniture Order Form";
    }

}