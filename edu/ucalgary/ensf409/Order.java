package edu.ucalgary.ensf409;

import java.util.ArrayList;

public class Order <T> {
    /*private Chair[] chairs;
    private Desk[] desks;
    private Filing[] filingCabinets;
    private Lamp[] lamps;
    private boolean isChair = false;
    private boolean isDesk = false;
    private boolean isFiling = false;
    private boolean isLamp = false;
    private int cost = 0;*/

    private ArrayList<T> items;
    private int cost = 0;

    //initialize Chair array and indicate the furniture type. Calc cost
    public Order(ArrayList<T> items){
        this.items = items;
        calculateCost();
    }

    public int getCost(){
        return this.cost;
    }

    public String[] getIDs(){
        ArrayList<String> retString = new ArrayList<>();

        for(int i = 0; i < this.items.size(); i++) {
            retString.add(((FurnitureItem)this.items.get(i)).getID());
        }

        return (String[]) retString.toArray(new String[0]);
    }

    public void calculateCost(){
        for(int i = 0; i < this.items.size(); i++){
            this.cost += ((FurnitureItem)this.items.get(i)).getPrice();
        }
    }

    /*//initialize Chair array and indicate the furniture type. Calc cost
    public Order(Chair[] chairs){
        this.chairs = chairs;
        this.isChair = true;
        calculateCost();
    }
 
    //initialize Desk array and indicate the furniture type. Calc cost
    public Order(Desk[] desks){
        this.desks = desks;
        this.isDesk = true;
        calculateCost();
    }

    //initialize Filing array and indicate the furniture type. Calc cost
    public Order(Filing[] filingCabinets){
        this.filingCabinets = filingCabinets;
        this.isFiling = true;
        calculateCost();
    }

    //initialize Lamp array and indicate the furniture type. Calc cost
    public Order(Lamp[] lamps){
        this.lamps = lamps;
        this.isLamp = true;
        calculateCost();
    }*/

    // public boolean getIsChair(){
    //     return this.isChair;
    // }

    // public boolean getIsDesk(){
    //     return this.isDesk;
    // }

    // public boolean getIsFiling(){
    //     return this.isFiling;
    // }

    // public boolean getIsLamp(){
    //     return this.isLamp;
    // }

    // public Chair[] getChairs() {
    //     return this.chairs;
    // }

    // public Desk[] getDesks() {
    //     return this.desks;
    // }

    // public Filing[] getFilingCabinets() {
    //     return this.filingCabinets;
    // }

    // public Lamp[] getLamps() {
    //     return this.lamps;
    // }

    /*public String[] getIDs(){
        ArrayList<String> retString = new ArrayList<>();
        if(isChair){
            for(int i = 0; i < this.chairs.length; i++){
                retString.add(this.chairs[i].getID());
            }
        }
        else if(isDesk){
            for(int i = 0; i < this.desks.length; i++){
                retString.add(this.desks[i].getID());
            }
        }
        else if(isFiling){
            for(int i = 0; i < this.filingCabinets.length; i++){
                retString.add(this.filingCabinets[i].getID());
            }
        }
        else if(isLamp){
            for(int i = 0; i < this.lamps.length; i++){
                retString.add(this.lamps[i].getID());
            }
        }

        return (String[]) retString.toArray();
    }*/

    /*public void calculateCost(){
        //if furniture type is chair, add up cost of all items in list
        if(this.isChair){
            for(int i = 0; i < this.chairs.length; i++){
                this.cost += this.chairs[i].getPrice();
            }
        }
        //if furniture type is chair, add up cost of all items in list
        if(this.isDesk){
            for(int i = 0; i < this.desks.length; i++){
                this.cost += this.desks[i].getPrice();
            }
        }
        //if furniture type is chair, add up cost of all items in list
        if(this.isFiling){
            for(int i = 0; i < this.filingCabinets.length; i++){
                this.cost += this.filingCabinets[i].getPrice();
            }
        }
        //if furniture type is chair, add up cost of all items in list
        if(this.isLamp){
            for(int i = 0; i < this.lamps.length; i++){
                this.cost += this.lamps[i].getPrice();
            }
        }
    }*/
}