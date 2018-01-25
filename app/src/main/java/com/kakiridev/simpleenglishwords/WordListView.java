package com.kakiridev.simpleenglishwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WordListView extends AppCompatActivity {

    private List<Word> words = new ArrayList<Word>();
    WordListViewAdapter adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list_view);


        getWordsIntent();
        adapter = new WordListViewAdapter(this, R.layout.word_listview_row,words);

        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }

    public List<Word> getWordsIntent(){
        words.clear();
        Bundle b = getIntent().getExtras();
        words = b.getParcelableArrayList("BUNDLE");

        return words;
    }
}
