package com.example.android.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        initializeQuestions();
        initRecyclerView();
    }

    private void initRecyclerView() {
        QuestionAdaptor movieAdapter = new QuestionAdaptor(questions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(movieAdapter);
    }

    private void initializeQuestions() {
        questions = new ArrayList<>();

        Question question1 = new Question("What is 2 + 2 = ?", QuestionType.MultipleChoice, 10);
        question1.addAnswer(String.valueOf(4), true);
        question1.addAnswer(String.valueOf(0), false);
        question1.addAnswer(String.valueOf(6), false);
        question1.addAnswer(String.valueOf(8), false);

        Question question2 = new Question("What is the capital of Romania?", QuestionType.MultipleChoice, 10);
        question2.addAnswer("Madrid", false);
        question2.addAnswer("Rome", false);
        question2.addAnswer("Paris", false);
        question2.addAnswer("Bucharest", true);

        Question question3 = new Question("What is 4 + 4 = ?", QuestionType.MultipleChoice, 10);
        question3.addAnswer(String.valueOf(6), false);
        question3.addAnswer(String.valueOf(0), false);
        question3.addAnswer(String.valueOf(5), false);
        question3.addAnswer(String.valueOf(8), true);

        Question question4 = new Question("What are the synonyms of 'available'?", QuestionType.MultipleAnswers, 10);
        question4.addAnswer("obtainable", true);
        question4.addAnswer("work", false);
        question4.addAnswer("accessible", true);
        question4.addAnswer("question", false);

        Question question5 = new Question("What are the synonyms of 'result'?", QuestionType.MultipleAnswers, 10);
        question5.addAnswer("consequence", true);
        question5.addAnswer("work", false);
        question5.addAnswer("accessible", false);
        question5.addAnswer("question", false);

        Question question6 = new Question("What are the synonyms of 'search'?", QuestionType.MultipleAnswers, 10);
        question6.addAnswer("scout", true);
        question6.addAnswer("look", true);
        question6.addAnswer("explore", true);
        question6.addAnswer("hunt", true);

        Question question7 = new Question("What are the synonyms of 'trust'?", QuestionType.MultipleAnswers, 10);
        question7.addAnswer("confidence", true);
        question7.addAnswer("look", false);
        question7.addAnswer("explore", false);
        question7.addAnswer("hunt", false);

        Question question8 = new Question("______ is the capital of Spain", QuestionType.FillInBlank, 10);
        question8.addAnswer("Madrid", true);

        Question question9 = new Question("______ is the capital of France", QuestionType.FillInBlank, 10);
        question9.addAnswer("Paris", true);

        Question question10 = new Question("______ is the capital of Romania", QuestionType.FillInBlank, 10);
        question10.addAnswer("Bucharest", true);

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);
        questions.add(question7);
        questions.add(question8);
        questions.add(question9);
        questions.add(question10);

        Collections.shuffle(questions);
    }
}
