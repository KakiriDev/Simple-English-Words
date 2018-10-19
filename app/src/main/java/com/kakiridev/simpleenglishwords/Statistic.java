package com.kakiridev.simpleenglishwords;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class Statistic extends AppCompatActivity {

    TextView totalWords, knownWords, unknownWords, complitedWords, uncomplitedWords, knownPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_statistic);

        initTextView();
        setTextView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
