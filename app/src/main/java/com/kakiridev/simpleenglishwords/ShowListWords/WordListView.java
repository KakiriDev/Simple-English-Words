package com.kakiridev.simpleenglishwords.ShowListWords;

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
import android.widget.ListView;
import com.kakiridev.simpleenglishwords.Constatus;
import com.kakiridev.simpleenglishwords.MainView;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class WordListView extends AppCompatActivity {


    WordListViewAdapter adapter;
    ListView listview;
    ImageView IV_back_to_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setContentView(R.layout.activity_word_list_view);
        setAdapter();
        IV_back_to_main = findViewById(R.id.iv_tab_buttonBack);
        initOnClickListener();

    }

    @Override
    public void onBackPressed() {}

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
    private void setAdapter(){
        ArrayList<Word> word_list = new ArrayList<Word>();
        word_list.addAll(Constatus.KNOWN_WORD_LIST);

        //sort list - descending
        Collections.sort(word_list, new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getscore() > rhs.getscore() ? -1 : (lhs.getscore() < rhs.getscore()) ? 1 : 0;
            }
        });

        adapter = new WordListViewAdapter(this, R.layout.word_listview_row, word_list);
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }






}
