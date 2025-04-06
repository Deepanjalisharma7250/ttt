public class TestJDBC {
    public static void main(String[] args) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Oracle JDBC Driver Loaded Successfully!");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load Oracle JDBC Driver!");
            e.printStackTrace();
        }
    }
}
