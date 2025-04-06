public class Question {
    int id;  // Unique question ID from database
    String questionText;
    String optionA, optionB, optionC, optionD;
    String correctAnswer;
    String domain;

    public Question(int id, String questionText, String optionA, String optionB, String optionC, String optionD, String correctAnswer, String domain) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.domain = domain;
    }

    public boolean isCorrect(String selectedAnswer) {
        return selectedAnswer.equalsIgnoreCase(correctAnswer);
    }
    
    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
