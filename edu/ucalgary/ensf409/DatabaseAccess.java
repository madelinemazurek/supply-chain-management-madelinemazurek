package edu.ucalgary.ensf409;
import java.sql.*;
public class DatabaseAccess{
    public final String DBURL;
    public final String USERNAME;
    public final String PASSWORD;
    private Connection dbConnect;
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
    
    public void close(){
        try{
            dbConnect.close();
        }
        catch (SQLException e){
            System.err.println("Could not fully close");
        }
    }
}
