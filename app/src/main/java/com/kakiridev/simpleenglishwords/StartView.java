package com.kakiridev.simpleenglishwords;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StartView extends AppCompatActivity {

    private ArrayList<Word> words = new ArrayList<Word>();
    EditText ET_PL;
    EditText ET_EN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_view);

        ET_PL = findViewById(R.id.PL);
        ET_EN = findViewById(R.id.EN);
        getWords(); // start at create projeck, get all words from firebase to list "words"

        Button btnToListView = findViewById(R.id.btn_show_wordslist);
        btnToListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendWordsList();
            }
        });

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ET_EN.getText().length() <= 0) {
                    Toast.makeText(StartView.this, "Wprowadź poprawne dane.", Toast.LENGTH_LONG).show();
                } else {
                    String Pl = ET_PL.getText().toString();
                    String En = ET_EN.getText().toString();
                    saveWord(Pl, En);
                }
            }
        });
    }

    public void sendWordsList() {
     //   Intent intent = new Intent(this, WordListView.class);

        Intent i = new Intent(this, WordListView.class);
        Bundle b = new Bundle();
        b.putParcelableArrayList("BUNDLE", words);
        i.putExtras(b);
        startActivity(i);
            }

        // Firebase useable
        private void Listener(){
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Words");
            mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String id = dataSnapshot.child("id").getValue().toString();
                    String pl = dataSnapshot.child("nazwaPl").getValue().toString();
                    String en = dataSnapshot.child("nazwaEn").getValue().toString();
                    String category = dataSnapshot.child("category").getValue().toString();
                    Word rekord = new Word(id, pl, en, category);

                    words.add(rekord);
                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    // Add new word to firebase
    private void saveWord(final String nazwaPl, final String nazwaEn){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Words"); //pobranie referencji do bazy
        String key = mDatabase.push().getKey();

        HashMap<String, String> dataMap = new HashMap<String, String>(); //kolejność wczytania do bazy odwrotna niz zapisu
        dataMap.put("id", key);
        dataMap.put("nazwaPl", nazwaPl);
        dataMap.put("nazwaEn", nazwaEn);
        dataMap.put("category", "Other");

        //sprawdzenie poprawnosci zapisu do bazy
        mDatabase.child(key).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(StartView.this, "Dodano nowe słowo: "+ nazwaPl + " / " + nazwaEn + "." , Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(StartView.this, "Error.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    // When sb add new word to firebase
    public void addNewWordToList(){

    }

    // When sb update word in firebase
    public void updateWord(){

    }

    // When sb delete word from firebase
    public void deleteWrd(){

    }

    // Get all words for firebase and add it to "words" list
    private void getWords() {
        DatabaseReference mDatabaseWords = FirebaseDatabase.getInstance().getReference().child("Words");
        // final List<Rekord> words = new ArrayList<Rekord>();
        words.clear();
        //  mDatabaseWords.addListenerForSingleValueEvent(new ValueEventListener() {
        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.v("TAG", "aaaa"); //displays the key for the node

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    String id = dsp.child("id").getValue().toString();
                    String pl = dsp.child("nazwaPl").getValue().toString();
                    String en = dsp.child("nazwaEn").getValue().toString();
                    String category = dsp.child("category").getValue().toString();
                    Word rekord = new Word(id, pl, en, category);

                    words.add(rekord);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}
