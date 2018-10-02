package com.kakiridev.simpleenglishwords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kakiridev.simpleenglishwords.KnowWords.RandW;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RoundView extends AppCompatActivity {
    LinearLayout LL, LL1, LL2, LL3, LL4, LL5, LL6;
    TextView Text, Text1, Text2, Text3, Text4, Text5, Text6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_view);

        initLayout();
        startGame();
    }




    private void initLayout(){
        LL = findViewById(R.id.LL);
        Text = findViewById(R.id.Tile);
        LL1 = findViewById(R.id.LL1);
        Text1 = findViewById(R.id.Tile1);
        LL2 = findViewById(R.id.LL2);
        Text2 = findViewById(R.id.Tile2);
        LL3 = findViewById(R.id.LL3);
        Text3 = findViewById(R.id.Tile3);
        LL4 = findViewById(R.id.LL4);
        Text4 = findViewById(R.id.Tile4);
        LL5 = findViewById(R.id.LL5);
        Text5 = findViewById(R.id.Tile5);
        LL6 = findViewById(R.id.LL6);
        Text6 = findViewById(R.id.Tile6);
    }


    public void startGame(){
        ArrayList<Word> tempList = new ArrayList<>();
        ArrayList<Word> wordList = new ArrayList<>();
        RandW randW = new RandW();
        boolean uniqueWord;
        wordList = randW.randWords(6);
        if(tempList.size() == 0){
            updateGrid(wordList);
        } else{

          do{
              uniqueWord = true;
              for (Word tempWord : tempList){
                  if(tempWord.getid().equals(wordList.get(0).getid())){
                      wordList = randW.randWords(6);
                      uniqueWord = false;
                      break;
                  }
              }
          } while (!uniqueWord);
            updateGrid(wordList);
        }

    }

    public void updateGrid(ArrayList<Word> wordList){
        //save correct word
        Word correctWord = wordList.get(0);
        //shuffle words
        Collections.shuffle(wordList);
        Text.setText(correctWord.getnazwaPl());
        Text1.setText(wordList.get(0).getnazwaEn());
        Text2.setText(wordList.get(1).getnazwaEn());
        Text3.setText(wordList.get(2).getnazwaEn());
        Text4.setText(wordList.get(3).getnazwaEn());
        Text5.setText(wordList.get(4).getnazwaEn());
        Text2.setText(wordList.get(5).getnazwaEn());
    }

}
