import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Update if needed
    private static final String USER = "DEEPANJALISHARMA"; // Replace with your Oracle username
    private static final String PASSWORD = "Deepa@2005"; // Replace with your Oracle password

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver"); // Load Oracle JDBC Driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Oracle JDBC Driver not found!");
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // Return null if connection fails
        }
    }
}
