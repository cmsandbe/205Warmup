import java.util.Scanner;
import java.util.ArrayList;
import java.io.IOException;
import java.io.*; 

public class Query {

    public static void main(String[] args) throws IOException{
        SqliteDB database = new SqliteDB();
        Scanner keyboard = new Scanner(System.in);
        database.createTables(); // Create the tables in the DB
    	String url = System.getProperty("user.dir") + "/" + "warmup.sqlite";  // Make connection URL

        File file = new File(url); 
        // Display rules to user
        System.out.println("RULES:");
        System.out.println("Enter the Pokemon name followed by the attribute you would like to look up.");
        System.out.println("Attributes include Type, SecondaryType, MaxCP, MaxHP, PictureOf");
        System.out.println("Keep in mind: This program is case sensitive!");
        System.out.println("Example: Bulbasaur MaxCP");
        System.out.println("Enter END to terminate the program.");
        System.out.println();

        // Take in user input
        String input="end";
        String name;
        ArrayList<String> atts;
        while (!(input.equals("END"))) {
            atts = new ArrayList<>();

            System.out.print("> ");
            input = keyboard.nextLine().trim();

            String[] words = input.split("\\s+");

            name = words[0];

            for (int i=1; i<words.length; i++) {
                atts.add(words[i]);
            }

            String queryStatement;

            //  Just name
            if (atts.size() == 0) {
            	if(!input.contains("END")) {
                	if(database.testQuery("SELECT * FROM Poke Where Name = '" + name + "';") == false) {
                		System.out.println("Looks like that pokemon isn't in our database yet! Please try another one.");
                	}
                	
                	if(database.testQuery("SELECT * FROM Poke Where Name = '" + name + "';") == true) {
//                		System.out.println("Please try again with a attribute!");
                        queryStatement = "SELECT *\n" + 
                        		"FROM Poke\n" + 
                        		"JOIN Stat ON Stat.TypeID=Poke.TypeID\n" + 
                        		"JOIN Type ON Type.typeID=Poke.T1ID\n" + 
                        		"WHERE Name = '"+ name +"';\n" + 
                        		"";
                        database.getEverything(queryStatement);
                		
                	}

            	}
//                checkPokName(name,input);
            } else {
            	if(database.testQuery("SELECT * FROM Poke Where Name = '" + name + "';") == false) {
            		System.out.println("Looks like that pokemon isn't in our database yet! Please try another one.");
            	} else {
//                checkPokName(name,input);
                if (atts.contains("MaxCP") || atts.contains("MaxHP") || atts.contains("Type") || atts.contains("SecondaryType") || atts.contains("PictureOf")) {
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

                        database.runSecondaryTypeQuery(queryStatement);
                    }
                    if (atts.contains("PictureOf")) {
                        queryStatement = "SELECT * FROM Poke where name = '" + name + "';";
                        
                        database.runQuery(queryStatement);
                    }
                } else {
                    System.out.println("Invalid attribute.");
                }
            	}
            }
        }


        database.closeConnection();
        file.delete();

    }

//    private static void checkPokName(String names, String input) {
//        String queryStatement;
//        queryStatement = "SELECT * FROM Poke WHERE Name = '" + names + "';";
//        boolean ava = database.checkExist(queryStatement,names);
//        if (ava) {
//            database.runQuery(queryStatement);
//        } else if (input.equals("END")) {
//            return;
//        } else {
//            if (Character.isLowerCase(names.charAt(0))){
//                System.out.println("Sorry, " + names + " need to start with uppercase letter");
//            } else{
//                System.out.println("Sorry, " + names + " is not a discovered pokemon");
//            }
//        }
//    }
}
