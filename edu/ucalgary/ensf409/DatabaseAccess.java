package edu.ucalgary.ensf409;
import java.sql.*;
import java.util.*;
public class DatabaseAccess{
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;
    private Connection dbConnect;
    private ResultSet results;
    public DatabaseAccess(String dburl, String username, String password){
        this.DBURL = dburl;
        this.USERNAME = username;
        this.PASSWORD = password;
    }
    public void initializeConnection(){
        try{
            dbConnect = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
        } catch (SQLException e){
            System.err.println("Could not connect to the database");
        }
    }
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
    public void close(){
        try{
            results.close();
            dbConnect.close();
        }
        catch (SQLException e){
            System.err.println("Could not fully close");
        }
    }
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