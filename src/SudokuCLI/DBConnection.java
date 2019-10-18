package SudokuCLI;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/***
 * Manages the database connection elements for the game
 */

public class DBConnection
{
    private static final String JDBC_URL = "jdbc:derby:P3-Sudoku; create=true"; // Database URL
    private static final String username = "sudoku"; // DB username
    private static final String password = "pdc"; // DB password
    Connection conn = null;
    Statement statement = null;

    /***
     * Connects to the database for the project. Uses elements set up in the final
     * static variables above to establish the URL, username and password of the database.
     */

    public void connectDB(){
        try{
            this.conn = DriverManager.getConnection(this.JDBC_URL, this.username, this.password);
            System.out.println("Connected.");
            this.statement = conn.createStatement();
            this.existingTableCheck("HIGHSCORE");
            this.statement.addBatch("CREATE TABLE HIGHSCORE (PLAYERNAME VARCHAR(20), SCORE INT(5)))");
            this.statement.addBatch("INSERT INTO BOOK VALUES ('test', 0001)\n"
                    + "('unbeatable', 9999)");
            this.statement.executeBatch();

        } catch (SQLException ex){
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /***
     * Checks to make sure the table does not already exist in the database. If it does
     * it deletes it to make room for the new one.
     * @param name The name of the table to be checked by the function
     */


    public void existingTableCheck(String name)
    {
        try
        {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            String[] types = {"TABLE"};
            ResultSet rs = dbmd.getTables(null, null, null, types);
            Statement statement = this.conn.createStatement();
            while (rs.next())
            {
                String table_name = rs.getString("TABLE_NAME");
                System.out.println(table_name);
                if (table_name.equalsIgnoreCase(name))
                {
                    statement.executeUpdate("Drop table " + name);
                    System.out.println("Table " + name + " has been deleted.");
                    break;
                }
            }
            rs.close();
            statement.close();
        } catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /***
     * This method returns a high score obtained from the table
     * @return The high score from the DB table
     */

    public ResultSet getHighScore() {
        ResultSet rs = null;
        try{
            rs = this.statement.executeQuery("SELECT ASDFasdfasdfasdfasdfasdfasdf");
        } catch (SQLException ex){
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
