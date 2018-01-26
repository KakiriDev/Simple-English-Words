package com.kakiridev.simpleenglishwords;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class WordListView extends AppCompatActivity {

    private ArrayList<Word> words = new ArrayList<Word>();
    WordListViewAdapter adapter;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list_view);

        Log.v("DTAG", "Class name: " + Thread.currentThread().getStackTrace()[2].getClassName() + " Method name: " + Thread.currentThread().getStackTrace()[2].getMethodName() + " Words count: " + words.size());

        words = getWords();

        adapter = new WordListViewAdapter(this, R.layout.word_listview_row,words);
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);
        initListViewOnItemLongClick();
    }


    // Add Context Menu
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){

        //menu.setHeaderTitle(taskDesc);

        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }

    // add longClickListener
    private void initListViewOnItemLongClick() {
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {

                Word task = words.get(position);
                String wordId = task.getmId();
                Log.v("DTAG","pos: " + position);
                return false;
            }
        });
    }



    // Get all words for firebase and add it to "words" list
    private ArrayList<Word> getWords() {
        Log.v("DTAG", "Class name: " + Thread.currentThread().getStackTrace()[2].getClassName() + " Method name: " + Thread.currentThread().getStackTrace()[2].getMethodName() + " Words count: " + words.size());

        DatabaseReference mDatabaseWords = FirebaseDatabase.getInstance().getReference().child("Words");
        final ArrayList<Word> fbWords = new ArrayList<Word>();
        words.clear();
//        mDatabaseWords.addListenerForSingleValueEvent(new ValueEventListener() {
        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.v("DTAG", "Class name: " + Thread.currentThread().getStackTrace()[2].getClassName() + " Method name: " + Thread.currentThread().getStackTrace()[2].getMethodName() + " Words count: " + words.size());

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    String id = dsp.child("id").getValue().toString();
                    String pl = dsp.child("nazwaPl").getValue().toString();
                    String en = dsp.child("nazwaEn").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    Word rekord = new Word(id, pl, en, category);

                    fbWords.add(rekord);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("DTAG", "Class name: " + Thread.currentThread().getStackTrace()[2].getClassName() + " Method name: " + Thread.currentThread().getStackTrace()[2].getMethodName() + " Words count: " + words.size());
            }

        });

        return fbWords;
    }



}
