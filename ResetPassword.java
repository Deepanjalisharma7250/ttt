import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ResetPassword extends JFrame {
    public ResetPassword(JFrame parent) { // Accept parent frame (AdminDashboard)
        setTitle("Reset Password");
        setSize(450, 250); // Increased size
        setLocationRelativeTo(null); // Center the window
        setLayout(new GridBagLayout()); // Use GridBagLayout for better control
        getContentPane().setBackground(new Color(255, 182, 193)); // Light Pink Background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField userField = new JTextField(15); // Longer input box
        userField.setPreferredSize(new Dimension(200, 30));

        JLabel passLabel = new JLabel("New Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JPasswordField passField = new JPasswordField(15); // Longer input box
        passField.setPreferredSize(new Dimension(200, 30));

        JButton resetButton = new JButton("Reset Password");
        resetButton.setBackground(new Color(70, 130, 180)); // Blue Shade
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(0, 0, 0)); // Black
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));

        // Add components to layout
        gbc.gridx = 0; gbc.gridy = 0;
        add(userLabel, gbc);

        gbc.gridx = 1;
        add(userField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(passLabel, gbc);

        gbc.gridx = 1;
        add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(resetButton, gbc);

        gbc.gridx = 1;
        add(backButton, gbc);

        // Reset Password Logic
        resetButton.addActionListener(e -> {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement("UPDATE USERSS SET PASSWORD = ? WHERE USERNAME = ?")) {

                pstmt.setString(1, new String(passField.getPassword()));
                pstmt.setString(2, userField.getText());

                int updated = pstmt.executeUpdate();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Password Updated Successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "User Not Found!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
            }
        });

        // Back Button Action
        backButton.addActionListener(e -> {
            this.dispose(); // Close ResetPassword
            parent.setVisible(true); // Show AdminDashboard again
        });

        setVisible(true);
    }
}
