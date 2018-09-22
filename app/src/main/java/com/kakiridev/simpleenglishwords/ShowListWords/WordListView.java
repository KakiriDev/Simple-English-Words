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
import com.kakiridev.simpleenglishwords.AddNewWord.AddWordView;

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

    // Add Context Menu
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }


    /** wykonuje się po wybraniu opcji z menu knteksowego **/
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuinfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        long selectid = menuinfo.id; //_id from database in this case
        int selectpos = menuinfo.position; //position in the adapter
        word = words.get(selectpos); // wskazany task

        if(item.getTitle()=="Edit") {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(); //pobranie referencji do bazy
            mDatabase.child("Words").child(word.getmId()); // zapis do bazy do childrena "grupa"

            intent = new Intent(getApplicationContext(), AddWordView.class);
            Toast.makeText(this, "isE " + word.getnazwaPl(), Toast.LENGTH_LONG).show();
            intent.putExtra("isEdit", true);
            intent.putExtra("id", word.getmId());
            intent.putExtra("nazwaPl", word.getnazwaPl());
            intent.putExtra("nazwaEn", word.getnazwaEn());
            intent.putExtra("category", word.getcategory());
            startActivity(intent);
        }
        if(item.getTitle()=="Delete") {

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query applesQuery = ref.child("Words").orderByChild("id").equalTo(word.getmId());

            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                        words.clear();
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("DTAG", "onCancelled", databaseError.toException());
                }
            });
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onFirebaseGetAllWordsListener(ArrayList<Word> words) {
        this.words = words;
        adapter = new WordListViewAdapter(this, R.layout.word_listview_row,words);
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);
        registerForContextMenu(listview);
        adapter.notifyDataSetChanged();
    }



}
