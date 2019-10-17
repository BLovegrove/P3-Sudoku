package SudokuCLI;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection
{
    private static final String JDBC_URL = "jdbc:derby:P3-Sudoku;create=true";
    private static final String username = "sudoku";
    private static final String password = "pdc";
    Connection conn;

    public void connectDB(){
        try{
            this.conn = DriverManager.getConnection(this.JDBC_URL, this.username, this.password);
            System.out.println("Connected.");

        } catch (SQLException ex){
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createHighScoreTable(){
        this.existingTableCheck("HIGHSCORE");

    }

    public void existingTableCheck(String name) {
    try {
        DatabaseMetaData dbmd = this.conn.getMetaData();
        String[] types = {"TABLE"};
        ResultSet rs = dbmd.getTables(null, null, null, types);
        Statement statement = this.conn.createStatement();
        while (rs.next()) {
            String table_name = rs.getString("TABLE_NAME");
            System.out.println(table_name);
            if (table_name.equalsIgnoreCase(name)) {
                statement.executeUpdate("Drop table " + name);
                System.out.println("Table " + name + " has been deleted.");
                break;
            }
        }
        rs.close();
        statement.close();
    } catch (SQLException ex) {
        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
}

















}
