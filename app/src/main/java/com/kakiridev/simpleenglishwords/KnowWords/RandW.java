package com.kakiridev.simpleenglishwords.KnowWords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.Constatus;
import com.kakiridev.simpleenglishwords.MainView;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.RoundView;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;
import java.util.Random;

public class RandW extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rand_w);


        Button buttonRand = findViewById(R.id.randWords);
        buttonRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity();
                /**
                 ArrayList<Word> word = new ArrayList<>();
                 word = randWords(5);
                 setScore(word.get(0).id, getScore(word.get(0).id) + 10);

                 Log.d("randomed", "score: " + getScore(word.get(0).id));
                 **/
            }
        });

    }

    public void startActivity() {
        Intent intent = new Intent(this, RoundView.class);
        startActivity(intent);
    }

    private int getScore(String wordId) {
        int score = 1;
        for (Word word : Constatus.KNOWN_WORD_LIST) {
            if (word.getid().equals(wordId)) {
                score = word.getscore();
            }
        }
        return score;
    }

    public void correctWord(String wordId) {
        if (getScore(wordId) <= 90) {
            setScore(wordId, getScore(wordId) + 10);
        } else {
            setScore(wordId, 100);
        }
    }

    public void wrongWord(String wordId) {
        if (getScore(wordId) > 10) {
            setScore(wordId, getScore(wordId) + 10);
        } else {
            setScore(wordId, 0);
        }
    }

    private void setScore(final String wordId, final int score) {

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DatabaseReference refUser = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId()).child("Words");

                refUser.child(wordId).child("score").setValue(score);

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
        ArrayList<Word> word = new ArrayList<Word>();
        Random wordId = new Random();
        Random percent = new Random();
        int knowSize = Constatus.KNOWN_WORD_LIST.size();
        int unknowSize = Constatus.UNKNOWN_WORD_LIST.size();

        boolean isRandomed = false;
        boolean isCorrect = false;

        while (word.size() < numberOfWords) {

            int randPercent = percent.nextInt(100);
            int knowId = wordId.nextInt(knowSize);
            int unknowId = wordId.nextInt(unknowSize);
            Word randKnownWord = Constatus.KNOWN_WORD_LIST.get(knowId);
            Word randUnknownWord = Constatus.UNKNOWN_WORD_LIST.get(unknowId);
            if (isCorrect == false) {
                // rand known word
                if (randKnownWord.getscore() <= randPercent) {
                    //word randomed correctly
                    word.add(randKnownWord);
                    isCorrect = true;
                }
            } else {
                //rand other words
                if (randKnownWord.getscore() <= randPercent) {
                    //word randomed correctly
                    boolean uniq = true;
                    for (Word w : word) {
                        if ((randKnownWord.getid()).equals(w.getid())) {
                            uniq = false;
                        }
                    }
                    if (uniq){
                        word.add(randKnownWord);
                    }
                } else {
                    boolean uniq = true;
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

    /**
     * get firebase user id
     **/
    private String getFirebaseUserId() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentFirebaseUser.getUid().toString();
        return uid;
    }


}
