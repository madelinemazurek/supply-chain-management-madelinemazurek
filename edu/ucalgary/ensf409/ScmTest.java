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

        // @Test
        // // Constructor with three args tested
        // public void constructorTest(String dburl, String username, String password){
        //     DatabaseAccess test = new DatabaseAccess(dburl , username, password);
        //     assertTrue("Constructor has incorrectly initialized fields", dburl.equals(test.getDburl()));
        // }
}
