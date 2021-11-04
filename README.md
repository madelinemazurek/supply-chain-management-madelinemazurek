"# supply-chain-management-madelinemazurek" 

This program is designed to provide a user with access to a database of furniture items, with the goal of re-using 
avaliable furniture components to promote sustainability and enviromental responsibilty. 

By inputting a request for furniture items as prompted on the command line, the user is able to request a number
and type of items to be purchased. The program will then access the live database, calculate the most cost effective
way to combine avaliable components using bitmasking, complete the order, and return a purchase order form to the user.

Click here for a video overview of the code and functionality of this program:
https://www.youtube.com/watch?v=jVgCQJguIEg

The Menu class is the driver of our program. It is the file that must be compiled and run. It can be compiled and 
run with the following commands, assuming the mysql-connector jar file is in a folder called lib in the same 
directory that holds the edu folder:
javac -cp .;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/Menu.java
java -cp .;lib/mysql-connector-java-8.0.23.jar;. edu.ucalgary.ensf409.Menu

The user will then be prompted to enter information for the order through the keyboard.

Some additional notes about our unit tests. Many of the tests access the database and check that certain variables 
contain expected values. For example, we test that our program can find the lowest price by finding an order and 
checking the returned price. This  required us to hardcode the expected return values (ie. check that the returned 
cost is 100, etc). Therefore, the unit tests must be run against the database provided by the ENSF 409 teaching team 
in the project folder in D2L. If a different database is used, some tests will fail because the test expected a 
specific, hardcoded price. Our program is fully capable of finding the lowest price regardless of the data entries in 
the database tables, but specifically for the unit tests, the original database must be used.

Tests can be compiled and run with the following commands:

javac -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar;. edu/ucalgary/ensf409/ScmTest.java

java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar;lib/mysql-connector-java-8.0.23.jar;. org.junit.runner.JUnitCore edu.ucalgary.ensf409.ScmTest

#Team Member Names
[Madeline Mazurek](https://github.com/March-27-Hackathon/supply-chain-management-madelinemazurek)
[Tyler Thain](https://github.com/Tyler-Thain/supply-chain-management-madelinemazurek)
[Jared Assen](https://github.com/JaredAssen/supply-chain-management-madelinemazurek)
[Michael Card](https://github.com/Dacard45/supply-chain-management-madelinemazurek)
