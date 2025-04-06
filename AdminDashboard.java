import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400); // ✅ Same size as LoginForm
        setLocationRelativeTo(null); // ✅ Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ✅ Background Color: Light Pink Shade
        getContentPane().setBackground(new Color(255, 192, 203));

        // ✅ Top Panel with "Welcome Admin"
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(255, 160, 180)); // Different Pink Shade
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 15)); // Adds space

        JLabel welcomeLabel = new JLabel("Welcome Admin");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLUE); // Blue Text
        topPanel.add(welcomeLabel);

        // ✅ Center Panel for Buttons (2 Columns)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 3 Rows, 2 Columns (Neat spacing)
        centerPanel.setBackground(new Color(255, 220, 230)); // Another Pink Shade

        JButton manageQuestions = createStyledButton("Manage Questions");
        JButton resetPassword = createStyledButton("Reset Password");
        JButton viewParticipants = createStyledButton("View Participants");
        JButton viewGraph = createStyledButton("Answer Stats");
        JButton backButton = createStyledButton("Back");
        JButton logoutButton = createStyledButton("Logout");

        // ✅ Add Buttons in 2 Columns
        centerPanel.add(manageQuestions);
        centerPanel.add(resetPassword);
        centerPanel.add(viewParticipants);
        centerPanel.add(viewGraph);
        centerPanel.add(backButton);
        centerPanel.add(logoutButton);

        // ✅ Event Listeners
        manageQuestions.addActionListener(e -> new ManageQuestions());
        resetPassword.addActionListener(e -> new ResetPassword(this));
        viewParticipants.addActionListener(e -> new ViewParticipants());
        viewGraph.addActionListener(e -> new AnswerStats());

        backButton.addActionListener(e -> {
            new LoginForm(); // ✅ Back to LoginForm
            dispose(); // Close this window
        });

        logoutButton.addActionListener(e -> {
            new LoginForm(); // ✅ Log out to LoginForm
            dispose();
        });

        // ✅ Layout Setup
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // ✅ Create Small Curved Button (Same as Welcome Screen's Log In button)
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11)); // Small Font
        button.setPreferredSize(new Dimension(120, 30)); // ✅ Small Size (Like "Log In" button)
        button.setBackground(new Color(180, 70, 90)); // ✅ Same Dark Contrast Color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createLineBorder(new Color(150, 50, 70), 2)); // ✅ Curved Border
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // ✅ Better Spacing
        return button;
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
