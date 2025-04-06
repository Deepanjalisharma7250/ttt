import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class QuizApp extends JFrame {
    private int userId;
    private String domain;
    private ArrayList<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    private JLabel questionLabel, timerLabel;
    private JRadioButton optionA, optionB, optionC, optionD;
    private ButtonGroup optionsGroup;
    private JButton nextButton, submitButton;

    private Timer timer;
    private int timeLeft = 30;

    public QuizApp(int userId, String domain) {
        this.userId = userId;
        this.domain = domain;

        // Show Disclaimer Before Starting the Quiz
        new DisclaimerPanel(domain, this).setVisible(true);
    }

    public void startQuiz() {
        questions = new ArrayList<>();
        setTitle("Quiz - " + domain);
        setSize(600, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(255, 204, 229));
        setLayout(new BorderLayout());

        if (!loadQuestions()) {
            JOptionPane.showMessageDialog(this, "No questions found for this domain!", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        JPanel quizPanel = new JPanel(new GridLayout(6, 1));
        quizPanel.setBackground(new Color(255, 204, 229));

        questionLabel = new JLabel("Question", SwingConstants.CENTER);
        quizPanel.add(questionLabel);

        optionA = new JRadioButton();
        optionB = new JRadioButton();
        optionC = new JRadioButton();
        optionD = new JRadioButton();

        optionsGroup = new ButtonGroup();
        optionsGroup.add(optionA);
        optionsGroup.add(optionB);
        optionsGroup.add(optionC);
        optionsGroup.add(optionD);

        quizPanel.add(optionA);
        quizPanel.add(optionB);
        quizPanel.add(optionC);
        quizPanel.add(optionD);

        timerLabel = new JLabel("Time Left: 30s", SwingConstants.CENTER);
        quizPanel.add(timerLabel);

        nextButton = new JButton("Next");
        nextButton.setBackground(new Color(135, 206, 250));
        nextButton.addActionListener(e -> nextQuestion());

        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(135, 206, 250));
        submitButton.addActionListener(e -> submitQuiz());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        add(quizPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        displayQuestion();
        startTimer();
    }
    
    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
        @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerLabel.setText("Time Left: " + timeLeft + "s");
                } else {
                    timer.stop();
                    nextQuestion(); // Auto move to the next question when time runs out
                }
            }
        });
        timer.start();
    }

    
    private boolean loadQuestions() {
        String sql = "SELECT ID, QUESTION_TEXT, OPTION_A, OPTION_B, OPTION_C, OPTION_D, CORRECT_OPTION FROM QUESTIONSS WHERE DOMAIN = ? ORDER BY DBMS_RANDOM.VALUE";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, domain);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String questionText = rs.getString("QUESTION_TEXT");
                String optA = rs.getString("OPTION_A");
                String optB = rs.getString("OPTION_B");
                String optC = rs.getString("OPTION_C");
                String optD = rs.getString("OPTION_D");
                String correctAnswer = rs.getString("CORRECT_OPTION");

                System.out.println("Loaded Question: " + questionText);
                System.out.println("Correct Answer: " + correctAnswer);
                
                questions.add(new Question(id, questionText, optA, optB, optC, optD, correctAnswer, domain));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: Unable to load questions!", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }

        return !questions.isEmpty();
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);
            questionLabel.setText(q.questionText);
            optionA.setText(q.optionA);
            optionB.setText(q.optionB);
            optionC.setText(q.optionC);
            optionD.setText(q.optionD);

            optionsGroup.clearSelection();
            timeLeft = 30;
        } else {
            submitQuiz();
        }
    }

    private void nextQuestion() {
        checkAnswer();
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            displayQuestion();
        } else {
            submitQuiz();
        }
    }
    
    private String getUsername(int userId) {
        String username = null;
        String query = "SELECT USERNAME FROM USERSS WHERE ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
             stmt.setInt(1, userId);
             ResultSet rs = stmt.executeQuery();

             if (rs.next()) {
                 username = rs.getString("USERNAME");
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }

    
    private void checkAnswer() {
        if (currentQuestionIndex >= questions.size()) return;

        Question q = questions.get(currentQuestionIndex);
        String selectedOption = getSelectedOption();
        String correctAnswer = q.correctAnswer.trim();
        String username = getUsername(userId);

        System.out.println("Question: " + q.questionText);
        System.out.println("Selected Answer: " + selectedOption);
        System.out.println("Correct Answer: " + correctAnswer);
        
        if (selectedOption == null) return; // Skip if no option is selecte
        
        if (selectedOption.equalsIgnoreCase(correctAnswer)) {
            System.out.println("Correct Answer! Increasing Score.");
            score++;
            updateAnswerStats(true);
        } else {
            System.out.println("Wrong Answer.");
            updateAnswerStats(false);
             
        }
    }

    private void updateAnswerStats(boolean isCorrect) {
        String query = isCorrect 
            ? "UPDATE USERSS SET CORRECT_ANSWERS = CORRECT_ANSWERS + 1 WHERE ID = ?" 
            : "UPDATE USERSS SET WRONG_ANSWERS = WRONG_ANSWERS + 1 WHERE ID = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getSelectedOption() {
        if (optionA.isSelected()) return optionA.getText();
        if (optionB.isSelected()) return optionB.getText();
        if (optionC.isSelected()) return optionC.getText();
        if (optionD.isSelected()) return optionD.getText();
        return null;
    }

    private void submitQuiz() {
        checkAnswer();
        timer.stop();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE USERSS SET SCORE = ? WHERE ID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, score);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, "Quiz Completed! Your Score: " + score, "Result", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
