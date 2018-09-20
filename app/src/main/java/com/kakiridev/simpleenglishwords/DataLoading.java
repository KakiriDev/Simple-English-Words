package com.kakiridev.simpleenglishwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class DataLoading extends AppCompatActivity {
    TextView load1, load2, load3, load4, load5, load6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_loading);

        textViewLoading();



        checkLoadingDataFinish("");
    }

    public void textViewLoading(){
        load1 = findViewById(R.id.load1);
        load2 = findViewById(R.id.load2);
        load3 = findViewById(R.id.load3);
        load4 = findViewById(R.id.load4);
        load5 = findViewById(R.id.load5);
        load6 = findViewById(R.id.load6);
    }

    public void checkLoadingDataFinish(String status) {
        switch (status) {
            default:
                loadUsers();
                break; //TODO DONE
            case "usersLoaded":
                loadWords();
                break; //TODO DONE
            case "wordsLoaded":
                getKnownWordsListener();
                break;
            case "":
                loadKnownAndUnknownWords();
                break;
        }
    }

    //TODO DONE load1
    public void loadUsers() {
        load1.setText("Users Loading...");
        load1.setVisibility(View.VISIBLE);
        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener() {
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

                if ((Constatus.USER_LIST.isEmpty())) {
                    load1.setText("0 Users Loaded");
                } else {
                    load1.setText(Constatus.USER_LIST.size() + " Users Loaded");
                }
                checkLoadingDataFinish("usersLoaded");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //TODO DONE load2, load3
    public void loadWords() {
        load2.setText("Words Loading...");
        load3.setText("Uncomplited Words Loading...");
        load2.setVisibility(View.VISIBLE);
        load3.setVisibility(View.VISIBLE);

        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Words");
        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            Word wordDB = dataSnapshot.child(taskId).getValue(Word.class);

                            if (wordDB.getmNazwaPl().equals("")) {
                                Constatus.WORD_UNCOMPLITED_LIST.add(wordDB);
                            }
                            Constatus.WORD_LIST.add(wordDB);
                        }
                    }
                }

                if ((Constatus.USER_LIST.isEmpty())) {
                    load2.setText("0 Words Loaded");
                } else {
                    load2.setText(Constatus.WORD_LIST.size() + " Words Loaded");
                }
                if ((Constatus.USER_LIST.isEmpty())) {
                    load3.setText("0 Words Loaded");
                } else {
                    load3.setText(Constatus.WORD_LIST.size() + " Words Loaded");
                }

                checkLoadingDataFinish("wordsLoaded");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }


    //TODO
    public boolean isKnownWords(KnownWord knownWord){

        if (Constatus.UNKNOWN_WORD_LIST.isEmpty()){
            return false;
        } else if (Constatus.UNKNOWN_WORD_LIST.contains(knownWord)){
            Constatus.UNKNOWN_WORD_LIST.remove(knownWord);
            Constatus.KNOWN_WORD_LIST.add(knownWord);
            return true;
        }

    return false;
    }

    //TODO
    public KnownWord randNewWord() {
        KnownWord knownWord = new KnownWord();
        if (Constatus.WORD_LIST.size() > 0) {
            int listSize = Constatus.WORD_LIST.size();
            Random rand = new Random();
            int randWord = rand.nextInt(listSize);

            knownWord.setmId(Constatus.WORD_LIST.get(randWord).getmId());
            knownWord.setmCategory(Constatus.WORD_LIST.get(randWord).getmCategory());
            knownWord.setmNazwaEn(Constatus.WORD_LIST.get(randWord).getmNazwaEn());
            knownWord.setmNazwaPl(Constatus.WORD_LIST.get(randWord).getmNazwaPl());
            knownWord.setmScore(0);
            if (isKnownWords(knownWord)){
                return knownWord;
            }
        }
        return null;
    }

    //TODO <<---
    public void loadKnownAndUnknownWords() {
        Log.d("FB", "getOrCreateUncomplitedWordsList started");
        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Words")) {
                    // check count of unknown words and rand new words if need


                    // boolean containsElement = list.contains("element 1");
                    for (DataSnapshot dsp : dataSnapshot.child("Words").getChildren()) {
                        if (dataSnapshot.getChildren() != null) {
                            String taskId = dsp.getKey().toString();
                            if (dataSnapshot.child(taskId).getChildren() != null) {
                                Word wordDB = dataSnapshot.child(taskId).getValue(Word.class);
                                Constatus.KNOWN_WORD_LIST.add(wordDB);
                            }
                        }
                    }

                } else {
                    //rand new unknown words
                    Constatus.UNKNOWN_WORD_LIST.addAll(Constatus.WORD_LIST);
                }
/**
 for (DataSnapshot dsp : dataSnapshot.getChildren()) {
 if (dataSnapshot.getChildren() != null) {
 String taskId = dsp.getKey().toString();
 if (dataSnapshot.child(taskId).getChildren() != null) {
 User userDB = dataSnapshot.child(taskId).getValue(User.class);
 Constatus.USER_LIST.add(userDB);
 }
 }
 }
 userListLoaded = true;
 checkLoadingDataFinish();
 Log.d("FB", "getUsers finished, count of users: " + Constatus.USER_LIST.size());
 **/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    //TODO
    public void getKnownWordsListener() {
        Log.d("FB", "getKnownWords started");
        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
//        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(LOGGED_USER.getUserId());
        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if (dataSnapshot.hasChild("KnownWords")) {
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        if (dataSnapshot.getChildren() != null) {
                            if (dataSnapshot.child("KnownWords").getChildren() != null) {
                                KnownWord wordDB = dataSnapshot.child("KnownWords").getValue(KnownWord.class);

                                if (wordDB.getmNazwaPl().equals("")) {
                                    Constatus.KNOWN_WORD_LIST.add(wordDB);
                                }
                                Constatus.WORD_LIST.add(wordDB);
                            }
                        }
                    }
                }
                Log.d("FB", "getKnownWords finished, count of words: " + Constatus.WORD_LIST.size() + ", Uncomplited: " + Constatus.WORD_UNCOMPLITED_LIST.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }


    public void startMainActivity() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
    }


}
