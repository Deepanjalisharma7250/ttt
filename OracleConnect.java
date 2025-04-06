import java.sql.*;

public class OracleConnect {
    public static void main(String[] args) {
        try {
            // Load Oracle JDBC Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Connection URL (Change it based on your database setup)
            String url = "jdbc:mysql://localhost:3306/your_database_name";  
String user = "exit";  // Use 'root' instead of 'system'
String password = "exit123";  // Your actual MySQL password


            // Connect to Oracle Database
            Connection conn = DriverManager.getConnection(url, user, password);
            
            System.out.println("Connected to Oracle 11g successfully!");

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
