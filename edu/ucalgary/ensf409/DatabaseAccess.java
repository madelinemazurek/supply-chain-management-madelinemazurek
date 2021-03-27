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
    public void deleteFromTable(String table, String id){
        try{
            String query = "DELETE FROM ? WHERE ID = ?";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setString(1,table);
            myStmt.setString(2,id);
            myStmt.executeUpdate();
            myStmt.close();
        } catch(SQLException e){
            System.err.println("Couldn't execute query");
        }
    }
    public ArrayList<String> fetchTables(){
        ArrayList <String> tableString = new ArrayList<String>();
        try{
            Statement myStmt = dbConnect.createStatement();
            String query = "SHOW TABLES";
            results = myStmt.executeQuery(query);
            while(results.next()){
                System.out.println(results.getString("TABLE_NAME"));
            }
        } catch (SQLException e){
            System.err.print("Could not execute query.");
        }
        return tableString;
    }
    public String[] fetchTypes(String table){
        String [] temp ={"Cool"};
        return temp;
    }
    public void close(){
        try{
            dbConnect.close();
        }
        catch (SQLException e){
            System.err.println("Could not fully close");
        }
    }
    public static void main(String[] args){
        DatabaseAccess myTest = new DatabaseAccess("jdbc:mysql://localhost/inventory","ethan", "Roxanne3");
        myTest.initializeConnection();
        ArrayList<String> cool = myTest.fetchTables();
    }
}
