import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.*;

public class ViewParticipants extends JFrame {
    public ViewParticipants() {
        setTitle("View Participants");
        setSize(400, 250); // Adjusted size to match LoginForm
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout());

        // Create table model
        String[] columnNames = {"ID", "Username", "Role"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable usersTable = new JTable(tableModel);
        usersTable.setRowHeight(30); // Increase row height for better visibility
        usersTable.setBackground(new Color(255, 200, 221)); // Light Pink Background
        usersTable.setForeground(Color.BLACK); // Text color
        usersTable.setSelectionBackground(new Color(173, 216, 230)); // Light Blue selection
        usersTable.setSelectionForeground(Color.BLACK);

        // Styling table header
        JTableHeader tableHeader = usersTable.getTableHeader();
        tableHeader.setBackground(new Color(0, 102, 204)); // Blue header
        tableHeader.setForeground(Color.WHITE); // White text
        tableHeader.setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 182, 193)); // Light Pink
        JButton closeButton = new JButton("Close");
        closeButton.setBackground(new Color(0, 102, 204)); // Blue button
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.addActionListener(e -> dispose());

        buttonPanel.add(closeButton);

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT ID, USERNAME, ROLE FROM USERSS ORDER BY ID");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("ID");
                String username = rs.getString("USERNAME");
                String role = rs.getString("ROLE");

                // Ensure role is never null
                if (role == null || role.trim().isEmpty()) {
                    role = "user"; // Default to 'user' if missing
                }

                tableModel.addRow(new Object[]{id, username, role});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
