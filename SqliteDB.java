import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class SqliteDB{
    Connection connection = null;
    Statement statement = null;

    SqliteDB(){
        //we connect to db here
        try {
            Class.forName("org.sqlite.JDBC");
            //warmup.sqlite is the name of our database .sqlite file
            connection=DriverManager.getConnection("jdbc:sqlite:warmup.sqlite");
            //if we got here, we connected
            System.out.println("Connection successful");
//            connection.close();
        } catch (Exception e) {
            System.out.println("Error message: " + e.getMessage());
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

    
    public void closeConnection() {
    	try {
			connection.close();
		} catch (SQLException e) {
            System.out.println("Error message: " + e.getMessage());
		}
    }

}