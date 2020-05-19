package com.example.android.quizapp;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QuestionAdaptor extends RecyclerView.Adapter<QuestionAdaptor.QuestionHolder> {

    private static final String TAG = "QuestionAdaptor";
    private List<Question> questions;

    public QuestionAdaptor(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item, parent, false);
        return new QuestionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
        Question question = questions.get(position);
        holder.setQuestionType(question.getType());
        holder.setQuestionNumber("Question " + (position + 1) + "/" + getItemCount());
        holder.setQuestion(question.getQuestion());
        holder.setPoints(question.getPoints());
        holder.setAnswers(question.getAnswers());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class QuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener, TextWatcher {

        public static final String TAG = "QuestionHolder";

        private TextView questionNumberTextView, questionTextView, pointsTextView;
        private LinearLayout answersView;
        private QuestionType questionType;
        private String fillInTheBlankAnswer;
        private String multipleChoiceAnswer;
        private List<String> multipleAnswersAnswer = new ArrayList<>(4);
        private boolean questionAnswered = false;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);

            questionNumberTextView = itemView.findViewById(R.id.questionNumberTextView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            pointsTextView = itemView.findViewById(R.id.pointsTextView);
            answersView = itemView.findViewById(R.id.answersView);
        }

        public void setQuestionType(QuestionType questionType) {
            this.questionType = questionType;
        }

        public void setQuestionNumber(String number) {
            this.questionNumberTextView.setText(number);
        }

        public void setQuestion(String question) {
            this.questionTextView.setText(question);
        }

        public void setPoints(int points) {
            this.pointsTextView.setText(String.valueOf(points));
        }

        private void setAnswers(List<Question.Answer> answers) {
            answersView.removeAllViews();
            if (questionType == QuestionType.MultipleChoice) {
                RadioGroup radioGroup = new RadioGroup(answersView.getContext());
                for (int i = 0; i < answers.size(); i++) {
                    RadioButton option = new RadioButton(radioGroup.getContext());

                    Question.Answer answer = answers.get(i);
                    option.setText(answer.getAnswer());
                    option.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RadioButton radioButton = (RadioButton) v;

                            if (radioButton.isChecked()) {
                                questionAnswered = true;
                            }
                            else {
                                questionAnswered = false;
                            }

                            multipleChoiceAnswer = radioButton.getText().toString();
                        }
                    });

                    radioGroup.addView(option);
                }
                answersView.addView(radioGroup);
            }

            if (questionType == QuestionType.MultipleAnswers) {
                for (Question.Answer answer : answers) {
                    CheckBox option = new CheckBox(answersView.getContext());
                    option.setText(answer.getAnswer());
                    option.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CheckBox checkBox = (CheckBox) v;

                            if (checkBox.isChecked()) {
                                questionAnswered = true;
                                multipleAnswersAnswer.add(checkBox.getText().toString());
                            }
                            else {
                                multipleAnswersAnswer.remove(checkBox.getText().toString());
                                if (multipleAnswersAnswer.size() == 0) {
                                    questionAnswered = false;
                                }
                            }
                        }
                    });

                    answersView.addView(option);
                }
            }

            if (questionType == QuestionType.FillInBlank) {
                EditText option = new EditText(answersView.getContext());
                option.setHint("Type answer here");
                option.addTextChangedListener(this);

                answersView.addView(option);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1.0f;
            params.gravity = Gravity.END;

            Button button = new Button(answersView.getContext());
            button.setText("Check Answer");
            button.setLayoutParams(params);
            button.setOnClickListener(this);

            answersView.addView(button);
        }

        @Override
        public void onClick(View view) {
            Question question = questions.get(getAdapterPosition());

            double points = 0;

            if (questionType == QuestionType.FillInBlank) {
                points = question.checkFillInBlank(fillInTheBlankAnswer);
            }
            else if (questionType == QuestionType.MultipleChoice) {
                points = question.checkMultipleChoice(multipleChoiceAnswer);
            }
            else {
                points = question.checkMultipleAnswers(multipleAnswersAnswer);
            }

            if (this.questionAnswered) {
                Toast.makeText(itemView.getContext(), "Points: " + points, Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(itemView.getContext(), "Question not answered!", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            questionAnswered = s.length() > 0;
        }

        @Override
        public void afterTextChanged(Editable s) {
            fillInTheBlankAnswer = s.toString();
        }
    }
}
