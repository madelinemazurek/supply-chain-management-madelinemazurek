/**
*@author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
*@author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
*@author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
*@author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
*@version    1.0
*@since      1.0
*/

package edu.ucalgary.ensf409;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class ScmTest {
    @Test
    public void searchInventoryConstructorAndGetOrderFoundTest(){
        //this test will not delete from database. Deletion occurs elsewhere. Done for ease of testing.
        String category = "chair";
        String type = "Mesh";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        db.close();
        assertEquals("Could not find a combo that works",test.getOrderFound(),true);
    }

    @Test
    public void searchInventoryConstructorAndGetBestOrderTest(){
        //this test will not delete from database. Deletion occurs elsewhere. Done for ease of testing.
        String category = "filing";
        String type = "Large";
        int numberOfItems = 2;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        db.close();
        assertNotNull("could not find a combo that works",test.getBestOrder());
    }

    @Test
    // Constructor with three args tested
    public void databaseAccessConstructorTest(){
        DatabaseAccess test = new DatabaseAccess("dburl" , "username", "password");
        assertEquals("Constructor has incorrectly initialized fields", "dburl", (test.getDburl()));
    }

    @Test 
    public void DatabaseAccessFetchTablesTest(){
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        ArrayList<String> tableNames = db.fetchTables();
        boolean goodTest = true;
        if(!tableNames.contains("filing")){
            goodTest = false;
        }
        if(!tableNames.contains("chair")){
            goodTest = false;
        }
        if(!tableNames.contains("desk")){
            goodTest = false;
        }
        if(!tableNames.contains("manufacturer")){
            goodTest = false;
        }
        if(!tableNames.contains("lamp")){
            goodTest = false;
        }
        db.close();
        assertTrue("Fetch tables did not successfully create an ArrayList of table names", goodTest);
    }

    @Test
    public void DatabaseAccessFetchColumnsTest(){
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        ArrayList<String> columnNames = db.fetchColumns("desk");
        boolean goodTest = true;
        if(!columnNames.contains("ID")){
            goodTest = false;
        }
        if(!columnNames.contains("Type")){
            goodTest = false;
        }
        if(!columnNames.contains("Legs")){
            goodTest = false;
        }
        if(!columnNames.contains("Top")){
            goodTest = false;
        }
        if(!columnNames.contains("Drawer")){
            goodTest = false;
        }
        if(!columnNames.contains("Price")){
            goodTest = false;
        }
        if(!columnNames.contains("ManuID")){
            goodTest = false;
        }
        db.close();
        assertTrue("Fetch columns did not successfully create an array list of column names", goodTest);
    }

    @Test
    public void DatabaseAccessFetchTypesTest(){
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        ArrayList<String> types = db.fetchTypes("desk");
        boolean goodTest = true;
        if(!types.contains("Standing")){
            goodTest = false;
        }
        if(!types.contains("Adjustable")){
            goodTest = false;
        }
        if(!types.contains("Traditional")){
            goodTest = false;
        }
        db.close();
        assertTrue("Fetch types did not successfully create an array list of column", goodTest);
    }

    @Test
    public void chairConstructorAndGetLegsTest(){
        Chair test = new Chair("1234", "mesh", 'Y', 'N', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getLegs());
    }

    @Test
    public void chairConstructorAndGetArmsTest(){
        Chair test = new Chair("1234", "mesh", 'N', 'Y', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getArms());
    }

    @Test
    public void chairConstructorAndGetSeatTest(){
        Chair test = new Chair("1234", "mesh", 'N', 'N', 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getSeat());
    }

    @Test
    public void chairConstructorAndGetCushionTest(){
        Chair test = new Chair("1234", "mesh", 'N', 'N', 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getCushion());
    }

    @Test
    public void deskConstructorAndGetLegsTest(){
        Desk test = new Desk("1234", "Standing", 'Y', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getLegs());
    }

    @Test
    public void deskConstructorAndGetTopTest(){
        Desk test = new Desk("1234", "Standing", 'N', 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getTop());
    }

    @Test
    public void deskConstructorAndGetDrawerTest(){
        Desk test = new Desk("1234", "Standing", 'N', 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getDrawer());
    }

    @Test
    public void filingConstructorAndGetRailsTest(){
        Filing test = new Filing("1234", "Standing", 'Y', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getRails());
    }

    @Test
    public void filingConstructorAndGetDrawersTestTest(){
        Filing test = new Filing("1234", "Standing", 'N', 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getDrawers());
    }

    @Test
    public void filingConstructorAndGetCabinetTestTest(){
        Filing test = new Filing("1234", "Standing", 'N', 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getCabinet());
    }

    @Test
    public void lampConstructorAndGetBaseTestTest(){
        Lamp test = new Lamp("1234", "Standing", 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getBase());
    }

    @Test
    public void lampConstructorAndGetBulbTestTest(){
        Lamp test = new Lamp("1234", "Standing", 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getBulb());
    }

    @Test
    public void furnitureItemConstructorAndGetIDTest(){
        FurnitureItem test = new FurnitureItem("1234", "Standing", 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","1234",test.getID());
    }

    @Test
    public void furnitureItemConstructorAndGetTypeTest(){
        FurnitureItem test = new FurnitureItem("1234", "Standing", 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","Standing",test.getType());
    }

    @Test
    public void furnitureItemConstructorAndGetPriceTest(){
        FurnitureItem test = new FurnitureItem("1234", "Standing", 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",1000000,test.getPrice());
    }

    @Test
    public void furnitureItemConstructorAndGetManuIDTest(){
        FurnitureItem test = new FurnitureItem("1234", "Standing", 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","4567",test.getManuID());
    }

    @Test
    public void chairGetManuIDTest(){
        Chair test = new Chair("1234", "mesh", 'N', 'N', 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","4567",test.getManuID());
    }

    @Test
    public void deskGetPriceTest(){
        Desk test = new Desk("1234", "Standing", 'Y', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",1000000,test.getPrice());
    }

    @Test
    public void filingGetTypeTest(){
        Filing test = new Filing("1234", "Standing", 'N', 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","Standing",test.getType());
    }

    @Test
    public void lampGetIDTest(){
        Lamp test = new Lamp("1234", "Standing", 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","1234",test.getID());
    }

    @Test
    //testing method formatOutput in WriteText
    public void formatOutputTest() {
        String category = "Chair";
        String type = "Mesh";
        String number = "3";
        String[] itemIDs = {"C0110", "C0120", "C0130"};
        String price = "450";
        WriteText myWriter = new WriteText(category, type, number, itemIDs, price);
        String expected = "Furniture Order Form\n\nFaculty Name:\nContact:\n" + 
                            "Date:\n\nOriginal Request: Mesh Chair, 3\n\n" + 
                            "Items Ordered\nID: C0110\nID: C0120\nID: C0130\n\n" + 
                            "Total Price: $450";
        myWriter.writeOutput();
        String actual = myWriter.getOutput();
        assertEquals("Output strings do not match", expected, actual);
    }

    @Test
    /*Testing that SearchInventory finds the correct price for a large filing
        cabinets using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice1LargeFilingTest(){
        String category = "filing";
        String type = "Large";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 300, price);
    }

    @Test
    /*Testing that SearchInventory finds the correct price for an ergonomic
        chair using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice1ErgoChairTest(){
        String category = "chair";
        String type = "Ergonomic";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 250, price);
    }

    @Test
    /*Testing that SearchInventory finds the correct price for 2 desk lamps
        using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice2DeskLampTest(){
        String category = "lamp";
        String type = "Desk";
        int numberOfItems = 2;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 40, price);
    }

    @Test
    /*Testing that SearchInventory finds the correct price for an adjustable
        desk using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPriceTest(){
        String category = "desk";
        String type = "Adjustable";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 250, price);
    }

    @Test
    public void DatabaseAccessFetchSpecificTypeTest(){
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection(); 
        ArrayList<String> typeArray = db.fetchSpecificType("desk", "Standing");
        int correctLength = 5;
        int sampleLength = typeArray.size();
        boolean goodTest = false;
        if(correctLength == sampleLength){
            goodTest = true;
        }
        db.close();
        assertTrue("Fetch specific type did not create an array list of the right size", goodTest);
    }

    @Test
    public <T> void ObjectConstructorTest(){
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        boolean goodTest = true;
        T[] objectConstructed = db.objectConstructor("desk", "Standing");
        if(objectConstructed == null){
            goodTest = false;
        }
        db.close();
        assertTrue("Object constructor did not correctly construct an object.", goodTest);
    }
}