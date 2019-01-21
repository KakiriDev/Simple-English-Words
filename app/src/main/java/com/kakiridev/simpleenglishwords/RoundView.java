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
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
    public void onBackPressed() {}

/**
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
**/
    /** initialize Word cells **/
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

    /** initialize click listener and check clicked word **/
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

    /** start main activity when back button is clicked **/
    public void startActivity_MainView() {
        Intent i = new Intent(this, MainView.class);
        startActivity(i);
        finish();
    }


    /** check fillup KnownWordList, when list is empty (first run) or when number of words <90% is less than numberOfMinWords (20) then rand new word/s from UNKNOWN_WORD_LIST **/
    private void checkFillupKnownWordList() {
        if ((Constatus.KNOWN_WORD_LIST.isEmpty() || Constatus.getUncomplitedWords() < Constatus.numberOfMinWords)) {
            int count = Constatus.numberOfMinWords - Constatus.getUncomplitedWords();
            DataLoading dl = new DataLoading();
            dl.randNewWords(count, false);

            Toast.makeText(RoundView.this, "Poznano nowe słowo!",Toast.LENGTH_SHORT).show();
        }
    }

    /** compare cell from parametr with correct word, if is correct/incorrect then set score, check fillup list, setBackground and start new round **/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void checkWord(int position, LinearLayout lin) {
//        RandW score = new RandW();
        if (arrayWords.get(position).getid().equals(correctWord.getid())) {
//            score.correctWord(arrayWords.get(position));
            isCorrectWord(arrayWords.get(position));
            checkFillupKnownWordList();
            lin.setBackground(getResources().getDrawable(R.drawable.rounded_corners_correct));
            correctWord(lin);
        } else {
//            score.wrongWord(arrayWords.get(position));
            isWrongWord(arrayWords.get(position));
            lin.setBackground(getResources().getDrawable(R.drawable.rounded_corners_wrong));
            correctLL.setBackground(getResources().getDrawable(R.drawable.rounded_corners_correct));
            wrongWord(lin);
        }
    }

    public void isCorrectWord(Word wordId) {
        int score  = wordId.getscore();
        if (score <= 90) {
            wordId.setscore(score + 10);
            setTotalScore(10);
            setScore(wordId);
        } else {
            wordId.setscore(100);
            setTotalScore(10);
            setScore(wordId);
        }
    }

    public void isWrongWord(Word wordId) {
        int score  = wordId.getscore();
        if (score > 10) {
            wordId.setscore(score - 10);
            setTotalScore(-10);
            setScore(wordId);
        } else {
            wordId.setscore(0);
            setTotalScore(-10);
            setScore(wordId);
        }
    }

    private void setScore(final Word wordId) {

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference refUser = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId()).child("Words");
                refUser.child(wordId.getid()).setValue(wordId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setTotalScore(final Integer score) {

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference refUser = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId()).child("userScore");

                int oldScore = Integer.parseInt(dataSnapshot.child("userScore").getValue().toString());
                //int oldScore = (int) dataSnapshot.child("userScore").getValue();
              //  int oldScore = 0;
                if (score > 0){
                    refUser.setValue(score + oldScore);
                } else {
                    if((oldScore + score) > 0) {
                        refUser.setValue(score + oldScore);
                    } else {
                        refUser.setValue(0);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /***
     * rand words
     * @param numberOfWords //total number of words
     * @return //list of words with known word on position 0
     */
    public ArrayList<Word> randWords(int numberOfWords) {
        Log.e("checkErr", "randWords");
        ArrayList<Word> word = new ArrayList<Word>();
        Random wordId = new Random();
        Random percent = new Random();
        int knowSize = Constatus.KNOWN_WORD_LIST.size();
        int unknowSize = Constatus.UNKNOWN_WORD_LIST.size();

        boolean isCorrect = false;
        int a = 1;
        while (word.size() < numberOfWords) {
            Log.e("checkErr", "a=" + a);
            a++;

            int randPercent = percent.nextInt(100);
            int knowId = wordId.nextInt(knowSize);
            int unknowId = wordId.nextInt(unknowSize);
            Log.e("checkErr", "knowId: " + knowId);
            Word randKnownWord = Constatus.KNOWN_WORD_LIST.get(knowId);
            Log.e("checkErr", "randKnownWord: " + randKnownWord.toString());
            Word randUnknownWord = Constatus.UNKNOWN_WORD_LIST.get(unknowId);

            if (isCorrect == false) {
                // rand known word
                if (randKnownWord.getscore() <= randPercent) {
                    //word randomed correctly
                    word.add(randKnownWord);
                    isCorrect = true;
                }
            } else {
                boolean uniq = true;
                //rand other words
                if (randKnownWord.getscore() <= randPercent) {
                    //word randomed correctly
                    for (Word w : word) {
                        // Log.e("checkErr", "randKnownWord: " + randKnownWord.toString());
                        //Log.e("checkErr", "w: " + w.toString());
                        // Log.e("checkErr", "word: " + word.toString());
                        if ((randKnownWord.getid()).equals(w.getid())) {
                            uniq = false;
                        }
                    }
                    if (uniq){
                        word.add(randKnownWord);
                    }
                } else {
                    for (Word w : word) {
                        if ((randKnownWord.getid()).equals(w.getid())) {
                            uniq = false;
                        }
                    }
                    if (uniq){
                        word.add(randUnknownWord);
                    }
                }
            }
        }
        return word;
    }

    /** if checked word is correct then setbackground changed cell and after delay start next round **/
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

    /** if checked word is incorrect then setbackground changed cell and after delay start next round **/
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
        boolean uniqueWord;
        Log.e("checkErr", "randWords in");
        wordList = randWords(NUM_OF_WORDS);
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
                        wordList = randWords(NUM_OF_WORDS);
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
                    case 0: correctLL = LL1; break;
                    case 1: correctLL = LL2; break;
                    case 2: correctLL = LL3; break;
                    case 3: correctLL = LL4; break;
                    case 4: correctLL = LL5; break;
                    case 5: correctLL = LL6; break;
                    case 6: correctLL = LL7; break;
                    case 7: correctLL = LL8; break;
                    case 8: correctLL = LL9; break;
                    case 9: correctLL = LL10; break;
                }
            } else {
                pos++;
            }

        }

    }

}
