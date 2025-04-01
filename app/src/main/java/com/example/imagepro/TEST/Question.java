package com.example.imagepro.TEST;

public class Question {
    private String questionText;
    private int imageAnswer1;
    private int imageAnswer2;
    private int imageAnswer3;
    private int imageAnswer4;
    private int correctAnswer;

    public Question(String questionText, int imageAnswer1, int imageAnswer2, int imageAnswer3, int imageAnswer4, int correctAnswer) {
        this.questionText = questionText;
        this.imageAnswer1 = imageAnswer1;
        this.imageAnswer2 = imageAnswer2;
        this.imageAnswer3 = imageAnswer3;
        this.imageAnswer4 = imageAnswer4;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getImageAnswer1() {
        return imageAnswer1;
    }

    public int getImageAnswer2() {
        return imageAnswer2;
    }

    public int getImageAnswer3() {
        return imageAnswer3;
    }

    public int getImageAnswer4() {
        return imageAnswer4;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}
