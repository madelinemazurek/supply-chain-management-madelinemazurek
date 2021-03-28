package edu.ucalgary.ensf409;

import static org.junit.Assert.*;
import org.junit.*;


    public class ScmTest {
        @Test
        public void searchInventoryConstructorAndGetOrderFoundTest(){

            String category = "chair";
            String type = "Mesh";
            int numberOfItems = 1;
            DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
            SearchInventory test = new SearchInventory(category,type,numberOfItems,db);

            assertEquals("Could not find a combo that works",test.getOrderFound(),true);
        }

        @Test
        public void searchInventoryConstructorAndGetBestOrderTest(){

            String category = "filing";
            String type = "Large";
            int numberOfItems = 2;
            DatabaseAccess db = new DatabaseAccess("jdbc:mysql://localhost/inventory","scm","ensf409");
            SearchInventory test = new SearchInventory(category,type,numberOfItems,db);

            assertNotNull("could not find a combo that works",test.getBestOrder());
        }

        @Test
        // Constructor with three args tested
        public void databaseAccessConstructorTest(){
            DatabaseAccess test = new DatabaseAccess("dburl" , "username", "password");
            assertEquals("Constructor has incorrectly initialized fields", "dburl", (test.getDburl()));
        }

        @Test
        // Constructor with three args tested
        public void constructorTest(String dburl, String username, String password){
            DatabaseAccess test = new DatabaseAccess(dburl , username, password);
            assertTrue("Constructor has incorrectly initialized fields", dburl.equals(test.getDburl()));
        }
}
