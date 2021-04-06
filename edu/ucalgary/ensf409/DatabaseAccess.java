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
 *  It has a variety of methods which will be used to pass relevant information from specified tables
 *  in the database to calling functions. This class also contains a method to delete values from tables
 *  in the database, once an order has been fulfilled.
 */
public class DatabaseAccess{
    public final String DBURL; //URL of the mySQL database holding the current inventory
    public final String USERNAME; //Username for the user accessing the inventory database
    public final String PASSWORD; //Password for the user accessing the database
    private Connection dbConnect; //Connection to the inventory database.
    private ResultSet results; //ResultSet used in various methods to retrieve database information.

    /**
     * DatabaseAccess constructor takes in parameters needed for access to the inventory database,
     *  and initializes the cooresponding fields. 
     * @param dburl URL of the mySQL database holding the current inventory.
     * @param username Username for the user accessing the inventory database.
     * @param password Password for the user accessing the database.
     */
    public DatabaseAccess(String dburl, String username, String password) {
        this.DBURL = dburl;
        this.USERNAME = username;
        this.PASSWORD = password;
    }

    /**
     * Getter for DBURL.
     * @return A string for the URL of the mySQL database holding the current inventory.
     */
    public String getDburl() {
        return this.DBURL;
    }

    /**
     *  Method intitalizeConnection initializes the Connection field called dbConnect.
     */
    public void initializeConnection() {
        try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e){
            System.err.println("Could not connect to the database.");
        }
    }

    /**
     * Method fetchTables will get all table names from the database in an sql file,
     *  and will return them in an ArrayList of type String.
     * @return ArrayList of type String containing all current table names.
     */
    public ArrayList<String> fetchTables() {
        //arrayList to be filled with table names
        ArrayList <String> tableString = new ArrayList<String>();
        try{
            String query = "SHOW TABLES";
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            //add to list for all retrieved table names
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
     * Method fetchColumns searches for and returns an ArrayList of type string
     *  containing the names of all columns in a specified table from the database.
     * @param table String name of the desired table.
     * @return ArrayList of type string containing the names of all columns in a table
     *  found in the database.
     */
    public ArrayList<String> fetchColumns(String table) {
        //arrayList to be filled with column names
        ArrayList <String> typeString = new ArrayList<String>();
        try{
            String query = "SELECT * FROM " + table;
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            ResultSetMetaData resultMD = results.getMetaData();
            int columns = resultMD.getColumnCount();
            //add to list for all retrieved column names
            for (int j = 1; j < columns + 1; j++) {
                typeString.add(resultMD.getColumnName(j));
            }
            stmt.close();
        } catch(SQLException e){
            System.err.println("Could not execute query.");
        }
        return typeString;
    }

    /**
     * Close is a method that closes the Connection field dbConnect, and ResultSet results.
     */
    public void close() {
        try {
            results.close();
            dbConnect.close();
        }
        catch (SQLException e) {
            System.err.println("Could not terminate connection.");
        }
    }

    /**
     * Method fetchTypes searches for and returns an ArrayList of type string containing each
     *  unique type found in a specified table within the database.
     * @param table String name of the desired table.
     * @return ArrayList of type string containing each unique type found in the table within
     *  the database.
     */
    public ArrayList<String> fetchTypes(String table) {
        //arrayList to be filled with type names
        ArrayList <String> typeString = new ArrayList<String>();
        try {
            String query = "SELECT * FROM " + table;
            Statement stmt = dbConnect.createStatement();
            results= stmt.executeQuery(query);
            //add to list for all retrieved type names
            while(results.next()){
                if(!typeString.contains(results.getString("Type"))) {
                    typeString.add(results.getString("Type"));
                }
            }
            stmt.close();
        } catch(SQLException e) {
            System.out.println("Could not execute query.");
        }
        return typeString;
    }

    /**
     * Method fetchManufacturerName searches for and returns an ArrayList of type
     *  String containing each unique manufacturer name found within a specified
     *  table in the database.
     * @param table String name of the desired table.
     * @return ArrayList of type String containing each unique manufacturer name found
     *  within the table in the database.
     */
    public ArrayList<String> fetchManufacturerName(String table) {
        //arrayList to be filled with manufacturer names from table
        ArrayList <String> manufacturerID = new ArrayList<String>();
        try {
            String query = "SELECT * FROM " + table;
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            //add to list for all manufacturers currently in specified table
            while(results.next()) {
                if(!manufacturerID.contains(results.getString("ManuID"))) {
                    manufacturerID.add(results.getString("ManuID"));
                }
            }
            stmt.close();
        } catch(SQLException e) {
            System.out.println("Could not execute query.");
        }
        //arrayList to be filled with manufacturer names from both table and
        //current manufacturers
        ArrayList<String> manufacturerName = new ArrayList<String>();
        try {
            String query = "SELECT * FROM MANUFACTURER";
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            //add to list if manufacturer is in both specified table and current
            //manufacturers
            while(results.next()) {
                if(manufacturerID.contains(results.getString("ManuID") )) {
                    manufacturerName.add(results.getString("Name"));
                }
            }
            stmt.close();
        } catch(SQLException e) {
            System.out.println("Could not execute query.");
        }
        return manufacturerName;
    }

    /**
     * Method fetchSpecificType will find every row in the specified table within the database
     *  which matches the specified type. These rows will be returned in the form of an ArrayList
     *  of type String.
     * @param table String name of the desired table.
     * @param type String name of the desired object type.
     * @return ArrayList containing all valid rows matching the specified type in the form of
     *  concatenated strings.
     */
    public ArrayList<String> fetchSpecificType(String table, String type) {
        //all columns in the specified table
        ArrayList <String> columns = fetchColumns(table); 
        //arrayList to be filled with a string concatenation of all 
        ArrayList<String> validArray = new ArrayList<String>(); 
        try {
            String query = "SELECT * FROM " +table;
            Statement stmt = dbConnect.createStatement();
            results = stmt.executeQuery(query);
            while(results.next()) {
                if(results.getString("Type").equals(type)) { //build string if type matches
                    String temp = "";
                    for(int i = 0; i < columns.size(); i++) {
                        if (i < columns.size() - 1) {
                            temp += results.getString(columns.get(i)) + "/"; //separator
                        }
                        else {
                            temp += results.getString(columns.get(i)); //last field, no separator
                        }
                    }
                validArray.add(temp); //add to array
                }
            }
            stmt.close();
        } catch(SQLException e) {
            System.err.println("Could not execute query.");
        }
        return validArray;
    }

    /**
     * Method objectConstructor will construct and return a generic array based on the specified
     *  table and type. This generic array will be generated by typecasting specific objects
     *  (i.e Chair, Desk, Lamp, Filing) to type T. 
     * @param <T> Generic type for any FurnitureItem object.
     * @param table String name of the desired table.
     * @param type String name of the desired object type.
     * @return Generic array of FurnitureItem objects. 
     */
    public <T> T[] objectConstructor(String table, String type) {
        //ArrayList containing all valid rows of the specified type in the form of concatenated strings.
        ArrayList<String> validRows = fetchSpecificType(table, type);
        //construct chair objects
        if(table.equals("chair")) {
            Chair[] returnChair = new Chair[validRows.size()];
            //parse information from concatenated string,
            //construct object and add to list
            for(int i = 0; i < validRows.size(); i++) {
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
            return (T[])returnChair;
        }
        //construct desk objects
        else if(table.equals("desk")) {
            Desk[] returnDesk = new Desk[validRows.size()];
            //parse information from concatenated string,
            //construct object and add to list
            for(int i = 0; i < validRows.size(); i++) {
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
            return (T[])returnDesk;
        }
        //construct filing objects
        else if(table.equals("filing")) {
            Filing[] returnFiling = new Filing[validRows.size()];
            //parse information from concatenated string,
            //construct object and add to list
            for(int i = 0; i < validRows.size(); i++) {
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
            return (T[])returnFiling;
        }
        //construct lamp objects
        else if(table.equals("lamp")) {
            Lamp[] returnLamp = new Lamp[validRows.size()];
            //parse information from concatenated string,
            //construct object and add to list
            for(int i = 0; i < validRows.size(); i++) {
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
            return (T[])returnLamp;
        }
        //no valid table name was provided, return null
        else {
            T[] empty = null;
            return empty;
        }
    }

    /**
     * Method deleteFromTable will delete every row of the specified table within the database
     *  containing an ID found within the specified string array.
     * @param table String name of the desired table.
     * @param id String array of names of the target item ID's.
     */
    public void deleteFromTable(String table, String[] id) {
        //attempt to delete from table for each of the specified ID's
        for (int i = 0; i < id.length; i++) {
            try {
                String query = "DELETE FROM  " + table + " WHERE ID = ?";
                PreparedStatement myStmt = dbConnect.prepareStatement(query);
                myStmt.setString(1, id[i]);
                myStmt.executeUpdate();
                myStmt.close();
            } catch(SQLException e) {
                System.err.println("Could not execute query.");
            }
        }  
    }
}