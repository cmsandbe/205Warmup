import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;
public class SqliteDB{
    Connection connection = null;
    Statement statement = null;
	String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/" + "warmup.sqlite";  // Make connection URL

    SqliteDB(){
        //we connect to db here
        try {
            Class.forName("org.sqlite.JDBC");
            //warmup.sqlite is the name of our database .sqlite file
            connection=DriverManager.getConnection(url);
            //if we got here, we connected
            System.out.println("Connection successful");
//            connection.close();
        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
        }
    }
    
    
    

    public void createTables() throws IOException{
    	
        // SQL statement for creating a new table created as a string
        String sql ="CREATE TABLE IF NOT EXISTS Poke (\n"
                + "    TypeID int PRIMARY KEY,\n"
                + "    Name varchar NOT NULL,\n"
                + "    T1ID int NOT NULL,\n"
                + "    T2ID int,\n"
                + "    imageurl varchar(200)\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);  // Test the connection again and then try the statement
        } catch (SQLException e) {
            System.out.println(e.getMessage());  // catch any errors and print out message
        }

        // Create a file and scanner for that file
        File pokemoncsv = new File(System.getProperty("user.dir") + "/"+ "tblPokemon.csv");
        Scanner r = new Scanner(pokemoncsv);
        sql = "";
        for(int i = 0; r.hasNext(); i++) {  // For each line in the file, insert into table
        	String[] line =  r.nextLine().split(",");
        	sql += "INSERT INTO Poke (TypeID, Name, T1ID, T2ID, imageurl)\n"
        			+ "values ('" + line[0] + "', '" + line[1].trim() + "', '" + line[2] + "', '" 
        			+ line[3] + "', '" + line[4]+ "'); \n";
            // System.out.println(sql);  // print out statement to error proof           
            try (Connection conn = DriverManager.getConnection(url);
                    Statement stmt = conn.createStatement()) {
                stmt.execute(sql);  // Connect again and try the statement
            } catch (SQLException e) {
                System.out.println(e.getMessage());  // print out any errors that you get
            }
            sql = "";
        }

        // Using same code from above, create new table
        sql = "CREATE TABLE IF NOT EXISTS Stat(\n"
                + "    TypeID int PRIMARY KEY,\n"
                + "    MaxCP int,\n"
                + "    MaxHP int \n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);  // Execute the new table statement
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        // Change the file and scanner to the next file Repeat same steps
        pokemoncsv = new File(System.getProperty("user.dir") + "/"+ "tblStats.csv");
        r = new Scanner(pokemoncsv);
        sql = "";
        for(int i = 0; r.hasNext(); i++) {  // Get data from each line and insert into table
        	String[] line =  r.nextLine().split(",");
        	sql += "INSERT INTO Stat(TypeID, MaxCP, MaxHP)\n"
        			+ "values ('" + line[0] + "', '" + line[1] + "', '" + line[2] + "'); \n";
            // System.out.println(sql);  // Print out statement to error proof
            try (Connection conn = DriverManager.getConnection(url);
                    Statement stmt = conn.createStatement()) {
                stmt.execute(sql);  // execute statement
            } catch (SQLException e) {
                System.out.println(e.getMessage());  // Catch errors and print them out
            }
            sql = "";
        }

        // Repeat steps from above for last table
        sql = "CREATE TABLE IF NOT EXISTS Type(\n"
                + "    typeID int PRIMARY KEY,\n"
                + "    typeName varchar(20)\n"
                + ");";
        
        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);  // execute the new table statement
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        // Set the file and scanner to the new file and repeat steps from above
        pokemoncsv = new File(System.getProperty("user.dir") + "/"+ "tblTypes.csv");
        r = new Scanner(pokemoncsv);
        sql = "";
        for(int i = 0; r.hasNext(); i++) {  // for each line in the file get the data and insert into table
        	String[] line =  r.nextLine().split(",");
        	sql += "INSERT INTO Type(TypeID, typeName)\n"
        			+ "values ('" + line[0] + "', '" + line[1] + "'); \n";
            // System.out.println(sql);  // Print out the statement to error proof
            try (Connection conn = DriverManager.getConnection(url);
                    Statement stmt = conn.createStatement()) {
                stmt.execute(sql);  // Connect and then execute the statement
            } catch (SQLException e) {
                System.out.println(e.getMessage());  // Catch any errors and print them out
            }
            sql = "";
        }
    }
    
    
    
    
    
    //this is where we'll be running our queries
    //TODO: test if I can assign things to stuff that is not in results
    //		so I might have to have a lot of hardcoded query methods that return
    //		and assign only the different ways the user can ask for info
    //		OR if it lets me assign stuff thats not in the results, then I would only need this one method.
//    public void runQuery(String queryStatement) {
//    	try {
//			this.statement = connection.createStatement();
//			ResultSet results = statement.executeQuery(queryStatement);
//
//			while(results.next()) {
//				int id = results.getInt("TypeID");
//				String pokemonName = results.getString("Name");
//				String imageUrl = results.getString("ImageURL");
//				int primaryTypeID = results.getInt("T1ID");
//				int secondaryTypeID = results.getInt("T2ID");
//				System.out.println("Pokemon: " + pokemonName);
//				
//
//
//			}
//		} catch (SQLException e) {
//            System.out.println("Error message: " + e.getMessage());
//		}
//    }
    
    public void runStatsQuery(String queryStatement) {
    	try {
			this.statement = connection.createStatement();
			ResultSet results = statement.executeQuery(queryStatement);
			while(results.next()) {
				int id = results.getInt("TypeID");
				String pokemonName = results.getString("Name");
				if(queryStatement.contains("MaxCP")) {
					int maxCP = results.getInt("MaxCP");
					System.out.println("Pokemon: " + pokemonName + " Has a MAX CP of " + maxCP);

				} else {
				int maxHP = results.getInt("MaxHP");
				System.out.println("Pokemon: " + pokemonName + " Has a MAX HP of " + maxHP);

				}
				//TODO: add logic of changing text and var to say max CP if user wants max CP


			}
		} catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
		}
    }
    
    public void runTypeQuery(String queryStatement) {
    	try {

			this.statement = connection.createStatement();
			ResultSet results = statement.executeQuery(queryStatement);


			while(results.next()) {
				String typeName = results.getString("typeName");
				String pokemonName = results.getString("Name");
				System.out.println("Pokemon: " + pokemonName + " Has a primary type of " + typeName);
				//TODO: add logic of changing text and var to say secondary type if its secondary type

			}
		} catch (SQLException | NullPointerException e) {
            System.out.println("Error message: " + e.getMessage());
		}
    }

    
    public void closeConnection() {
    	try {
			connection.close();
		} catch (SQLException | NullPointerException e) {
            System.out.println("Error message: " + e.getMessage());
		}
    }

}
