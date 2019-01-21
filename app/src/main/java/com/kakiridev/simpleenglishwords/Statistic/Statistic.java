package com.kakiridev.simpleenglishwords.Statistic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakiridev.simpleenglishwords.Constatus;
import com.kakiridev.simpleenglishwords.MainView;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;

public class Statistic extends AppCompatActivity {

    TextView totalWords, knownWords, unknownWords, knownPercent;
    ImageView IV_back_to_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setToolbar(); //TODO TOOLBAR
        super.onCreate(savedInstanceState);

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_statistic);

        initTextView();
        setTextView();
        initOnClickListener();
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

//TODO TOOLBAR
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
    //TODO TOOLBAR
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

    private void initTextView(){
        totalWords = findViewById(R.id.totalWords);
        knownWords = findViewById(R.id.knownWords);
        unknownWords = findViewById(R.id.unknownWords);
        knownPercent = findViewById(R.id.knownPercent);
        IV_back_to_main = findViewById(R.id.iv_tab_buttonBack);
    }

    /** initialize click listener and check clicked word **/
    private void initOnClickListener() {
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

    private void setTextView(){
        totalWords.setText("Total Words: " + Constatus.WORD_LIST.size());
        knownWords.setText("Known Words: " + Constatus.KNOWN_WORD_LIST.size());
        unknownWords.setText("Unknown Words: " + Constatus.UNKNOWN_WORD_LIST.size());
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
