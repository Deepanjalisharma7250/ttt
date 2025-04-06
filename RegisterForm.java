import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public RegisterForm() {
        setTitle("User Registration");
        setSize(600, 400); // Same size as LoginForm
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        // Create panel with updated background color
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 192, 203)); // Updated pastel pink
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title Label
        JLabel titleLabel = new JLabel("Register for QuizArena");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Username Label & Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLUE);
        usernameField = new JTextField(20);

        // Password Label & Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.RED);
        passwordField = new JPasswordField(20);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(135, 206, 250)); // Light blue
        registerButton.setForeground(Color.BLACK);

        // Back Button
        backButton = new JButton("Back");
        backButton.setBackground(new Color(135, 206, 250)); // Light blue
        backButton.setForeground(Color.BLACK);

        // Adding components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(255, 192, 203)); // Match background color
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, gbc);

        // Button Actions
        registerButton.addActionListener(e -> registerUser());
        backButton.addActionListener(e -> goBackToLogin());

        add(panel);
        setVisible(true);
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO USERSS (USERNAME, PASSWORD, ROLE) VALUES (?, ?, 'user')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void goBackToLogin() {
        new LoginForm(); // Open LoginForm
        dispose(); // Close RegisterForm
    }

    public static void main(String[] args) {
        new RegisterForm();
    }
}
