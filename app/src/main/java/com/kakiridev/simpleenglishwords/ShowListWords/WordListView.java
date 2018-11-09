package com.kakiridev.simpleenglishwords.ShowListWords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.kakiridev.simpleenglishwords.FirebaseGetAllWordsListener;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;



public class WordListView extends AppCompatActivity implements FirebaseGetAllWordsListener {

    private ArrayList<Word> words = new ArrayList<Word>();
    WordListViewAdapter adapter;
    Word word;
    ListView listview;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list_view);

        startGetAllWordsListener(false);

        //switchList
        switchListListener();

        //back button
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    private void switchListListener(){
        Switch switch_button = (Switch) findViewById(R.id.switchList);
        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    startGetAllWordsListener(true);
                } else {
                    startGetAllWordsListener(false);
                }
            }
        });
    }

    public void startGetAllWordsListener(boolean b){
        com.kakiridev.simpleenglishwords.FirebaseDatabase fb = new com.kakiridev.simpleenglishwords.FirebaseDatabase();
        fb.getListOfWordsListener = this;
        fb.getListOfWords(b);
    }

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onFirebaseGetAllWordsListener(ArrayList<Word> words) {
        this.words = words;
        adapter = new WordListViewAdapter(this, R.layout.word_listview_row,words);
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }



}
