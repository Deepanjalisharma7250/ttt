import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class Scoreboard extends JFrame {
    private int userId;

    public Scoreboard(int userId) {
        this.userId = userId;

        setTitle("Scoreboard");
        setSize(500, 400);
        setLocationRelativeTo(null); // Open in center
        getContentPane().setBackground(new Color(255, 204, 229)); // Light pink background

        // Table setup
        String[] columnNames = {"Domain", "Score", "Timestamp"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.getTableHeader().setBackground(new Color(173, 216, 230)); // Light blue header
        table.getTableHeader().setForeground(Color.BLACK);
        table.setGridColor(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(table);

        // Fetch user scores from the database
        String sql = "SELECT DOMAIN, SCORE, TIMESTAMP FROM SCOREBOARD WHERE USER_ID = ? ORDER BY TIMESTAMP DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String domain = rs.getString("DOMAIN");
                int score = rs.getInt("SCORE");
                Timestamp timestamp = rs.getTimestamp("TIMESTAMP");

                model.addRow(new Object[]{domain, score, timestamp});
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Back button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(135, 206, 250)); // Sky blue
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.addActionListener(e -> {
            new UserDashboard(userId, getUsername(userId)); // Open UserDashboard
            dispose();
        });

        // Panel for layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 204, 229)); // Match background
        panel.add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 204, 229)); // Match background
        buttonPanel.add(backButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(panel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    // Fetch username from USERSS table
    private String getUsername(int userId) {
        String username = "User";
        String query = "SELECT USERNAME FROM USERSS WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("USERNAME");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static void main(String[] args) {
        new Scoreboard(1); // Example test with userId = 1
    }
}
