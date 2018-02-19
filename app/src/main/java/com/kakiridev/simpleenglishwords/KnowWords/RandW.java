package com.kakiridev.simpleenglishwords.KnowWords;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.AddNewWord.AddWordView;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;
import java.util.HashMap;

public class RandW extends AppCompatActivity {

    ArrayList<Word> fbWords;
    boolean known;
    int oldScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rand_w);


        fbWords = new ArrayList<Word>();
        ArrayList<Word> list = getAllWords();
       // Log.e("DTAG", "order 3 " + list.size());

        Button buttonRand = findViewById(R.id.randWords);
        buttonRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Word> list = getAllWords();
                //int aaa = getScore("-L5fiJWEf6M0RPuBhFs5");
                Log.e("DTAG", "order 3 " + list.size());
                Log.e("DTAG", "order 4 " + fbWords.size());
            }
        });
    }






    //get all words from firebase
    private ArrayList<Word> getAllWords(){
        final ArrayList<Word> fbWords = new ArrayList<Word>();
        DatabaseReference mDatabaseWords = FirebaseDatabase.getInstance().getReference().child("Words");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fbWords.clear();
                String userId = getFirebaseUserId();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String id = dsp.child("id").getValue().toString();
                    String pl = dsp.child("nazwaPl").getValue().toString();
                    String en = dsp.child("nazwaEn").getValue().toString();
                    String category = dsp.child("category").getValue().toString();

                    String score = "0";

                    if(checkUserKnowWord(id)){
                        score = dsp.child(userId).child("score").getValue().toString();
                    }

                    Word rekord = new Word(id, pl, en, category, score);

                    fbWords.add(rekord);
                    Log.e("DTAG", "order 33 " + fbWords.size());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        mDatabaseWords.addValueEventListener(listener);
       // mDatabaseWords.removeEventListener(listener);
        return fbWords;
    }

    //get all known words from list of words (firebase -> list of words)
    private ArrayList<Word> getAllKnownWords(){
        final ArrayList<Word> fbWords = new ArrayList<Word>();
        DatabaseReference mDatabaseWords = FirebaseDatabase.getInstance().getReference().child("Words");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String userId = getFirebaseUserId();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String id = dsp.child("id").getValue().toString();
                    String pl = dsp.child("nazwaPl").getValue().toString();
                    String en = dsp.child("nazwaEn").getValue().toString();
                    String category = dsp.child("category").getValue().toString();

                    String score = "0";

                    if(checkUserKnowWord(id)){
                        score = dsp.child(userId).child("score").getValue().toString();
                        Word rekord = new Word(id, pl, en, category, score);
                        fbWords.add(rekord);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };
        mDatabaseWords.addValueEventListener(listener);
        mDatabaseWords.removeEventListener(listener);
        return fbWords;
    }

    //get number of words from
    private int getCountOfWords(ArrayList<Word> words){
        int count = words.size();

        return count;
    }

    //get average score from list of known words
    private int getTotalScore(ArrayList<Word> wordsList){
        int score = 0;
        for (Word word : wordsList) {
            score = score + Integer.parseInt(word.getmScore());
        }
        return score;
    }

    private long getAverageScore(int knownCount, int score) {
        long average;
        if(knownCount > 0){
            average = score / knownCount;
        } else {
            average = 0;
        }
        return average;
    }



    //add userId to word
    private void KnowNewWord(String word){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String userId = getFirebaseUserId();

        HashMap<String, String> dataMap = new HashMap<String, String>(); //kolejność wczytania do bazy odwrotna niz zapisu
        dataMap.put("score", "0");

        mDatabase.child("Words").child(word).child(userId).setValue(dataMap);
    }
/*
    private int getScore(String word){
        int oldScore = 0;
        Log.e("DTAG", "order 1");
        String userId = getFirebaseUserId();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Words").child(word).child(userId).child("score");

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                oldScore = Integer.parseInt(snapshot.getValue().toString());
                Log.e("DTAG", "order 3");
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        rootRef.addListenerForSingleValueEvent(listener);
        rootRef.removeEventListener(listener);
        Log.e("DTAG", "order 2");
    return oldScore;
    }
*/
    private boolean checkUserKnowWord(String word){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Words").child(word);

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(getFirebaseUserId())) {
                    known = true;
                } else {
                    known = false;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        rootRef.addValueEventListener(listener);
        rootRef.removeEventListener(listener);

        return known;
    }

    //get firebase user id
    private String getFirebaseUserId(){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String uid = currentFirebaseUser.getUid().toString();
        return uid;
    }



}
