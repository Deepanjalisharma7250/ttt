import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {
    public WelcomeScreen() {
        setTitle("Welcome to QuizArena");
        setSize(600, 400); // Same size as LoginForm
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Set gradient background colors using a custom panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 182, 193); // Light Pink
                Color color2 = new Color(173, 216, 230); // Light Blue
                Color color3 = new Color(255, 255, 255); // White
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight() / 2, color2, true);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        // Welcome label with custom font and color
        JLabel welcomeLabel = new JLabel("Welcome to QuizArena", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        welcomeLabel.setForeground(new Color(75, 0, 130)); // Indigo color for contrast
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create the "Log In" button with curved edges (smaller size)
        JButton loginButton = new JButton("Log In") {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof RoundedBorder) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(getBackground());
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Curved edges
                }
                super.paintComponent(g);
            }
        };
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(new RoundedBorder(15)); // Custom rounded border
        loginButton.setPreferredSize(new Dimension(130, 35)); // Smaller button size
        loginButton.setMaximumSize(new Dimension(130, 35)); // Ensures proper alignment
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listener to open LoginForm
        loginButton.addActionListener(e -> {
            new LoginForm(); // Open LoginForm
            dispose(); // Close Welcome Screen
        });

        // Add spacing and components
        backgroundPanel.add(Box.createVerticalStrut(80)); // Space above the welcome text
        backgroundPanel.add(welcomeLabel);
        backgroundPanel.add(Box.createVerticalStrut(50)); // Space between text and button
        backgroundPanel.add(loginButton);

        add(backgroundPanel);
        setVisible(true);
    }

    // Custom rounded border class
    static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private int radius;
        RoundedBorder(int radius) {
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(c.getBackground().darker());
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    public static void main(String[] args) {
        new WelcomeScreen();
    }
}
