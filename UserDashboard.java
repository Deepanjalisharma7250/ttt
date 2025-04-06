import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {
    private int userId;
    private String username;

    public UserDashboard(int userId, String username) {
        this.userId = userId;
        this.username = username;

        setTitle("User Dashboard");
        setSize(600, 400); // Same size as LoginForm
        setLocationRelativeTo(null); // Center the window
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(255, 182, 193)); // Light pink background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.BLACK);

        // Buttons
        JButton chooseQuiz = new JButton("Choose Quiz Domain");
        JButton viewScoreboard = new JButton("View Scoreboard");
        JButton logoutButton = new JButton("Logout");

        // Button Styling
        chooseQuiz.setBackground(new Color(173, 216, 230)); // Light Blue
        chooseQuiz.setForeground(Color.BLACK);
        chooseQuiz.setFocusPainted(false);

        viewScoreboard.setBackground(new Color(240, 248, 255)); // Alice Blue
        viewScoreboard.setForeground(Color.BLACK);
        viewScoreboard.setFocusPainted(false);

        logoutButton.setBackground(new Color(135, 206, 250)); // Sky Blue
        logoutButton.setForeground(Color.BLACK);
        logoutButton.setFocusPainted(false);

        chooseQuiz.setPreferredSize(new Dimension(200, 40));
        viewScoreboard.setPreferredSize(new Dimension(200, 40));
        logoutButton.setPreferredSize(new Dimension(200, 40));

        // Add Components
        gbc.gridy = 0;
        add(welcomeLabel, gbc);

        gbc.gridy = 1;
        add(chooseQuiz, gbc);

        gbc.gridy = 2;
        add(viewScoreboard, gbc);

        gbc.gridy = 3;
        add(logoutButton, gbc);

        // Button Actions
        chooseQuiz.addActionListener(e -> new ChooseDomain(userId, username));
        viewScoreboard.addActionListener(e -> new Scoreboard(userId));
        logoutButton.addActionListener(e -> {
            new LoginForm();
            dispose();
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new UserDashboard(1, "TestUser");
    }
}
