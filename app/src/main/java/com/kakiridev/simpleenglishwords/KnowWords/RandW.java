package com.kakiridev.simpleenglishwords.KnowWords;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.Constatus;
import com.kakiridev.simpleenglishwords.FirebaseDatabase;
import com.kakiridev.simpleenglishwords.FirebaseDatabaseUsers;
import com.kakiridev.simpleenglishwords.KnownWord;
import com.kakiridev.simpleenglishwords.MainView;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.User;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandW extends AppCompatActivity {

    ArrayList<Word> listOfWords;
    ArrayList<Word> listOfKnownWords;
    ArrayList<Word> listOfUnknownWords;
    int countWords;
    int countKnownWords;
    int countUnknownWords;
    long averageScore;
    int totalScore;
    boolean firstGetWords = true;

    private int progressStatus = 0;
    private Handler handler = new Handler();

    boolean known;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rand_w);

        ArrayList<User> uL = new ArrayList<>();
        uL = MainView.userList;
        Log.d("users", "123");


        Button buttonRand = findViewById(R.id.randWords);
        buttonRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<Word> word = new ArrayList<>();
                word = randCorrectWord(5);
                Log.d("randomed", "Rand: " + word.toString());

            }
        });

    }

    public ArrayList<Word> randCorrectWord( int numberOfWords){
        ArrayList<Word> word = new ArrayList<Word>();
        Random wordId = new Random();
        Random percent = new Random();
        int knowSize = Constatus.KNOWN_WORD_LIST.size();
        int unknowSize = Constatus.UNKNOWN_WORD_LIST.size();

        boolean isRandomed = false;
        boolean isCorrect = false;

        while (word.size() < numberOfWords){

            int randPercent = percent.nextInt(100);
            int knowId = wordId.nextInt(knowSize);
            int unknowId = wordId.nextInt(unknowSize);
            Word randKnownWord = Constatus.KNOWN_WORD_LIST.get(knowId);
            Word randUnknownWord = Constatus.UNKNOWN_WORD_LIST.get(unknowId);
            if(isCorrect == false){
                // rand known word
                if(randKnownWord.getscore() <= randPercent){
                    //word randomed correctly
                    word.add(randKnownWord);
                    isCorrect = true;
                }
            } else {
                //rand other words
                if(randKnownWord.getscore() <= randPercent){
                    //word randomed correctly
                    if(randKnownWord.getid() != word.get(0).getid()){
                        word.add(randKnownWord);
                    }
                } else {
                   word.add(randUnknownWord);
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

    /** get firebase user id **/
    private String getFirebaseUserId(){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String uid = currentFirebaseUser.getUid().toString();
        return uid;
    }



}
