package com.kakiridev.simpleenglishwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
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
        initListViewOnItemLongClick();
        listview.setAdapter(adapter);

    }

    /** Tworzy menu kontekstowe wywolywane podczas długiego klikniecia na task **/
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        //menu.setHeaderTitle(taskDesc);

        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    /** dodaje listenera na dlugie kliknięcie **/
    private void initListViewOnItemLongClick() {
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                Word task = words.get(position);
                String wordId = task.getmId();
                Log.v("long clicked","pos: " + position);
                return false;
            }
        });
    }

    // get list of words from main activity
    public List<Word> getWordsIntent(){
        words.clear();
        Bundle b = getIntent().getExtras();
        words = b.getParcelableArrayList("BUNDLE");

        return words;
    }
}
