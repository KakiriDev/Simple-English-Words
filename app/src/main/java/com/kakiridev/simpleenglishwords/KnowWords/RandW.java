package com.kakiridev.simpleenglishwords.KnowWords;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.Constatus;
import com.kakiridev.simpleenglishwords.DataLoading;
import com.kakiridev.simpleenglishwords.MainView;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.RoundView;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;
import java.util.Random;

public class RandW extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rand_w);


        Button buttonRand = findViewById(R.id.randWords);
        buttonRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity();
            }
        });

    }

    public void startActivity() {
        Intent intent = new Intent(this, RoundView.class);
        startActivity(intent);
    }




    public void correctWord(Word wordId) {
        int score  = wordId.getscore();
        if (score <= 90) {
            wordId.setscore(score + 10);
            setScore(wordId);
        } else {
            wordId.setscore(100);
            setScore(wordId);
        }
    }

    public void wrongWord(Word wordId) {
        int score  = wordId.getscore();
        if (score > 10) {
            wordId.setscore(score - 10);
            setScore(wordId);
        } else {
            wordId.setscore(0);
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

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getFirebaseUserId() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentFirebaseUser.getUid().toString();
        return uid;
    }


}
