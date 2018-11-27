package com.kakiridev.simpleenglishwords;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class DataLoading extends AppCompatActivity {
    TextView load1, load2, load3, load4, load5;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_loading);


        progresLoading();
        textViewLoading();

        checkLoadingDataFinish("");
    }
    @Override
    public void onBackPressed() { }

    private void progresLoading() {
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        textView = (TextView) findViewById(R.id.progress_text);
    }

    private void updateLoadingStatus(int status) {
        progressBar.setProgress(status);
        textView.setText(status + "/" + progressBar.getMax());
    }

    public void textViewLoading() {
        load1 = findViewById(R.id.load1);
        load2 = findViewById(R.id.load2);
        load3 = findViewById(R.id.load3);
        load4 = findViewById(R.id.load4);
        load5 = findViewById(R.id.load5);
    }

    public void checkLoadingDataFinish(String status) {
        switch (status) {
            default:
                updateLoadingStatus(0);
                loadUsers();
                break;
            case "usersLoaded":
                updateLoadingStatus(25);
                loadWords();
                break;
            case "wordsLoaded":
                updateLoadingStatus(50);
                loadKnownAndUnknownWords();
                break;
            case "checkFillupknownList":
                updateLoadingStatus(75);
                checkFillupknownList();
                break;
            case "loadFinished":
                updateLoadingStatus(100);
                addClick();
                break;

        }
    }


    //TODO DONE load1
    public void loadUsers() {
        load1.setText("Users Loading...");
        load1.setVisibility(View.VISIBLE);
        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            User userDB = dataSnapshot.child(taskId).getValue(User.class);
                            Constatus.USER_LIST.add(userDB);
                        }
                    }
                }

                load1.setText("Users Loaded");

                checkLoadingDataFinish("usersLoaded");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //TODO DONE load2
    public void loadWords() {
        load2.setText("Words Loading...");
        load2.setVisibility(View.VISIBLE);

        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Words");
        mDatabaseWords.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            Word wordDB = dataSnapshot.child(taskId).getValue(Word.class);

                            Constatus.WORD_LIST.add(wordDB);
                        }
                    }
                }
                load2.setText("Words Loaded");

                checkLoadingDataFinish("wordsLoaded");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    //TODO DONE load3
    public void loadKnownAndUnknownWords() {
        load3.setText("Known and Unknown Words Loading...");
        load3.setVisibility(View.VISIBLE);

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // if (dataSnapshot.hasChild("Words")) {
                // check count of unknown words and rand new words if need
                Constatus.UNKNOWN_WORD_LIST.addAll(Constatus.WORD_LIST);

                // boolean containsElement = list.contains("element 1");
                for (DataSnapshot dsp : dataSnapshot.child("Words").getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            Word wordDB = dataSnapshot.child("Words").child(taskId).getValue(Word.class);
                            Constatus.KNOWN_WORD_LIST.add(wordDB);
                            // Constatus.UNKNOWN_WORD_LIST.indexOf(wordDB.getid());

                            for (int i = 0; i < Constatus.UNKNOWN_WORD_LIST.size(); i++) {
                                if (Constatus.UNKNOWN_WORD_LIST.get(i).getid().equalsIgnoreCase(wordDB.getid())) {
                                    Constatus.UNKNOWN_WORD_LIST.remove(i);
                                    break;
                                }
                            }
                        }
                    }
                }
                load3.setText("Known and Unknown Words Loaded");

                checkLoadingDataFinish("checkFillupknownList");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //TODO DONE load4 load 5
    public void checkFillupknownList() {
        load4.setVisibility(View.VISIBLE);
        load4.setText("Checking New Words... ");
        if ((Constatus.KNOWN_WORD_LIST.isEmpty() || Constatus.getUncomplitedWords() < Constatus.numberOfMinWords)) {
            //FIRST RUN


            int count = Constatus.numberOfMinWords - Constatus.getUncomplitedWords();
            randNewWords(count, true);
        } else {
            load4.setText("New Words Loaded");

            load5.setVisibility(View.VISIBLE);
            load5.setText("CLICK TO START!");

            checkLoadingDataFinish("loadFinished");
        }
    }

    //TODO
    public void randNewWords(int count, boolean isDataLoading) {

        if (Constatus.UNKNOWN_WORD_LIST.size() > 0) {
            for (int i = 0; i < count; i++) {
                final KnownWord knownWord = new KnownWord();
                int listSize = Constatus.UNKNOWN_WORD_LIST.size();
                Random rand = new Random();
                int randWord = rand.nextInt(listSize);

                knownWord.setid(Constatus.UNKNOWN_WORD_LIST.get(randWord).getid());
                knownWord.setcategory(Constatus.UNKNOWN_WORD_LIST.get(randWord).getcategory());
                knownWord.setnazwaEn(Constatus.UNKNOWN_WORD_LIST.get(randWord).getnazwaEn());
                knownWord.setnazwaPl(Constatus.UNKNOWN_WORD_LIST.get(randWord).getnazwaPl());
                knownWord.setscore(0);

                Constatus.KNOWN_WORD_LIST.add(knownWord);
                Constatus.UNKNOWN_WORD_LIST.remove(randWord);

                DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId());
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        DatabaseReference refUser = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId()).child("Words");
                        refUser.child(knownWord.getid().toString()).setValue(knownWord);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            if(isDataLoading) {
                checkLoadingDataFinish("checkFillupknownList");
            }
        }
    }


    public void addClick() {

        // startMainActivity();

        LinearLayout layoutClick = findViewById(R.id.layoutClick);
        layoutClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });

    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
        finish();
    }


}
