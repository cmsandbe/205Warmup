import java.util.Scanner;
import java.util.ArrayList;

public class Query {
   public static void main(String[] args) {

      SqliteDB database = new SqliteDB();
      
      /*
      String queryStatementExample = "SELECT * FROM Poke WHERE Name = 'Charmeleon';";
      String queryStatementExample2 = "      SELECT Poke.TypeID, Poke.Name, Stat.MaxCP,Stat.MaxHP\n" + 
        		"      FROM Poke\n" + 
        		"      INNER JOIN Stat\n" + 
        		"      ON Poke.typeid=Stat.typeid WHERE Name = 'Charmeleon';  ";
      //just change charmeleon to var of pokemon name
      
      String queryStatementExample3 = "      SELECT Poke.Name, Type.typename\n" + 
        		"      FROM Poke\n" + 
        		"      INNER JOIN Type\n" + 
        		"      ON Poke.t1id=Type.typeid WHERE Name = 'Charmeleon';";
        					  //change t1id to t2id if they want secondary id, and change charmeleon to var of pokemon name
      
      database.runQuery(queryStatementExample);
      database.runStatsQuery(queryStatementExample2);
      database.runTypeQuery(queryStatementExample3);
      */
      
      //      database.runQuery(queryStatementExample2);
      
    //query for join requirement: 
	//    SELECT Poke.TypeID, Poke.Name, Stat.MaxCP,Stat.MaxHP
	//    FROM Poke
	//    INNER JOIN Stat
	//    ON Poke.typeid=Stat.typeid WHERE Name = 'Charmeleon';      
	    //this returns '5' 'charmeleon' '1568' '103'
	    
	//    
	//    SELECT Poke.Name, Type.typename
	//    FROM Poke
	//    INNER JOIN Type
	//    ON Poke.t1id=Type.typeid WHERE Name = 'Charmeleon';
    
    //this returns 'charmeleon' 'fighting'

      
      
      

      // Display rules to user
      System.out.println("RULES:");
      System.out.println("Enter the Pokemon name followed by the attribute you would like to look up.");
      System.out.println("Attributes include Type, SecondaryType, MaxCP, MaxHP, PictureOf");
      System.out.println("Example: Bulbasaur MaxCP");
      System.out.println("Enter END to terminate the program.");
      System.out.println();
      
      // Take in user input
      Scanner keyboard = new Scanner(System.in);
      String input="";
      String name = "";
      ArrayList<String> atts;
      while (!(input.equals("END"))) {
    	 atts = new ArrayList<>();
    	  
         System.out.print("> ");
         input = keyboard.nextLine();
         
         
         //String attribute = "";
         String[] words = input.split("\\s+");

         name = words[0];
         
         for (int i=1; i<words.length; i++) {
        	 atts.add(words[i]);
         }
         
         String queryStatement = "";
         
         //  Just name
         if (atts.size() == 0) {
       	  queryStatement = "SELECT * FROM Poke WHERE Name = '" + name + "';";
       	  database.runQuery(queryStatement);
         }
         
         for (String attribute : atts) {
        	
             
             
        	 
         }
         
         // Name and Attribute
         /*
         if (atts.contains("MaxCP") || atts.contains("MaxHP") || atts.contains("PictureOf")) {
        	 queryStatement = "      SELECT Poke.TypeID, Poke.Name, Stat.MaxCP,Stat.MaxHP\n" + 
             		"      FROM Poke\n" + 
            		"      INNER JOIN Stat\n" + 
            		"      ON Poke.typeid=Stat.typeid WHERE Name = '" + name + "';  ";
        	 database.runStatsQuery(queryStatement);
         }
         */
         
         
         if (atts.contains("MaxCP")) {
        	 queryStatement = "      SELECT Poke.TypeID, Poke.Name, Stat.MaxCP\n" + 
              		"      FROM Poke\n" + 
             		"      INNER JOIN Stat\n" + 
             		"      ON Poke.typeid=Stat.typeid WHERE Name = '" + name + "';  ";
         	 database.runStatsQuery(queryStatement);
         }
         
         if (atts.contains("MaxHP")) {
        	 queryStatement = "      SELECT Poke.TypeID, Poke.Name, Stat.MaxHP\n" + 
              		"      FROM Poke\n" + 
             		"      INNER JOIN Stat\n" + 
             		"      ON Poke.typeid=Stat.typeid WHERE Name = '" + name + "';  ";
         	 database.runStatsQuery(queryStatement);
         }
         
         if (atts.contains("PictureOf")) {
        	 
         }
         
         
         // Name and Type
         if (atts.contains("Type")) {
        	 queryStatement = "      SELECT Poke.Name, Type.typename\n" + 
             		"      FROM Poke\n" + 
             		"      INNER JOIN Type\n" + 
             		"      ON Poke.t1id=Type.typeid WHERE Name = '" + name + "';";
          
        	 database.runTypeQuery(queryStatement);
         }
         
         if (atts.contains("SecondaryType")) {
        	 queryStatement = "      SELECT Poke.Name, Type.typename\n" + 
             		"      FROM Poke\n" + 
             		"      INNER JOIN Type\n" + 
             		"      ON Poke.t2id=Type.typeid WHERE Name = '" + name + "';";
         
        	 database.runTypeQuery(queryStatement);
         } 
         
         
      }
      
      
      
      database.closeConnection();
      
      
   }
}