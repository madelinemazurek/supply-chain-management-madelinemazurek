/**
    @author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
    @author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
    @author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
    @author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
    @version    1.6
    @since      1.0
 */
package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.*;
/**
 * DatabaseAccess class has 5 fields all used for database access and manipulation of sql files. 
 * It has a variety of methods which will be used to pass relevant information on specific tables in 
 * the database to calling functions. This class also contains a method to delete values from tables in
 * the database.
 */
public class DatabaseAccess{
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;
    private Connection dbConnect;
    private ResultSet results;
    /**
     * DatabaseAccess constructor. Takes in parameters for three fields and initializes
     * the member variables. 
     * @param dburl
     * @param username
     * @param password
     */
    public DatabaseAccess(String dburl, String username, String password){
        this.DBURL = dburl;
        this.USERNAME = username;
        this.PASSWORD = password;
    }
    /**
     * Getter for DBURL
     * @return A string for the DBURL member
     */
    public String getDburl(){
        return this.DBURL;
    }
    /**
     *  intitalizeConnection is a method that should initialize the Connection member variable dbConnect.
     */
    public void initializeConnection(){
        try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e){
            System.err.println("Could not connect to the database");
        }
    }
    /**
     * fetchTables is a method that will get all table names from the database in an sql file, 
     * and return them in an ArrayList of type String.
     * @return ArrayList of type String containing table names.
     */
    public ArrayList<String> fetchTables(){
        ArrayList <String> tableString = new ArrayList<String>();
        try{
            String query = "SHOW TABLES";
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            while(results.next()){
                tableString.add(results.getString(1));
            }
            stmt.close();
        } catch (SQLException e){
            System.err.print("Could not execute query.");
        }
        return tableString;
    }
    /**
     * fetchColumns is a method that recieves one argument, this being the table name and returns an ArrayList
     * of type string containing the names of all columns in a table found in a database.
     * @param table
     * @return ArrayList of type string containing the names of all columns in a table found in a database.
     */
    public ArrayList<String> fetchColumns(String table){
        ArrayList <String> typeString = new ArrayList<String>();
        try{
            String query = "SELECT * FROM " + table;
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            ResultSetMetaData resultMD = results.getMetaData();
            int columns = resultMD.getColumnCount();
            for (int j =1; j<columns+1;j++){
                typeString.add(resultMD.getColumnName(j));
            }
            stmt.close();
        } catch(SQLException e){
            System.err.println("Could not execute query");
        }
        return typeString;
    }
    /**
     * close is a method that closes the Connection field dbConnect, and ResultSet results.
     */
    public void close(){
        try{
            results.close();
            dbConnect.close();
        }
        catch (SQLException e){
            System.err.println("Could not fully close");
        }
    }
    /**
     * fetchTypes is a method that recieves one argument a String representing the table name 
     * and returns an ArrayList of type string containg each unique type found in a table within the database
     * @param table
     * @return ArrayList of type string containg each unique type found in a table within the database
     */
    public ArrayList<String> fetchTypes(String table){
        ArrayList <String> typeString = new ArrayList<String>();
        try{
            String query = "SELECT * FROM " + table;
            Statement stmt = dbConnect.createStatement();
            results= stmt.executeQuery(query);
            while(results.next()){
                if(!typeString.contains(results.getString("Type"))){
                    typeString.add(results.getString("Type"));
                }
            }
            stmt.close();
        } catch(SQLException e){
            System.out.println("Could not execute query");
        }
        return typeString;
    }
    /**
     * fetchManufacturerName is a method that recieves one argument a String representing a table name,
     * and returns an ArrayList of type String containing each unique manufacturer name found within a table in the database.
     * @param table
     * @return ArrayList of type String containing each unique manufacturer name found within a table in the database.
     */
    public ArrayList<String> fetchManufacturerName(String table){
        ArrayList <String> manufacturerID = new ArrayList<String>();
        try{
            String query = "SELECT * FROM " + table;
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            while(results.next()){
                if(!manufacturerID.contains(results.getString("ManuID"))){
                    manufacturerID.add(results.getString("ManuID"));
                }
            }
            stmt.close();
        } catch(SQLException e){
            System.out.println("Could not execute query");
        }
        ArrayList<String> manufacturerName = new ArrayList<String>();
        try{
            String query = "SELECT * FROM MANUFACTURER";
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            while(results.next()){
                if(manufacturerID.contains(results.getString("ManuID") )){
                    manufacturerName.add(results.getString("Name"));
                }
            }
            stmt.close();
        } catch(SQLException e){
            System.out.println("Could not execute query");
        }
        return manufacturerName;
    }
    /**
     * fetchSpecificType is a method that recieves two arguments one string representing a table name and one representing a type name.
     * The method will then find every row in the specified table within the database containing the type specified in the arguments. 
     * These rows will then be returned in the form of an ArrayList of type String.
     * @param table
     * @param type
     * @return ArrayList of type string containing valid rows with a type matching argument type.
     */
    public ArrayList<String> fetchSpecificType(String table, String type){
        ArrayList <String> columns = fetchColumns(table);
        ArrayList<String> validArray = new ArrayList<String>();
        try{
        String query = "SELECT * FROM " +table;
        Statement stmt = dbConnect.createStatement();
        results = stmt.executeQuery(query);
        while(results.next()){
            if(results.getString("Type").equals(type)){
            String temp ="";
            for(int i = 0; i<columns.size();i++){
                if(i<columns.size()-1){
                    temp += results.getString(columns.get(i))+"/";
                }
                else{
                    temp+= results.getString(columns.get(i));
                }
            }
            validArray.add(temp);
        }
        }
        stmt.close();
        } catch(SQLException e){
            System.err.println("Could not execute query");
        }
        return validArray;
    }
    /**
     * Method that takes in two arguments a String representing a table name and a String representing a type within the table.
     * The method will construct a generic array based on the two arguments and return it. 
     * This generic array will be generated by typecasting specific objects(i.e Chair, Desk, Lamp, Filing). 
     * @param <T>
     * @param table
     * @param type
     * @return Generic array of objects 
     */
    public <T> T[] objectConstructor(String table, String type){
        ArrayList<String> validRows = fetchSpecificType(table, type);
        if(table.equals("chair")){
            Chair[] returnChair = new Chair[validRows.size()];
            for(int i = 0; i<validRows.size();i++){
                String temp = validRows.get(i);
                String[] split = temp.split("/");
                String id = split[0];
                String Ctype = split[1];
                char legs = split[2].charAt(0);
                char arms = split[3].charAt(0);
                char seat = split[4].charAt(0);
                char cushion = split[5].charAt(0);
                int price = Integer.parseInt(split[6]);
                String manuID = split[7];
                returnChair[i] = new Chair(id, Ctype, legs, arms, seat, cushion, price, manuID);
            }
            return (T[]) returnChair;
        }
        else if(table.equals("desk")){
            Desk[] returnDesk = new Desk[validRows.size()];
            for(int i = 0; i<validRows.size();i++){
                String temp = validRows.get(i);
                String[] split = temp.split("/");
                String id = split[0];
                String Ctype = split[1];
                char legs = split[2].charAt(0);
                char top = split[3].charAt(0);
                char drawer = split[4].charAt(0);
                int price = Integer.parseInt(split[5]);
                String manuID = split[6];
                returnDesk[i] = new Desk(id, Ctype, legs, top, drawer, price, manuID);
            }
            return (T[]) returnDesk;
        }
        else if(table.equals("filing")){
            Filing[] returnFiling = new Filing[validRows.size()];
            for(int i = 0; i<validRows.size();i++){
                String temp = validRows.get(i);
                String[] split = temp.split("/");
                String id = split[0];
                String Ctype = split[1];
                char rails = split[2].charAt(0);
                char drawers = split[3].charAt(0);
                char cabinet = split[4].charAt(0);
                int price = Integer.parseInt(split[5]);
                String manuID = split[6];
                returnFiling[i] = new Filing(id, Ctype, rails, drawers, cabinet, price, manuID);
            }
            return (T[]) returnFiling;
        }
        else if(table.equals("lamp")){
            Lamp[] returnLamp = new Lamp[validRows.size()];
            for(int i = 0; i<validRows.size();i++){
                String temp = validRows.get(i);
                String[] split = temp.split("/");
                String id = split[0];
                String Ctype = split[1];
                char base = split[2].charAt(0);
                char bulb = split[3].charAt(0);
                int price = Integer.parseInt(split[4]);
                String manuID = split[5];
                returnLamp[i] = new Lamp(id, Ctype, base, bulb, price, manuID);
            }
            return (T[]) returnLamp;
        }
        else{
            T[] empty = null;
            return empty;
        }
    }
    /**
     * Takes in two arguments one String titled table and one String array titled id.
     * This function will then delete every row of the table within the database containing an id found within the String[] id.
     * @param table
     * @param id
     */
    public void deleteFromTable(String table, String[] id){
        for (int i =0; i<id.length;i++){
            try{
            String query = "DELETE FROM  " + table + " WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setString(1,id[i]);
            myStmt.executeUpdate();
            myStmt.close();
            } catch(SQLException e){
                System.err.println("Could not execute query");
            }
        }  
    }
}

