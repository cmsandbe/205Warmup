import java.util.Scanner;
public class Query {
   public static void main(String[] args) {

      SqliteDB database = new SqliteDB();
      
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

//      database.runQuery(queryStatementExample2);
      
      //query for join requirement: 
//      SELECT Poke.TypeID, Poke.Name, Stat.MaxCP,Stat.MaxHP
//      FROM Poke
//      INNER JOIN Stat
//      ON Poke.typeid=Stat.typeid WHERE Name = 'Charmeleon';      
      //this returns '5' 'charmeleon' '1568' '103'
      
//      
//      SELECT Poke.Name, Type.typename
//      FROM Poke
//      INNER JOIN Type
//      ON Poke.t1id=Type.typeid WHERE Name = 'Charmeleon';
      
      //this returns 'charmeleon' 'fighting'
      
      
      

      database.closeConnection();
      
      

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
      while (!(input.equals("END"))) {
         System.out.print("> ");
         input = keyboard.nextLine();
      }
   }
}
