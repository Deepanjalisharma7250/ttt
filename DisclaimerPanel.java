import javax.swing.*;
import java.awt.*;

public class DisclaimerPanel extends JFrame {
    public DisclaimerPanel(String domain, QuizApp quizApp) {
        setTitle("Disclaimer / Note");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set background color
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JLabel heading = new JLabel("Disclaimer / Note", JLabel.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        heading.setForeground(Color.PINK);

        JTextArea message = new JTextArea(
            "1. Each question is limited to 30 seconds.\n" +
            "2. You cannot go back to a previous question.\n" +
            "3. All the best for the quiz!\n" +
            "4. Click OK to start quiz or Back to choose different domain."
        );
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        message.setForeground(Color.PINK);
        message.setBackground(Color.BLACK);
        message.setEditable(false);
        message.setOpaque(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);

        JButton startQuizButton = new JButton("OK (Start Quiz)");
        JButton backButton = new JButton("Back");

        // Styling buttons
        styleButton(startQuizButton);
        styleButton(backButton);

        startQuizButton.addActionListener(e -> {
            dispose();
            quizApp.startQuiz();
        });

        backButton.addActionListener(e -> dispose());

        buttonPanel.add(backButton);
        buttonPanel.add(startQuizButton);

        mainPanel.add(heading, BorderLayout.NORTH);
        mainPanel.add(message, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.PINK);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.PINK, 2, true));
        button.setPreferredSize(new Dimension(150, 35));
    }
}
