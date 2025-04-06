import java.sql.*;

public class MySQLConnect {
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL Database
            String url = "jdbc:mysql://localhost:3306/DEEPANJALISHARMA"; // Change 'your_database' to your actual database name
            String user = "system"; // Replace with MySQL username
            String password = "deepa@2005"; // Replace with MySQL password
            Connection conn = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to MySQL successfully!");

            // Close connection
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
