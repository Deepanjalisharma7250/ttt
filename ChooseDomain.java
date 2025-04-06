import javax.swing.*;
import java.awt.*;

public class ChooseDomain extends JFrame {
    private int userId; // Store the logged-in user's ID
    private String username; // Store the username for passing back

    public ChooseDomain(int userId, String username) {
        this.userId = userId; // Assign userId
        this.username = username; // Assign username

        setTitle("Select Quiz Domain");
        setSize(600, 400); // Same size as LoginForm
        setLocationRelativeTo(null); // Open in center
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(255, 160, 180)); // Different shade of pink

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Heading Label
        JLabel headingLabel = new JLabel("Choose Your Domain", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setForeground(Color.BLACK);

        // Buttons
        JButton dsaButton = new JButton("DSA Quiz");
        JButton javaButton = new JButton("Java Quiz");
        JButton pythonButton = new JButton("Python Quiz");
        JButton backButton = new JButton("Back");

        // Button Styling (same as LoginForm)
        dsaButton.setBackground(new Color(173, 216, 230)); // Light Blue
        dsaButton.setForeground(Color.BLACK);
        dsaButton.setFocusPainted(false);

        javaButton.setBackground(new Color(240, 248, 255)); // Alice Blue (almost white)
        javaButton.setForeground(Color.BLACK);
        javaButton.setFocusPainted(false);

        pythonButton.setBackground(new Color(135, 206, 250)); // Sky Blue
        pythonButton.setForeground(Color.BLACK);
        pythonButton.setFocusPainted(false);

        backButton.setBackground(new Color(255, 99, 132)); // Light Red for contrast
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);

        dsaButton.setPreferredSize(new Dimension(200, 40));
        javaButton.setPreferredSize(new Dimension(200, 40));
        pythonButton.setPreferredSize(new Dimension(200, 40));
        backButton.setPreferredSize(new Dimension(200, 40));

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.setBackground(new Color(255, 160, 180)); // Match background
        buttonPanel.add(dsaButton);
        buttonPanel.add(javaButton);
        buttonPanel.add(pythonButton);
        buttonPanel.add(backButton);

        gbc.gridy = 0;
        add(headingLabel, gbc);

        gbc.gridy = 1;
        add(buttonPanel, gbc);

        // Button actions - Open QuizApp with selected domain & pass userId
        dsaButton.addActionListener(e -> new QuizApp(userId, "DSA"));
        javaButton.addActionListener(e -> new QuizApp(userId, "Java"));
        pythonButton.addActionListener(e -> new QuizApp(userId, "Python"));

        // Back button action - Go back to UserDashboard
        backButton.addActionListener(e -> {
            new UserDashboard(userId, username);
            dispose(); // Close ChooseDomain window
        });

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChooseDomain(1, "TestUser"); // Test run
    }
}
