package com.kakiridev.simpleenglishwords;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
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
    TextView hello, score;
    ImageView IV_back_to_main;
    ArrayList<Word> arrayWords;
    Word correctWord;
    LinearLayout correctLL;
    private static final String TAG = "MainActivity";
    private AdView mAdView;
    boolean isAvailalble = true;

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_round_view);

        addAdMob();
        setToolbarText();
        initLayout();
        initOnClickListener();
        startGame();

    }

    private void addAdMob(){
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void setToolbarText(){
        Typeface typeface = ResourcesCompat.getFont(this, R.font.squeaky);

        hello = findViewById(R.id.tv_tab_witaj);
        score = findViewById(R.id.tv_tab_pkt);

        hello.setTypeface(typeface);
        score.setTypeface(typeface);
    }

    private void setToolbar(){
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
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

        IV_back_to_main = findViewById(R.id.iv_tab_buttonBack);
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
        IV_back_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity_MainView();
            }
        });
    }

    public void startActivity_MainView() {
        Intent i = new Intent(this, MainView.class);
        startActivity(i);
        finish();
    }
    
    @Override
    public void onBackPressed() { }

    private void checkFillupKnownWordList() {
        if ((Constatus.KNOWN_WORD_LIST.isEmpty() || Constatus.getUncomplitedWords() < Constatus.numberOfMinWords)) {
            int count = Constatus.numberOfMinWords - Constatus.getUncomplitedWords();
            DataLoading dl = new DataLoading();
            dl.randNewWords(count, false);

            Toast.makeText(RoundView.this, "Poznano nowe słowo!",Toast.LENGTH_SHORT).show();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkWord(int position, LinearLayout lin) {
        RandW score = new RandW();
        if (arrayWords.get(position).getid().equals(correctWord.getid())) {
            score.correctWord(arrayWords.get(position));
            checkFillupKnownWordList();
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
                lin.setBackground(getResources().getDrawable(R.drawable.blackbutton));//save correct word
                correctLL.setBackground(getResources().getDrawable(R.drawable.blackbutton));


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
                lin.setBackground(getResources().getDrawable(R.drawable.blackbutton));//save correct word
                correctLL.setBackground(getResources().getDrawable(R.drawable.blackbutton));


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
            correctLL.setBackground(getResources().getDrawable(R.drawable.blackbutton));
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
        Text.setText(correctWord.getnazwaPl());
        Text1.setText(wordList.get(0).getnazwaEn());
        Text2.setText(wordList.get(1).getnazwaEn());
        Text3.setText(wordList.get(2).getnazwaEn());
        Text4.setText(wordList.get(3).getnazwaEn());
        Text5.setText(wordList.get(4).getnazwaEn());
        Text6.setText(wordList.get(5).getnazwaEn());
        Text7.setText(wordList.get(6).getnazwaEn());
        Text8.setText(wordList.get(7).getnazwaEn());
        Text9.setText(wordList.get(8).getnazwaEn());
        Text10.setText(wordList.get(9).getnazwaEn());
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
