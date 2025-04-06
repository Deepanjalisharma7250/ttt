import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerStats extends JFrame {
    private List<UserStats> userStatsList = new ArrayList<>();

    public AnswerStats() {
        setTitle("Answer Statistics");
        setSize(600, 500); // Adjusted to match LoginForm size
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout());

        fetchAnswerStats(); // Fetch user rankings from DB

        JPanel graphPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.PINK);

                if (userStatsList.isEmpty()) {
                    g.setColor(Color.BLACK);
                    g.drawString("No Data Available", 250, 250);
                    return;
                }

                int barWidth = 50;
                int spacing = 80;
                int startX = 80;
                int baseY = 350;
                int maxHeight = 200;

                // Find max correct answers for dynamic scaling
                int maxCorrect = userStatsList.stream().mapToInt(u -> u.correctAnswers).max().orElse(1);

                for (int i = 0; i < userStatsList.size(); i++) {
                    UserStats user = userStatsList.get(i);
                    int correctHeight = (int) (((double) user.correctAnswers / maxCorrect) * maxHeight);
                    int wrongHeight = (int) (((double) user.wrongAnswers / maxCorrect) * maxHeight);

                    // Draw correct answer bar (Green)
                    g.setColor(Color.GREEN);
                    g.fillRect(startX + i * spacing, baseY - correctHeight, barWidth, correctHeight);

                    // Draw wrong answer bar (Red)
                    g.setColor(Color.RED);
                    g.fillRect(startX + i * spacing + barWidth + 10, baseY - wrongHeight, barWidth, wrongHeight);

                    // Draw labels
                    g.setColor(Color.BLACK);
                    g.drawString(user.username, startX + i * spacing, baseY + 20);
                    g.drawString(user.correctAnswers + " ✔", startX + i * spacing, baseY - correctHeight - 5);
                    g.drawString(user.wrongAnswers + " ✖", startX + i * spacing + barWidth + 10, baseY - wrongHeight - 5);
                }
            }
        };

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current window
                new AdminDashboard(); // Open AdminDashboard (Ensure it exists)
            }
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);

        add(graphPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void fetchAnswerStats() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT USERNAME, CORRECT_ANSWERS, WRONG_ANSWERS FROM USERSS ORDER BY CORRECT_ANSWERS DESC");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String username = rs.getString("USERNAME");
                int correct = rs.getInt("CORRECT_ANSWERS");
                int wrong = rs.getInt("WRONG_ANSWERS");
                userStatsList.add(new UserStats(username, correct, wrong));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class UserStats {
        String username;
        int correctAnswers;
        int wrongAnswers;

        public UserStats(String username, int correctAnswers, int wrongAnswers) {
            this.username = username;
            this.correctAnswers = correctAnswers;
            this.wrongAnswers = wrongAnswers;
        }
    }

    public static void main(String[] args) {
        new AnswerStats();
    }
}
