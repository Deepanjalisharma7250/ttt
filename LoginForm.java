import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JRadioButton userButton, adminButton;
    private JButton loginButton, cancelButton, helpButton, registerButton, backButton;
    private ButtonGroup roleGroup;

    public LoginForm() {
        setTitle("Welcome to QuizArena - Login");
        setSize(600, 400); // Same size as LoginGUI
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 182, 193)); // Light pink
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Welcome to QuizArena!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.BLUE);
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.RED);
        passwordField = new JPasswordField(20);

        userButton = new JRadioButton("Login as User", true);
        adminButton = new JRadioButton("Login as Admin");
        roleGroup = new ButtonGroup();
        roleGroup.add(userButton);
        roleGroup.add(adminButton);

        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        helpButton = new JButton("Help");
        backButton = new JButton("Back");

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
        panel.add(userButton, gbc);
        gbc.gridx = 1;
        panel.add(adminButton, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        panel.add(buttonPanel, gbc);

        gbc.gridy++;
        JPanel extraButtonPanel = new JPanel(new FlowLayout());
        extraButtonPanel.add(cancelButton);
        extraButtonPanel.add(helpButton);
        extraButtonPanel.add(backButton);
        panel.add(extraButtonPanel, gbc);

        // ✅ Forgot Password label
        JLabel forgotLabel = new JLabel("<HTML><U>Forgot Password?</U></HTML>");
        forgotLabel.setForeground(Color.BLUE);
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ForgotPassword(); // open forgot password window
            }
        });

        gbc.gridy++;
        panel.add(forgotLabel, gbc); // Add below buttons

        // Button Actions
        loginButton.addActionListener(e -> login());
        registerButton.addActionListener(e -> new RegisterForm());
        cancelButton.addActionListener(e -> System.exit(0));
        helpButton.addActionListener(e -> showHelp());
        backButton.addActionListener(e -> goBackToWelcome());

        add(panel);
        setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        boolean isAdmin = adminButton.isSelected();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Fields cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT ID FROM USERSS WHERE USERNAME = ? AND PASSWORD = ?")) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("ID");

                if (isAdmin) {
                    if (username.equals("admin")) {
                        JOptionPane.showMessageDialog(this, "Admin Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new AdminDashboard();
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Admin Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "User Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new UserDashboard(userId, username);
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showHelp() {
        String helpMessage = "Help Guide:\n"
                + "- Enter your username and password.\n"
                + "- Select 'Login as User' or 'Login as Admin'.\n"
                + "- Click 'Login' to proceed.\n"
                + "- Click 'Register' to create a new account.\n"
                + "- Click 'Cancel' to exit.\n"
                + "- Forgot password? Click the link below.\n\n"
                + "Common Issues:\n"
                + "1. Make sure Caps Lock is off.\n"
                + "2. Use correct credentials.\n"
                + "3. Contact support if login fails.";

        JOptionPane.showMessageDialog(this, helpMessage, "Help", JOptionPane.INFORMATION_MESSAGE);
    }

    private void goBackToWelcome() {
        new WelcomeScreen(); // Replace with your welcome screen
        dispose();
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}
