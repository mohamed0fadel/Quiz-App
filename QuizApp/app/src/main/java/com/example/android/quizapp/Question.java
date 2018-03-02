package com.example.android.quizapp;

import java.util.ArrayList;

/**
 * Created by mohamed on 12/21/17.
 */

public class Question {

    private String text;
    private int type;            // 0 for onChoice questions | 1 for multiChoice questions | 2 for text answers
    private boolean isCorrect;
    private ArrayList<Answer> answers;

    public Question(int type){
        answers = new ArrayList<>();
        isCorrect = false;
        this.type = type;
    }

    public String getText() {return text; }

    public int getType() {return type; }

    public ArrayList<Answer> getAnswers() {return answers; }

    public void setText(String text) {this.text = text; }

    public void setType(int type) {this.type = type; }

    public void setIsCorrect(boolean result) {this.isCorrect = result; }

    public boolean isCorrect() {return isCorrect;}

    public void addAnswer(String text){
        Answer answer = new Answer(text);
        this.answers.add(answer);
    }


}
