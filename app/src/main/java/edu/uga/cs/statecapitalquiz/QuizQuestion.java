package edu.uga.cs.statecapitalquiz;

public class QuizQuestion {
    private int id;              // primary key for this relation
    private int quizId;          // foreign key to Quiz
    private int questionId;      // foreign key to Question
    private boolean isCorrect;   // whether answer was correct

    public QuizQuestion() {
        id = -1;
        quizId = -1;
        questionId = -1;
        isCorrect = false;
    }

    public QuizQuestion(int quizId, int questionId, boolean isCorrect) {
        this.quizId = quizId;
        this.questionId = questionId;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getQuizId() {
        return quizId;
    }
    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getQuestionId() {
        return questionId;
    }
    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return isCorrect;
    }
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
