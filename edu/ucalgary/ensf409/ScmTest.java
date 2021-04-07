/**
*@author     Madeline Mazurek <a href="mailto:madeline.mazurek@ucalgary.ca">madeline.mazurek@ucalgary.ca</a> 
*@author     Jared Assen <a href="mailto:jared.assen@ucalgary.ca">jared.assen@ucalgary.ca</a> 
*@author     Ethan Card <a href="mailto:michael.card@ucalgary.ca">michael.card@ucalgary.ca</a> 
*@author     Tyler Thain <a href="mailto:tyler.thain@ucalgary.ca">tyler.thain@ucalgary.ca</a> 
*@version    1.4
*@since      1.0
*/

package edu.ucalgary.ensf409;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

/**
 * ScmTest is a unit testing file that demonstrates functionality for each part of the accompanying program.
 * 
 * Many of the following tests query the database and the results of the test depend on the contents of
 *  the inventory database posted by the ENSF409 teaching team. For example, we will test for the price
 *  of a successful order, and compare the int returned to an int value in the test. Therefore, all of the
 *  prices and items in the database being tested against must be the same as the original database. As a
 *  note, our unit tests do not delete from the database, because the order in which tests are executed is
 *  random, and altering the database may invalidate later tests. Therefore, for ease of testing, we opted
 *  to not include the deletion of the returned order in that test. We still demonstrate the ability to
 *  access the database and delete the specified order in the video included in our project submission.
 */
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
        assertEquals("SearchInventory failed to find the correct price", 400, price);
    }

    @Test
    /** 
     * Tests objectConstructor(String table, String type) method in database access,
     * using the original inventory database checks if objectConstructor does not 
     * return null 
     */
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
	
	@Test
    /**
     * Tests fetchSpecificType method in database access using the original 
     * inventory database. Checks if correct amount of table rows are 
     * fetched from the database.
     */
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
    /**
     * Tests fetchTypes method in DatabaseAccess using the original
     * inventory database. Checks if correct type names have been 
     * returned in the form of an ArrayList from the method.
     */
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
    /**
     * Tests fetchColumns method in DatabaseAccess using the original
     * inventory database. Checks if correct column names have been 
     * returned in the form of an ArrayList from the method.
     */
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
    /**
     * Tests fetchTables method in DatabaseAccess using the original 
     * inventory database. Checks if the correct table names have been 
     * returned in the form of an ArrayList from the method.
     */
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
    /**
     * Tests DatabaseAccess constructor that the constructor will correctly
     * set a field in DatabaseAccess when the constructor is called.
     */
    public void databaseAccessConstructorTest(){
        DatabaseAccess test = new DatabaseAccess("dburl" , "username", "password");
        assertEquals("Constructor has incorrectly initialized fields", "dburl", (test.getDburl()));
    }

    @Test
    /**Testing that SearchInventory finds the correct price for a medium filing
        cabinets using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice1MediumFilingTest(){
        String category = "filing";
        String type = "Medium";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 200, price);
    }

    @Test
    /**Testing that SearchInventory finds the correct price for 2 small filing
        cabinets using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice2SmallFilingTest(){
        String category = "filing";
        String type = "Small";
        int numberOfItems = 2;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 200, price);
    }

    @Test
    /**Testing that SearchInventory finds the correct price for 1 executive
        chair using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice1ExecutiveChairTest(){
        String category = "chair";
        String type = "Executive";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 400, price);
    }

    @Test
    /**Testing that SearchInventory finds the correct price for 1 mesh
        chair using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice1MeshChairTest(){
        String category = "chair";
        String type = "Mesh";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 200, price);
    }

    @Test
    /**Testing that SearchInventory finds the correct price for 1 task
        chair using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice1TaskChairTest(){
        String category = "chair";
        String type = "Task";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 150, price);
    }

    @Test
    /**Testing that SearchInventory finds the correct price for 1 study lamp
        using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice1StudyLampTest(){
        String category = "lamp";
        String type = "Study";
        int numberOfItems = 1;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 10, price);
    }

    @Test
    /**Testing that SearchInventory finds the correct price for 2 swing arm lamps
        using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice2SwingArmLampTest(){
        String category = "lamp";
        String type = "Swing Arm";
        int numberOfItems = 2;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 60, price);
    }

    @Test
    /**Testing that SearchInventory finds the correct price for 2 standing
        desks using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice2StandingDeskTest(){
        String category = "desk";
        String type = "Standing";
        int numberOfItems = 2;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 600, price);
    }

    @Test
    /**Testing that SearchInventory finds the correct price for 1 traditional
        desk using the original inventory database. Test does not delete
        any entries from the database for ease of testing*/
    public void searchInventoryGetBestPrice1TraditionalDeskTest(){
        String category = "desk";
        String type = "Traditional";
        int numberOfItems = 2;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        int price = test.getBestOrder().getCost();
        db.close();
        assertEquals("SearchInventory failed to find the correct price", 200, price);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 adjustable desks, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20AdjDeskTest(){
        String category = "desk";
        String type = "Adjustable";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 standing desks, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20StandingDeskTest(){
        String category = "desk";
        String type = "Standing";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 traditional desks, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20TraditionalDeskTest(){
        String category = "desk";
        String type = "Traditional";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 desk lamps, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20DeskLampTest(){
        String category = "lamp";
        String type = "Desk";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 study lamps, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20StudyLampTest(){
        String category = "lamp";
        String type = "Study";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 swing arm lamps, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20SwingArmLampTest(){
        String category = "lamp";
        String type = "Study";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 ergonomic chairs, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20ErgoChairTest(){
        String category = "chair";
        String type = "Ergonomic";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 executive chairs, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20ExecChairTest(){
        String category = "chair";
        String type = "Executive";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 kneeling chairs, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20KneelingChairTest(){
        String category = "chair";
        String type = "Kneeling";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 mesh chairs, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20MeshChairTest(){
        String category = "chair";
        String type = "Mesh";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 task chairs, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20TaskChairTest(){
        String category = "chair";
        String type = "Task";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 large filing cabinets, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20LargeFilingTest(){
        String category = "filing";
        String type = "Large";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 medium filing cabinets, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20MediumFilingTest(){
        String category = "filing";
        String type = "Medium";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**Testing that SearchInventory does not find an order when one isn't possible.
        Attempt to purchase 20 small filing cabinets, which isn't possible for this
        database.*/
    public void searchInventoryPurchase20SmallFilingTest(){
        String category = "filing";
        String type = "Small";
        int numberOfItems = 20;
        DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
        db.initializeConnection();
        SearchInventory test = new SearchInventory(category,type,numberOfItems,db);
        boolean exists = test.getOrderFound();
        db.close();
        assertFalse("SearchInventory found an order when it shouldn't have", exists);
    }

    @Test
    /**
     * Tests getLegs() method by creating a Chair object and seeing if getter method properly returns the correct value.
     */
    public void chairConstructorAndGetLegsTest(){
        Chair test = new Chair("1234", "mesh", 'Y', 'N', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getLegs());
    }

    @Test
    /**
     * Tests getArms() method by creating a Chair object and seeing if getter method properly returns the correct value.
     */
    public void chairConstructorAndGetArmsTest(){
        Chair test = new Chair("1234", "mesh", 'N', 'Y', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getArms());
    }

    @Test
    /**
     * Tests getSeat() method by creating a Chair object and seeing if the getter method properly returns the correct value.
     */
    public void chairConstructorAndGetSeatTest(){
        Chair test = new Chair("1234", "mesh", 'N', 'N', 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getSeat());
    }

    @Test
    /**
     * Tests getCushion() method by creating a Chair object and seeing if the getter method properly returns the correct value.
     */
    public void chairConstructorAndGetCushionTest(){
        Chair test = new Chair("1234", "mesh", 'N', 'N', 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getCushion());
    }

    @Test
    /**
     * Tests getLegs() method by creating a Desk object and seeing if the getter method properly returns the correct value.
     */
    public void deskConstructorAndGetLegsTest(){
        Desk test = new Desk("1234", "Standing", 'Y', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getLegs());
    }

    @Test
    /**
     * Tests getTop() method by creating a Desk object and seeing if the getter method properly returns the correct value.
     */
    public void deskConstructorAndGetTopTest(){
        Desk test = new Desk("1234", "Standing", 'N', 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getTop());
    }

    @Test
    /**
     * Tests getDrawer() method by creating a Desk object and seeing if the getter method properly returns the correct value.
     */
    public void deskConstructorAndGetDrawerTest(){
        Desk test = new Desk("1234", "Standing", 'N', 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getDrawer());
    }

    @Test
    /**
     * Tests getRails() method by creating a Filing object and seeing if the getter method properly returns the correct value.
     */
    public void filingConstructorAndGetRailsTest(){
        Filing test = new Filing("1234", "Standing", 'Y', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getRails());
    }

    @Test
    /**
     * Tests getDrawers() method by creating a Filing object and seeing if the getter method properly returns the correct value.
     */
    public void filingConstructorAndGetDrawersTest(){
        Filing test = new Filing("1234", "Standing", 'N', 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getDrawers());
    }

    @Test
    /**
     * Tests getCabinet() method by creating a Filing object and seeing if the getter method properly returns the correct value.
     */
    public void filingConstructorAndGetCabinetTest(){
        Filing test = new Filing("1234", "Standing", 'N', 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getCabinet());
    }

    @Test
    /**
     * Tests getBase() method by creating a Lamp object and seeing if the getter method properly returns the correct value.
     */
    public void lampConstructorAndGetBaseTest(){
        Lamp test = new Lamp("1234", "Standing", 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getBase());
    }

    @Test
    /**
     * Tests getBulb() method by creating a Lamp object and seeing if the getter method properly returns the correct value.
     */
    public void lampConstructorAndGetBulbTestTest(){
        Lamp test = new Lamp("1234", "Standing", 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",'Y',test.getBulb());
    }

    @Test
    /**
     * Tests getID() method by creating a FurnitureItem object and seeing if the getter method properly returns the correct value.
     */
    public void furnitureItemConstructorAndGetIDTest(){
        FurnitureItem test = new FurnitureItem("1234", "Standing", 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","1234",test.getID());
    }

    @Test
    /**
     * Tests getType() method by creating a FurnitureItem object and seeing if the getter method properly returns the correct value.
     */
    public void furnitureItemConstructorAndGetTypeTest(){
        FurnitureItem test = new FurnitureItem("1234", "Standing", 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","Standing",test.getType());
    }

    @Test
    /**
     * Tests getPrice() method by creating a FurnitureItem object and seeing if the getter method properly returns the correct value.
     */
    public void furnitureItemConstructorAndGetPriceTest(){
        FurnitureItem test = new FurnitureItem("1234", "Standing", 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",1000000,test.getPrice());
    }

    @Test
    /**
     * Tests getManuID() method by creating a FurnitureItem object and seeing if the getter method properly returns the correct value.
     */
    public void furnitureItemConstructorAndGetManuIDTest(){
        FurnitureItem test = new FurnitureItem("1234", "Standing", 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","4567",test.getManuID());
    }

    @Test
    /**
     * Tests that Chair extends FunritureItem by creating a Chair object and seeing if the 
     * getter method getManuID(), which is a part of FurnitureItem, properly returns the correct value.
     */
    public void chairGetManuIDTest(){
        Chair test = new Chair("1234", "mesh", 'N', 'N', 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","4567",test.getManuID());
    }

    @Test
    /**
     * Tests that Desk extends FunritureItem by creating a Desk object and seeing if the 
     * getter method getPrice(), which is a part of FurnitureItem, properly returns the correct value.
     */
    public void deskGetPriceTest(){
        Desk test = new Desk("1234", "Standing", 'Y', 'N', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work",1000000,test.getPrice());
    }

    @Test
    /**
     * Tests that Filing extends FunritureItem by creating a Filing object and seeing if the 
     * getter method getType(), which is a part of FurnitureItem, properly returns the correct value.
     */
    public void filingGetTypeTest(){
        Filing test = new Filing("1234", "Standing", 'N', 'Y', 'N', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","Standing",test.getType());
    }

    @Test
    /**
     * Tests that Lamp extends FunritureItem by creating a Lamp object and seeing if the 
     * getter method getID(), which is a part of FurnitureItem, properly returns the correct value.
     */
    public void lampGetIDTest(){
        Lamp test = new Lamp("1234", "Standing", 'N', 'Y', 1000000, "4567");
        assertEquals("Getter has retrieved the wrong data or does not work","1234",test.getID());
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a category requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTest(){
        Menu test = new Menu("chair","Mesh","6");
        boolean isNotValid = test.checkNotValidInput(1,test.getCategory());
        test.getDatabaseObj().close();
        assertFalse("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a category requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestTwo(){
        Menu test = new Menu("CHAIR","Mesh","6");
        boolean isNotValid = test.checkNotValidInput(1,test.getCategory());
        test.getDatabaseObj().close();
        assertFalse("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a category requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestThree(){
        Menu test = new Menu("  CHaIR   ","Mesh","6");
        boolean isNotValid = test.checkNotValidInput(1,test.getCategory());
        test.getDatabaseObj().close();
        assertFalse("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a category requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestFour(){
        Menu test = new Menu("invalid","Mesh","6");
        boolean isNotValid = test.checkNotValidInput(1,test.getCategory());
        test.getDatabaseObj().close();
        assertTrue("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a type requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestFive(){
        Menu test = new Menu("chair","Mesh","6");
        boolean isNotValid = test.checkNotValidInput(2,test.getType());
        test.getDatabaseObj().close();
        assertFalse("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a type requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestSix(){
        Menu test = new Menu("chair","mESH","6");
        boolean isNotValid = test.checkNotValidInput(2,test.getType());
        test.getDatabaseObj().close();
        assertFalse("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a type requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestSeven(){
        Menu test = new Menu("chair","ThisWontWork","6");
        boolean isNotValid = test.checkNotValidInput(2,test.getType());
        test.getDatabaseObj().close();
        assertTrue("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a number of items requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestEight(){
        Menu test = new Menu("chair","Mesh","6");
        boolean isNotValid = test.checkNotValidInput(3,test.getNumberOfItems());
        test.getDatabaseObj().close();
        assertFalse("Input check is outputting the wrong boolean", isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a number of items requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestNine(){
        Menu test = new Menu("chair","Mesh","0");
        boolean isNotValid = test.checkNotValidInput(3,test.getNumberOfItems());
        test.getDatabaseObj().close();
        assertTrue("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a number of items requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestTen(){
        Menu test = new Menu("chair","Mesh","NotANumber");
        boolean isNotValid = test.checkNotValidInput(3,test.getNumberOfItems());
        test.getDatabaseObj().close();
        assertTrue("Input check is outputting the wrong boolean",isNotValid);
    }

    @Test
    /**
     * Tests checkNotValidInput() in Menu by simulating user input for
     * a number of items requested and seeing if it returns the correct
     * boolean
     */
    public void menuCheckNotValidTestEleven(){
        Menu test = new Menu("chair","Mesh","-67");
        boolean isNotValid = test.checkNotValidInput(3,test.getNumberOfItems());
        test.getDatabaseObj().close();
        assertTrue("Input check is outputting the wrong boolean",isNotValid);
    }
}