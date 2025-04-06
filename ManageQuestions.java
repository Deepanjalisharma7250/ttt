import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ManageQuestions extends JFrame {
    private JTextField questionField;
    private JButton addButton, updateButton, deleteButton, viewButton, backButton;

    public ManageQuestions() {
        setTitle("Manage Questions");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.PINK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel questionLabel = new JLabel("Enter Question:");
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        questionField = new JTextField(20);

        addButton = createStyledButton("Add Question");
        updateButton = createStyledButton("Update Question");
        deleteButton = createStyledButton("Delete Question");
        viewButton = createStyledButton("View Questions");
        backButton = createStyledButton("Back");

        gbc.gridy = 0; add(questionLabel, gbc);
        gbc.gridy = 1; add(questionField, gbc);
        gbc.gridy = 2; add(addButton, gbc);
        gbc.gridy = 3; add(updateButton, gbc);
        gbc.gridy = 4; add(deleteButton, gbc);
        gbc.gridy = 5; add(viewButton, gbc);
        gbc.gridy = 6; add(backButton, gbc);

        // Button Actions
        addButton.addActionListener(e -> handleDatabaseAction("add"));
        updateButton.addActionListener(e -> handleDatabaseAction("update"));
        deleteButton.addActionListener(e -> handleDatabaseAction("delete"));
        viewButton.addActionListener(e -> viewQuestions());
        backButton.addActionListener(e -> {
            new AdminDashboard();
            dispose();
        });

        setVisible(true);
    }

    private void handleDatabaseAction(String action) {
        String questionText = questionField.getText().trim();
        if (questionText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a question.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            PreparedStatement pstmt = null;
            if (action.equals("add")) {
    String domain = JOptionPane.showInputDialog(this, "Enter Domain (DSA/Java/Python):");
    if (domain == null || domain.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Domain cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }
    pstmt = conn.prepareStatement("INSERT INTO QUESTIONSS (QUESTION_TEXT, DOMAIN) VALUES (?, ?)");
    pstmt.setString(1, questionText);
    pstmt.setString(2, domain);
}
 else if (action.equals("update")) {
                String newQuestion = JOptionPane.showInputDialog(this, "Enter new question:");
                if (newQuestion == null || newQuestion.trim().isEmpty()) return;
                pstmt = conn.prepareStatement("UPDATE QUESTIONSS SET QUESTION_TEXT = ? WHERE QUESTION_TEXT = ?");
                pstmt.setString(1, newQuestion);
                pstmt.setString(2, questionText);
            } else if (action.equals("delete")) {
                pstmt = conn.prepareStatement("DELETE FROM QUESTIONSS WHERE QUESTION_TEXT = ?");
                pstmt.setString(1, questionText);
            }

            if (pstmt != null) {
                int affectedRows = pstmt.executeUpdate();
                JOptionPane.showMessageDialog(this, action + " successful! " + affectedRows + " rows affected.");
                pstmt.close();
            }

            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewQuestions() {
        try {
            Connection conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this, "Database Connection Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String query = "SELECT QUESTION_TEXT FROM QUESTIONSS";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder questionsList = new StringBuilder();
            while (rs.next()) {
                questionsList.append(rs.getString("QUESTION_TEXT")).append("\n");
            }

            if (questionsList.length() == 0) {
                JOptionPane.showMessageDialog(this, "No Questions Found!", "Info", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JTextArea textArea = new JTextArea(questionsList.toString());
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(300, 200));
                JOptionPane.showMessageDialog(this, scrollPane, "All Questions", JOptionPane.INFORMATION_MESSAGE);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(30, 144, 255));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setPreferredSize(new Dimension(200, 30));
        return button;
    }

    public static void main(String[] args) {
        new ManageQuestions();
    }
}
