package com.example.android.quizapp;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {

    private String question;
    private List<Answer> availableAnswers = new ArrayList<>(4);
    private int correctAnswers = 0;
    private QuestionType type;
    private int points;

    public Question(String question, QuestionType type, int points) {
        this.question = question;
        this.type = type;
        this.points = points;
    }

    public void addAnswer(String answer, boolean isCorrect) {
        Answer a = new Answer(answer);

        if (isCorrect) {
            a.markCorrect();
            correctAnswers++;
        }

        this.availableAnswers.add(a);

        Collections.shuffle(availableAnswers);
    }

    public double checkFillInBlank(String providedAnswer) {
        boolean isCorrect = false;

        for (Answer answer : availableAnswers) {
            if (answer.checkAnswer(providedAnswer.trim())) {
                isCorrect = true;
            }
        }

        return isCorrect ? points : 0;
    }

    public double checkMultipleAnswers(List<String> providedAnswers) {
        int correct = 0;

        for (String providedAnswer : providedAnswers) {
            for (Answer answer : availableAnswers) {
                if (answer.checkAnswer(providedAnswer.trim())) {
                    correct++;
                }
            }
        }

        return ((double) points / correctAnswers) * correct;
    }

    public double checkMultipleChoice(String providedAnswer) {
        return checkFillInBlank(providedAnswer);
    }

    public List<Answer> getAnswers() {
        return availableAnswers;
    }

    @Override
    public String toString() {
        return toString();
    }

    public String getQuestion() {
        return question;
    }

    public int getPoints() {
        return points;
    }

    public QuestionType getType() {
        return type;
    }

    public class Answer {
        private String answer;
        private boolean isCorrect = false;

        public Answer(String answer) {
            this.answer = answer.trim();
        }

        public String getAnswer() {
            return this.answer;
        }

        protected void markCorrect() {
            this.isCorrect = true;
        }

        private boolean checkAnswer(String answer) {
            return this.answer.equalsIgnoreCase(answer.trim()) && this.isCorrect;
        }

        @NonNull
        @Override
        public String toString() {
            return answer;
        }
    }
}
