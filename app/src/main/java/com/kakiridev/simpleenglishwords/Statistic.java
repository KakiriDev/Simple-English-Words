package com.kakiridev.simpleenglishwords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Statistic extends AppCompatActivity {

    TextView totalWords, knownWords, unknownWords, complitedWords, uncomplitedWords, knownPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        initTextView();
        setTextView();
    }

    private void initTextView(){
        totalWords = findViewById(R.id.totalWords);
        knownWords = findViewById(R.id.knownWords);
        unknownWords = findViewById(R.id.unknownWords);
        complitedWords = findViewById(R.id.complitedWords);
        uncomplitedWords = findViewById(R.id.uncomplitedWords);
        knownPercent = findViewById(R.id.knownPercent);
    }

    private void setTextView(){
        totalWords.setText("Total Words: " + Constatus.WORD_LIST.size());
        knownWords.setText("Known Words: " + Constatus.KNOWN_WORD_LIST.size());
        unknownWords.setText("Unknown Words: " + Constatus.UNKNOWN_WORD_LIST.size());
        complitedWords.setText("Complited Words: " + Constatus.WORD_COMPLITED_LIST.size());
        uncomplitedWords.setText("Uncomplited Words: " + Constatus.UNKNOWN_WORD_LIST.size());
        knownPercent.setText("Known Percent: " + Double.valueOf(getTotalScore()).toString());
        }

        private double getTotalScore(){
            int score = 0;

            ArrayList<Word> wordList = Constatus.WORD_LIST;
            ArrayList<Word> knownwords = Constatus.KNOWN_WORD_LIST;

            for (Word kw : knownwords){
                score = score + kw.getscore();
        }

        return score / (wordList.size() * 100);
    }
}
