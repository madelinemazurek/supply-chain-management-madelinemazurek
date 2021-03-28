package edu.ucalgary.ensf409;

public class Order{
    private Chair[] chairs;
    private Desk[] desks;
    private Filing[] filingCabinets;
    private Lamp[] lamps;
    private boolean isChair = false;
    private boolean isDesk = false;
    private boolean isFiling = false;
    private boolean isLamp = false;
    private int cost = 0;

    //initialize Chair array and indicate the furniture type. Calc cost
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
    }

    public int getCost(){
        return this.cost;
    }

    public void calculateCost(){
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
    }
}