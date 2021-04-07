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
import java.util.ArrayList;


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
        //testing method formatOutput in WriteText
        public void formatOutputTest() {
            String category = "Chair";
            String type = "Mesh";
            String number = "3";
            String[] itemIDs = {"C0110", "C0120", "C0130"};
            String price = "450";
            WriteText myWriter = new WriteText(category, type, number, itemIDs, price);
            String expected = "Furniture Order Form\n\nFaculty Name:\nContact: \n" + 
                                "Date: \n\nOriginal Request: Mesh Chair, 1\n\n" + 
                                "Items Ordered\nID: C0110\nID: C0120\nID: C0130\n\n" + 
                                "Total Price: $450";
            myWriter.writeOutput();
            String actual = myWriter.getOutput();
            assertEquals("Output strings do not match", expected, actual);
        }


}
