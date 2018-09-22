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
                loadKnownAndUnknownWords();
               // getKnownWordsListener();
                break;
            case "checkFillupknownList":
                checkFillupknownList();
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
        mDatabaseWords.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            Word wordDB = dataSnapshot.child(taskId).getValue(Word.class);

                            if (wordDB.getnazwaPl().equals("")) {
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
                    load3.setText("0 Uncomplited Words Loaded");
                } else {
                    load3.setText(Constatus.WORD_LIST.size() + " Uncomplited Words Loaded");
                }

                checkLoadingDataFinish("wordsLoaded");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    //TODO DONE load4, load5
    public void loadKnownAndUnknownWords() {
        load4.setText("Known Words Loading...");
        load5.setText("Unknown Words Loading...");
        load4.setVisibility(View.VISIBLE);
        load5.setVisibility(View.VISIBLE);

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("Words")) {
                    // check count of unknown words and rand new words if need


                    // boolean containsElement = list.contains("element 1");
                    for (DataSnapshot dsp : dataSnapshot.child("Words").getChildren()) {
                        if (dataSnapshot.getChildren() != null) {
                            String taskId = dsp.getKey().toString();
                            if (dataSnapshot.child(taskId).getChildren() != null) {
                                Word wordDB = dataSnapshot.child(taskId).getValue(KnownWord.class);
                                Constatus.KNOWN_WORD_LIST.add(wordDB);
                            }
                        }
                    }

                } else {
                    //rand new unknown words
                    Constatus.UNKNOWN_WORD_LIST.addAll(Constatus.WORD_LIST);
                }

                if ((Constatus.KNOWN_WORD_LIST.isEmpty())) {
                    load4.setText("0 Known Words Loaded");
                } else {
                    load4.setText(Constatus.KNOWN_WORD_LIST.size() + " Known Words Loaded");
                }
                if ((Constatus.UNKNOWN_WORD_LIST.isEmpty())) {
                    load5.setText("0 Unknown Words Loaded");
                } else {
                    load5.setText(Constatus.UNKNOWN_WORD_LIST.size() + " Unknown Words Loaded");
                }
                checkLoadingDataFinish("checkFillupknownList");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //TODO DONE load6
    public void checkFillupknownList(){
        if ((Constatus.KNOWN_WORD_LIST.isEmpty() || Constatus.KNOWN_WORD_LIST.size() < 20)) {
            //FIRST RUN
            load6.setText("Checking New Words... ");

            int count = 20 - Constatus.KNOWN_WORD_LIST.size();
            randNewWords(count);
        } else {
            load6.setText("Fillup Known List Finished... ");
        }

    }

    //TODO
    public void randNewWords(int count) {

        if (Constatus.UNKNOWN_WORD_LIST.size() > 0) {
            for (int i = 0; i < count; i++) {
                final KnownWord knownWord = new KnownWord();
                int listSize = Constatus.UNKNOWN_WORD_LIST.size();
                Random rand = new Random();
                int randWord = rand.nextInt(listSize);

                knownWord.setid(Constatus.UNKNOWN_WORD_LIST.get(randWord).getmId());
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
                        refUser.child(knownWord.getmId().toString()).setValue(knownWord);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
            checkLoadingDataFinish("checkFillupknownList");
        }

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

                                if (wordDB.getnazwaPl().equals("")) {
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
