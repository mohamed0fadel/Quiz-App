package com.example.android.quizapp;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // the answers views
    RadioGroup radioGroup;
    EditText editText;
    LinearLayout linearLayoutCheckBoxs;
    private int currentQuestion = 0;
    private int finalResult = 0;
    ArrayList<Question> questionArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initializing the questions views
        radioGroup = (RadioGroup)findViewById(R.id.radio);
        editText = (EditText)findViewById(R.id.edt_answer);
        linearLayoutCheckBoxs = (LinearLayout)findViewById(R.id.check_box_layout);
        questionArrayList = new ArrayList<>();
        startQuiz();


        // action listner for the RadioGroup
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (currentQuestion){
                    case 0:
                        if(i == R.id.radio_button3)
                            questionArrayList.get(currentQuestion).setIsCorrect(true);
                        break;
                    case 1:
                        if(i == R.id.radio_button4)
                            questionArrayList.get(currentQuestion).setIsCorrect(true);
                        break;
                    case 2:
                        if(i == R.id.radio_button2)
                            questionArrayList.get(currentQuestion).setIsCorrect(true);
                        break;
                    case 3:
                        if(i == R.id.radio_button1)
                            questionArrayList.get(currentQuestion).setIsCorrect(true);
                        break;
                }
            }
        });
    }

    /**
     * stats the quiz for the first time
     */
    private void startQuiz(){
        fillData();
        initializeViews();
    }

    /**
     * starts the first question and updates the question number
     */
    private void initializeViews(){
        startOneChoiceQuestion();
        updateQuestionNumber();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * starts question from type 0 one choice answer question
     */
    private void startOneChoiceQuestion(){
        questionOneChoice();
        displayQuestionText(questionArrayList.get(currentQuestion).getText());
        displayOneChoiceQuestionAnswers(questionArrayList.get(currentQuestion));
    }

    /**
     * starts question from type 1 multi choice answer question
     */
    private void startMultiChoiceQuestion(){
        questionMultiChoice();
        displayQuestionText(questionArrayList.get(currentQuestion).getText());
        displayMultiChoiceQuestionAnswers(questionArrayList.get(currentQuestion));
    }

    /**
     * starts question from type 2 text answer question
     */
    private void startTextQuestion(){
        questionTextAnswer();
        displayQuestionText(questionArrayList.get(currentQuestion).getText());

    }

    /**
     * updates the question number textview to display the cureent question number
     */
    private void updateQuestionNumber(){
        TextView questionNumber = (TextView)findViewById(R.id.txt_question_number);
        questionNumber.setText(getString(R.string.question_number,
                currentQuestion+1, questionArrayList.size()));
    }

    /**
     * displays the cureent questiion text
     * @param text : question text to display
     */
    private void displayQuestionText(String text){
        TextView questionTextView = (TextView)findViewById(R.id.txt_question);
        questionTextView.setVisibility(View.VISIBLE);
        questionTextView.setText(text);
    }

    /**
     * displays the choices for one answer question
     * @param question : the cureent question
     */
    private void displayOneChoiceQuestionAnswers(Question question){
        RadioButton radioButton1 = (RadioButton)findViewById(R.id.radio_button1);
        RadioButton radioButton2 = (RadioButton)findViewById(R.id.radio_button2);
        RadioButton radioButton3 = (RadioButton)findViewById(R.id.radio_button3);
        RadioButton radioButton4 = (RadioButton)findViewById(R.id.radio_button4);
        /*
            there was problem with unchecking the radio bautton after moving to the next question
            so i added this extra radio button to check after moving to the next question
            and it's visiblity is alaws GONE
         */
        RadioButton radioButton5 = (RadioButton)findViewById(R.id.radio_button5);

        radioButton5.setSelected(true);
        radioButton5.setChecked(true);

        ArrayList<Answer> cureentAnswers = new ArrayList<>();
        cureentAnswers = question.getAnswers();
        radioButton1.setText(cureentAnswers.get(0).getText());
        radioButton2.setText(cureentAnswers.get(1).getText());
        radioButton3.setText(cureentAnswers.get(2).getText());
        radioButton4.setText(cureentAnswers.get(3).getText());
    }

    /**
     * displays the answwers when the current question is multible answers question
     * @param question the current question
     */
    private void displayMultiChoiceQuestionAnswers(Question question){
        CheckBox checkBox1 = (CheckBox)findViewById(R.id.checkbox1);
        CheckBox checkBox2 = (CheckBox)findViewById(R.id.checkbox2);
        CheckBox checkBox3 = (CheckBox)findViewById(R.id.checkbox3);
        CheckBox checkBox4 = (CheckBox)findViewById(R.id.checkbox4);

        // unchecking all the checkboxs when moving to the nxt question
        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        ArrayList<Answer> curentAnswers = new ArrayList<>();
        curentAnswers = question.getAnswers();
        checkBox1.setText(curentAnswers.get(0).getText());
        checkBox2.setText(curentAnswers.get(1).getText());
        checkBox3.setText(curentAnswers.get(2).getText());
        checkBox4.setText(curentAnswers.get(3).getText());
    }


    /**
     * checks if the user have chosed answer from the answer
     * @return false if no answer is checked otherwise returns true
     */
    private boolean checkQuestionAnswered(){
        RadioButton radioButton1 = (RadioButton)findViewById(R.id.radio_button1);
        RadioButton radioButton2 = (RadioButton)findViewById(R.id.radio_button2);
        RadioButton radioButton3 = (RadioButton)findViewById(R.id.radio_button3);
        RadioButton radioButton4 = (RadioButton)findViewById(R.id.radio_button4);
        if(radioButton1.isChecked())
            return true;
        else if(radioButton2.isChecked())
            return true;
        else if(radioButton3.isChecked())
            return true;
        else if(radioButton4.isChecked())
            return true;
        else
            return false;
    }

    /**
     * moves to the next question and diplays the final result after the last question
     * @param view
     */

    public void nextQuestion(View view) {

        if (checkQuestionAnswered()) {
            if (questionArrayList.get(currentQuestion).getType() == 1)
                checkMultiChoiceQuestionIsCorrect();
            if (questionArrayList.get(currentQuestion).getType() == 2)
                checkTextQuestionIsCorrect();

            currentQuestion++;
            if(currentQuestion > -1){
                TextView previousQuestionView = (TextView)findViewById(R.id.txt_previous);
                previousQuestionView.setVisibility(View.VISIBLE);
            }
            if (currentQuestion < questionArrayList.size()) {
                switch (questionArrayList.get(currentQuestion).getType()) {
                    case 0:
                        if(currentQuestion == questionArrayList.size()-1){
                            TextView textView = (TextView)findViewById(R.id.txt_next);
                            textView.setText(getString(R.string.finish));
                        }
                        updateQuestionNumber();
                        startOneChoiceQuestion();
                        break;
                    case 1:
                        if(currentQuestion == questionArrayList.size()-1){
                            TextView textView = (TextView)findViewById(R.id.txt_next);
                            textView.setText(getString(R.string.finish));
                        }
                        updateQuestionNumber();
                        startMultiChoiceQuestion();
                        break;
                    case 2:
                        if(currentQuestion == questionArrayList.size()-1){
                            TextView textView = (TextView)findViewById(R.id.txt_next);
                            textView.setText(getString(R.string.finish));
                        }
                        updateQuestionNumber();
                        startTextQuestion();
                        break;
                }
            } else {
                finalResult = countCorrectAnswers(questionArrayList);
                Toast.makeText(this, String.valueOf(finalResult) + " of " + questionArrayList.size(),
                        Toast.LENGTH_SHORT).show();
                resetValues(questionArrayList);
                finalResult = 0;
                TextView textNext = (TextView)findViewById(R.id.txt_next);
                textNext.setVisibility(View.GONE);
                TextView textPrevious = (TextView)findViewById(R.id.txt_previous);
                textPrevious.setVisibility(View.GONE);
                TextView textRestart = (TextView)findViewById(R.id.txt_restart);
                textRestart.setVisibility(View.VISIBLE);

            }
        }

    }


    /**
     * moves to the previous question
     * @param view
     */
    public void previousQuestion(View view) {
        currentQuestion--;

        if(currentQuestion == 0){
            TextView textRestart = (TextView)findViewById(R.id.txt_previous);
            textRestart.setVisibility(View.GONE);
        }

        if(currentQuestion < questionArrayList.size()-1){
            TextView textView = (TextView)findViewById(R.id.txt_next);
            textView.setText(getString(R.string.next));
        }

        if(questionArrayList.get(currentQuestion).isCorrect())
            finalResult--;


        switch (questionArrayList.get(currentQuestion).getType()) {
            case 0:
                if(currentQuestion == questionArrayList.size()-1){
                    TextView textNext = (TextView)findViewById(R.id.txt_next);
                    textNext.setText(getString(R.string.finish));
                }
                updateQuestionNumber();
                startOneChoiceQuestion();
                break;
            case 1:
                if(currentQuestion == questionArrayList.size()-1){
                    TextView textNext = (TextView)findViewById(R.id.txt_next);
                    textNext.setText(getString(R.string.finish));
                }
                updateQuestionNumber();
                startMultiChoiceQuestion();
                break;
            case 2:
                if(currentQuestion == questionArrayList.size()-1){
                    TextView textNext = (TextView)findViewById(R.id.txt_next);
                    textNext.setText(getString(R.string.finish));
                }
                updateQuestionNumber();
                startTextQuestion();
                break;
        }
    }

    /**
     * restarts the quiz
     * @param view
     */
    public void restartQuestion(View view) {
        currentQuestion = 0;
        initializeViews();
        TextView textNext = (TextView)findViewById(R.id.txt_next);
        textNext.setVisibility(View.VISIBLE);
        textNext.setText(getString(R.string.next));
        TextView textRestart = (TextView)findViewById(R.id.txt_restart);
        textRestart.setVisibility(View.GONE);
    }

    /**
     * counts the number of correctly answered questions
     * @param questions all the questions in the questions array list
     * @return int the score
     */
    private int countCorrectAnswers(ArrayList<Question> questions){
        int count = 0;
        for (Question question : questions){
            if(question.isCorrect())
                count++;
        }
        return count;
    }

    /**
     * checks the score of one multi choice question
     */

    private void checkMultiChoiceQuestionIsCorrect(){
        CheckBox checkBox1 = (CheckBox)findViewById(R.id.checkbox1);
        CheckBox checkBox2 = (CheckBox)findViewById(R.id.checkbox2);
        CheckBox checkBox3 = (CheckBox)findViewById(R.id.checkbox3);
        CheckBox checkBox4 = (CheckBox)findViewById(R.id.checkbox4);
        switch (currentQuestion){
            case 4:
                if(checkBox1.isChecked() && checkBox3.isChecked() &&
                        !checkBox2.isChecked() && !checkBox4.isChecked())
                    questionArrayList.get(currentQuestion).setIsCorrect(true);
                break;
            case 5:
                if(checkBox1.isChecked() && checkBox2.isChecked() &&
                        !checkBox3.isChecked() && !checkBox4.isChecked())
                    questionArrayList.get(currentQuestion).setIsCorrect(true);
                break;
        }
    }

    /**
     * checks the answer of text question answer
     */

    private void checkTextQuestionIsCorrect(){
        if(currentQuestion == 6)
           questionArrayList.get(currentQuestion)
                   .setIsCorrect(editText.getText().toString().trim().equals("ImageView"));
        if(currentQuestion == 7)
            questionArrayList.get(currentQuestion)
                    .setIsCorrect(editText.getText().toString().trim().equals("TextView"));
    }

    private void resetValues(ArrayList<Question> arrayList){
        for(Question question : arrayList){
            question.setIsCorrect(false);
        }
    }


    /**
     * makes the RadioGroup visible and hides the other view
     */
    private void questionOneChoice(){
        radioGroup.setVisibility(View.VISIBLE);
        linearLayoutCheckBoxs.setVisibility(View.GONE);
        editText.setVisibility(View.GONE);
    }

    /**
     * makes the CheckBox lyout to visible and hides the other view
     */
    private void questionMultiChoice(){
        radioGroup.setVisibility(View.GONE);
        linearLayoutCheckBoxs.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
    }

    /**
     * makes the EditText to visible and hides the other view
     */
    private void questionTextAnswer(){
        radioGroup.setVisibility(View.GONE);
        linearLayoutCheckBoxs.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editText.setText("");
    }


    /**
     * adds all the questions and answers to the question arraylist
     */
    private void fillData(){
        Question question1 = new Question(0);
        question1.setText(getString(R.string.question1));
        question1.addAnswer(getString(R.string.q1_answer1));
        question1.addAnswer(getString(R.string.q1_answer2));
        question1.addAnswer(getString(R.string.q1_answer3));
        question1.addAnswer(getString(R.string.q1_answer4));
        questionArrayList.add(question1);

        Question question2 = new Question(0);
        question2.setText(getString(R.string.question2));
        question2.addAnswer(getString(R.string.q2_answer1));
        question2.addAnswer(getString(R.string.q2_answer2));
        question2.addAnswer(getString(R.string.q2_answer3));
        question2.addAnswer(getString(R.string.q2_answer4));
        questionArrayList.add(question2);

        Question question3 = new Question(0);
        question3.setText(getString(R.string.question3));
        question3.addAnswer(getString(R.string.q3_answer1));
        question3.addAnswer(getString(R.string.q3_answer2));
        question3.addAnswer(getString(R.string.q3_answer3));
        question3.addAnswer(getString(R.string.q3_answer4));
        questionArrayList.add(question3);

        Question question4 = new Question(0);
        question4.setText(getString(R.string.question4));
        question4.addAnswer(getString(R.string.q4_answer1));
        question4.addAnswer(getString(R.string.q4_answer2));
        question4.addAnswer(getString(R.string.q4_answer3));
        question4.addAnswer(getString(R.string.q4_answer4));
        questionArrayList.add(question4);

        Question question5 = new Question(1);
        question5.setText(getString(R.string.question5));
        question5.addAnswer(getString(R.string.q5_answer1));
        question5.addAnswer(getString(R.string.q5_answer2));
        question5.addAnswer(getString(R.string.q5_answer3));
        question5.addAnswer(getString(R.string.q5_answer4));
        questionArrayList.add(question5);

        Question question6 = new Question(1);
        question6.setText(getString(R.string.question6));
        question6.addAnswer(getString(R.string.q6_answer1));
        question6.addAnswer(getString(R.string.q6_answer2));
        question6.addAnswer(getString(R.string.q6_answer3));
        question6.addAnswer(getString(R.string.q6_answer4));
        questionArrayList.add(question6);

        Question question7 = new Question(2);
        question7.setText(getString(R.string.question7));
        questionArrayList.add(question7);

        Question question8 = new Question(2);
        question8.setText(getString(R.string.question8));
        questionArrayList.add(question8);
    }

}
