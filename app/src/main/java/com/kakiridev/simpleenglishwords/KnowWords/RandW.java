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

/***
        //back button
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
*
        com.kakiridev.simpleenglishwords.FirebaseDatabase fb = new com.kakiridev.simpleenglishwords.FirebaseDatabase();
        Boolean fbb = fb.knownListIsExist();
        if (fbb){
            Log.d("FBB", "1");
        } else {
            Log.d("FBB", "2");
        }



        //startLoadingDialog();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        listOfWords = new ArrayList<Word>();
        listOfKnownWords = new ArrayList<Word>();
        listOfUnknownWords = new ArrayList<Word>();

        startGetWords();****/
/**        FirebaseListenert();**/

        /**
         * check count of known words [if < 10 then knownNewWords]
         * check averageScore [if > 80 then knowNewWords]
         */

        Button buttonRand = findViewById(R.id.randWords);
        buttonRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                Log.d("users", "1");
                com.kakiridev.simpleenglishwords.FirebaseDatabase fb = new com.kakiridev.simpleenglishwords.FirebaseDatabase();
                User user = fb.getFirebaseUser();
**/
                /**
                boolean fbb = fb.isExistUser(user);

                if (fbb){
                    Log.d("users", "userExist");
                } else {
                    Log.d("users", "userDontExist");
                }
                 **/
                //updateKnownCountStatus(averageScore, countKnownWords);
                //startGetWords();
            }
        });

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
    private void FirebaseListenert(){
        DatabaseReference mDatabaseWords = FirebaseDatabase.getInstance().getReference().child("Words");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("DTAG", "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
    //            Comment comment = dataSnapshot.getValue(Comment.class);

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("DTAG", "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
         //       Comment newComment = dataSnapshot.getValue(Comment.class);
          //      String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d("DTAG", "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
         //       String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d("DTAG", "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
          //      Comment movedComment = dataSnapshot.getValue(Comment.class);
          //      String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DTAG", "postComments:onCancelled", databaseError.toException());
                //Toast.makeText(mContext, "Failed to load comments.", Toast.LENGTH_SHORT).show();
            }
        };
        mDatabaseWords.addChildEventListener(childEventListener);
    }

**/













/***
    //get all words from firebase
    private void startGetWords(){

        DatabaseReference mDatabaseWords = FirebaseDatabase.getInstance().getReference().child("Words");
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DTAG", "onDataChange ");
                final ArrayList<Word> firebaseWordsList = new ArrayList<Word>();
                final ArrayList<Word> firebaseKnownWordsList = new ArrayList<Word>();
                final ArrayList<Word> firebaseUnknownWordsList = new ArrayList<Word>();
                String userId = getFirebaseUserId();
                int allScore = 0;

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    boolean knownWords = false;

                    String id = dsp.child("id").getValue().toString();
                    String pl = dsp.child("nazwaPl").getValue().toString();
                    String en = dsp.child("nazwaEn").getValue().toString();
                    String category = dsp.child("category").getValue().toString();

                    String score = "0";

                    if(dsp.child(userId).exists()){
                        score = dsp.child(userId).child("score").getValue().toString();
                        allScore = allScore + Integer.parseInt(score);
                        knownWords = true;
                    }

                    Word rekord = new Word(id, pl, en, category, score);

                    //add known/unknow words to list
                    if(knownWords) {
                        firebaseKnownWordsList.add(rekord);
                    } else {
                        firebaseUnknownWordsList.add(rekord);
                    }

                    //add all words to list
                    firebaseWordsList.add(rekord);
                }

                listOfWords = (ArrayList<Word>)firebaseWordsList.clone();
                listOfKnownWords = (ArrayList<Word>)firebaseKnownWordsList.clone();
                listOfUnknownWords = (ArrayList<Word>)firebaseUnknownWordsList.clone();
                countWords = listOfWords.size();
                countKnownWords = listOfKnownWords.size();
                countUnknownWords = listOfUnknownWords.size();
                totalScore = allScore;

                if (countKnownWords > 0 ){
                    averageScore = totalScore / countKnownWords;
                } else {
                    averageScore = 0;
                }

                Log.e("DTAG", "totalScore " + totalScore + ", averageScore " + averageScore + ", countKnownWords " + countKnownWords);
                if (firstGetWords) {
                    firstGetWords = false;
                } else {
                    updateKnownCountStatus(averageScore, countKnownWords);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };

        //mDatabaseWords.addListenerForSingleValueEvent(listener);
        mDatabaseWords.addValueEventListener(listener);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        if (!firstGetWords) {
            mDatabaseWords.removeEventListener(listener);
        }
        //mDatabaseWords.removeEventListener(listener);
    }
**/
    /** rand words from unknonwn list and return word
    private Word randNewWords(ArrayList<Word> unknowWords){
        int count = unknowWords.size()-1;
        Random r = new Random();
        int Low = 0;
        int High = count;
        int Result = r.nextInt(High-Low) + Low;

        Word word = new Word();
        word = unknowWords.get(Result);

        return word;
    }
**/
    /** check average score>80 or count of knonw words is < 4 and add new words
    private void updateKnownCountStatus(long averageScore, int knowCount){
        if(averageScore > 80 || knowCount < 4){
            Word word = new Word();
            word = randNewWords(listOfUnknownWords);
            knowNewWord(word.getid());

            startGetWords();
        }
    }
**/
    /** add userId and score=0 to word
    private void knowNewWord(String word){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        String userId = getFirebaseUserId();

        HashMap<String, String> dataMap = new HashMap<String, String>(); //kolejność wczytania do bazy odwrotna niz zapisu
        dataMap.put("score", "0");

        mDatabase.child("Words").child(word).child(userId).setValue(dataMap);
    }
**/
/**
    private void startLoadingDialog(){

        final ProgressDialog pd = new ProgressDialog(RandW.this);
        pd.setIndeterminate(false);

        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        pd.setCancelable(true);
        pd.setMax(100);

        pd.show();

        progressStatus = 0;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(progressStatus < 100){

                    //progressStatus +=1;

                    try{
                        Thread.sleep(20);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            pd.setProgress(progressStatus);
                            Log.e("DTAG", "progressStatus " + progressStatus);
                            if(progressStatus == 100){
                               // pd.dismiss();
                            }
                        }
                    });
                }
            }
        }).start(); // Start the operation
    }
**/
/******* SIMPLE FB **********/
    /** check known words and return known=true/false **/

    /**
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
        //rootRef.removeEventListener(listener);

        return known;
    }
**/
    /** get firebase user id **/
    private String getFirebaseUserId(){
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        String uid = currentFirebaseUser.getUid().toString();
        return uid;
    }



}
