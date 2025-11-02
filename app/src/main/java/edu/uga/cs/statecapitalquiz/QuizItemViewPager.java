package edu.uga.cs.statecapitalquiz;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizItemViewPager {
    int questionNumber;
    String questionText;
    String radioButton1;
    String radioButton2;
    String radioButton3;

    public QuizItemViewPager(int questionNumber, String questionText, String radioButton1, String radioButton2, String radioButton3) {
        this.questionNumber = questionNumber;
        this.questionText = questionText;
        this.radioButton1 = radioButton1;
        this.radioButton2 = radioButton2;
        this.radioButton3 = radioButton3;
    }
}
