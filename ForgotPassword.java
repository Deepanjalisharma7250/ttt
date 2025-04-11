import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ForgotPassword extends JFrame {
    private JTextField usernameField;
    private JPasswordField newPasswordField;

    public ForgotPassword() {
        setTitle("Forgot Password - QuizArena");
        setSize(400, 220);
        setLocationRelativeTo(null); // Center the window
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(255, 204, 229)); // Light pink

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Reset Your Password", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setForeground(Color.BLACK);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(heading, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);

        usernameField = new JTextField();
        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("New Password:"), gbc);

        newPasswordField = new JPasswordField();
        gbc.gridx = 1;
        add(newPasswordField, gbc);

        JButton resetBtn = new JButton("Reset Password");
        resetBtn.setBackground(new Color(135, 206, 250)); // Sky blue
        resetBtn.setForeground(Color.BLACK);
        resetBtn.setFocusPainted(false);

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(resetBtn, gbc);

        resetBtn.addActionListener(e -> resetPassword());

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void resetPassword() {
        String username = usernameField.getText().trim();
        String newPassword = new String(newPasswordField.getPassword()).trim();

        if (username.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE USERSS SET PASSWORD = ? WHERE USERNAME = ?")) {

            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            int updated = stmt.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "Password reset successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ForgotPassword();
    }
}
