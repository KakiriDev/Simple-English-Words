package com.kakiridev.simpleenglishwords;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kakiridev.simpleenglishwords.KnowWords.RandW;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class RoundView extends AppCompatActivity {
    LinearLayout LL, LL1, LL2, LL3, LL4, LL5, LL6, LL7, LL8, LL9, LL10;
    TextView Text, Text1, Text2, Text3, Text4, Text5, Text6, Text7, Text8, Text9, Text10;
    ArrayList<Word> arrayWords;
    Word correctWord;
    LinearLayout correctLL;
    boolean isAvailalble = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_round_view);

        initLayout();
        initOnClickListener();
        startGame();


    }

    private void initLayout() {
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
        LL7 = findViewById(R.id.LL7);
        Text7 = findViewById(R.id.Tile7);
        LL8 = findViewById(R.id.LL8);
        Text8 = findViewById(R.id.Tile8);
        LL9 = findViewById(R.id.LL9);
        Text9 = findViewById(R.id.Tile9);
        LL10 = findViewById(R.id.LL10);
        Text10 = findViewById(R.id.Tile10);
    }

    private void initOnClickListener() {
        LL1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(0, LL1);
                }
            }
        });
        LL2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(1, LL2);
                }
            }
        });
        LL3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(2, LL3);
                }
            }
        });
        LL4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(3, LL4);
                }
            }
        });
        LL5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(4, LL5);
                }
            }
        });
        LL6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(5, LL6);
                }
            }
        });
        LL7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(6, LL7);
                }
            }
        });
        LL8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(7, LL8);
                }
            }
        });
        LL9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(8, LL9);
                }
            }
        });
        LL10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isAvailalble) {
                    isAvailalble = false;
                    checkWord(9, LL10);
                }
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkWord(int position, LinearLayout lin) {
        RandW score = new RandW();
        if (arrayWords.get(position).getid().equals(correctWord.getid())) {
            score.correctWord(arrayWords.get(position));
            lin.setBackground(getResources().getDrawable(R.drawable.rounded_corners_correct));
            correctWord(lin);
        } else {
            score.wrongWord(arrayWords.get(position));
            lin.setBackground(getResources().getDrawable(R.drawable.rounded_corners_wrong));
            correctLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners_correct));
            wrongWord(lin);
        }
    }

    private void correctWord(final LinearLayout lin) {
        Log.e("checkErr", "correctWord");
        lin.postDelayed(new Runnable() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                lin.setBackground(getResources().getDrawable(R.drawable.rounded_corners));//save correct word
                correctLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                startGame();
                isAvailalble = true;
            }
        }, 1500);

    }

    private void wrongWord(final LinearLayout lin) {
        Log.e("checkErr", "wrongWord");
        lin.postDelayed(new Runnable() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                lin.setBackground(getResources().getDrawable(R.drawable.rounded_corners));//save correct word
                correctLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
                startGame();
                isAvailalble = true;
            }
        }, 2500);

    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void startGame() {
        int NUM_OF_WORDS = 10;
        Log.e("checkErr", "startGame");
        ArrayList<Word> tempList = new ArrayList<>();
        ArrayList<Word> wordList = new ArrayList<>();
        RandW randW = new RandW();
        boolean uniqueWord;
        Log.e("checkErr", "randWords in");
        wordList = randW.randWords(NUM_OF_WORDS);
        Log.e("checkErr", "randWords out");
        if (correctLL != null)
            correctLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners));
        if (tempList.size() == 0) {
            updateGrid(wordList);
        } else {
            do {
                uniqueWord = true;
                for (Word tempWord : tempList) {
                    if (tempWord.getid().equals(wordList.get(0).getid())) {
                        wordList = randW.randWords(NUM_OF_WORDS);
                        uniqueWord = false;
                        break;
                    }
                }
            } while (!uniqueWord);
            updateGrid(wordList);
        }
    }

    private void setTileText(ArrayList<Word> wordList) {
        Text.setText(correctWord.getnazwaPl() + " --- " + correctWord.getscore());
        Text1.setText(wordList.get(0).getnazwaEn() + " --- " + wordList.get(0).getscore());
        Text2.setText(wordList.get(1).getnazwaEn() + " --- " + wordList.get(1).getscore());
        Text3.setText(wordList.get(2).getnazwaEn() + " --- " + wordList.get(2).getscore());
        Text4.setText(wordList.get(3).getnazwaEn() + " --- " + wordList.get(3).getscore());
        Text5.setText(wordList.get(4).getnazwaEn() + " --- " + wordList.get(4).getscore());
        Text6.setText(wordList.get(5).getnazwaEn() + " --- " + wordList.get(5).getscore());
        Text7.setText(wordList.get(6).getnazwaEn() + " --- " + wordList.get(6).getscore());
        Text8.setText(wordList.get(7).getnazwaEn() + " --- " + wordList.get(7).getscore());
        Text9.setText(wordList.get(8).getnazwaEn() + " --- " + wordList.get(8).getscore());
        Text10.setText(wordList.get(9).getnazwaEn() + " --- " + wordList.get(9).getscore());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void updateGrid(ArrayList<Word> wordList) {
        Log.e("checkErr", "updateGrid");

        correctWord = wordList.get(0);
        //shuffle words
        Collections.shuffle(wordList);
        arrayWords = wordList;

        setTileText(wordList);

        int pos = 0;
        for (Word wl : arrayWords) {

            if (wl.getid().equals(correctWord.getid())) {
                switch (pos) {
                    case 0:
                        correctLL = LL1;
                        break;
                    case 1:
                        correctLL = LL2;
                        break;
                    case 2:
                        correctLL = LL3;
                        break;
                    case 3:
                        correctLL = LL4;
                        break;
                    case 4:
                        correctLL = LL5;
                        break;
                    case 5:
                        correctLL = LL6;
                        break;
                    case 6:
                        correctLL = LL7;
                        break;
                    case 7:
                        correctLL = LL8;
                        break;
                    case 8:
                        correctLL = LL9;
                        break;
                    case 9:
                        correctLL = LL10;
                        break;
                }
            } else {
                pos++;
            }

        }

    }

}
