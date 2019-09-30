import java.sql.*;
import java.io.IOException;

public class SqliteDB{
    Connection connection = null;
    Statement statement = null;
    String url = "";

    SqliteDB(String fileName) throws IOException{
    	// Code for this connection is from https://www.sqlitetutorial.net/sqlite-java/create-database/
    	 url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/" + fileName;  // Make connection URL
    	 try (Connection conn = DriverManager.getConnection(url)) {  // Try connecting to DB, create new if needed
             if (conn != null) {
                 DatabaseMetaData meta = conn.getMetaData();
                 System.out.println("The driver name is " + meta.getDriverName());
                 System.out.println("A new database has been created.");
             }
         } catch (SQLException e) {  // Catch exceptions and print the error message
             System.out.println(e.getMessage());
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
        sql = "CREATE TABLE IF NOT EXISTS Stats(\n"
                + "    TypeID integer PRIMARY KEY,\n"
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
        	sql += "INSERT INTO Stats(TypeID, MaxCP, MaxHP)\n"
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
        sql = "CREATE TABLE IF NOT EXISTS Types(\n"
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
        	sql += "INSERT INTO Types(TypeID, typeName)\n"
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
    public void runQuery(String queryStatement) {
    	try {
			this.statement = connection.createStatement();
			ResultSet results = statement.executeQuery(queryStatement);

			while(results.next()) {
				int id = results.getInt("TypeID");
				String pokemonName = results.getString("Name");
				String imageUrl = results.getString("ImageURL");
				int primaryTypeID = results.getInt("T1ID");
				int secondaryTypeID = results.getInt("T2ID");
				System.out.println("Pokemon: " + pokemonName);
				


			}
		} catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
		}
    }
    
    public void runStatsQuery(String queryStatement) {
    	try {
			this.statement = connection.createStatement();
			ResultSet results = statement.executeQuery(queryStatement);

			while(results.next()) {
				int id = results.getInt("TypeID");
				String pokemonName = results.getString("Name");
				
				if (queryStatement.contains("MaxCP")) {
					int maxCP = results.getInt("MaxCP");
					System.out.println("Pokemon: " + pokemonName + " Has a MAX CP of " + maxCP);
					
				} else if (queryStatement.contains("MaxHP")) {
					int maxHP = results.getInt("MaxHP");
					System.out.println("Pokemon: " + pokemonName + " Has a MAX HP of " + maxHP);
				}

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
				if (queryStatement.contains("t2id")) {
					System.out.println("Pokemon: " + pokemonName + " has a secondary type of " + typeName);
				} else if(queryStatement.contains("t1id")) {
					System.out.println("Pokemon: " + pokemonName + " has a primary type type of " + typeName);
				}
				
				


			}
		} catch (SQLException | NullPointerException e) {
            System.out.println("Error message: " + e.getMessage());
		}
    }

	public boolean checkExist(String queryStatement, String userIn) {
    	boolean exist = false;
		try {
			this.statement = connection.createStatement();
			ResultSet results = statement.executeQuery(queryStatement);

			while (results.next()) {
				String pokemonName = results.getString("Name");
				if (pokemonName.equals(userIn)) {
					exist = true;
				} else {
					exist = false;
				}
			}

		} catch (SQLException | NullPointerException e) {
			System.out.println("Error message: " + e.getMessage());
		}
		return exist;
	}
    
    public void closeConnection() {
    	try {
			connection.close();
		} catch (SQLException | NullPointerException e) {
            System.out.println("Error message: " + e.getMessage());
		}
    }

}
