package com.kakiridev.simpleenglishwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class DataLoading extends AppCompatActivity {
    TextView load1, load2, load3, load4, load5, load6, load7;

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
        load7 = findViewById(R.id.load7);
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
                break;
            case "checkFillupknownList":
                checkFillupknownList();
                break;
            case "loadFinished":
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

    //TODO DONE load2, load3, load4
    public void loadWords() {
        load2.setText("Words Loading...");
        load3.setText("Uncomplited Words Loading...");
        load4.setText("Complited Words Loading...");
        load2.setVisibility(View.VISIBLE);
        load3.setVisibility(View.VISIBLE);
        load4.setVisibility(View.VISIBLE);

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
                            } else {
                                Constatus.WORD_COMPLITED_LIST.add(wordDB);
                            }
                            Constatus.WORD_LIST.add(wordDB);
                        }
                    }
                }

                if ((Constatus.WORD_LIST.isEmpty())) {
                    load2.setText("0 Words Loaded");
                } else {
                    load2.setText(Constatus.WORD_LIST.size() + " Words Loaded");
                }
                if ((Constatus.WORD_UNCOMPLITED_LIST.isEmpty())) {
                    load3.setText("0 Uncomplited Words Loaded");
                } else {
                    load3.setText(Constatus.WORD_UNCOMPLITED_LIST.size() + " Uncomplited Words Loaded");
                }
                if ((Constatus.WORD_COMPLITED_LIST.isEmpty())) {
                    load4.setText("0 Complited Words Loaded");
                } else {
                    load4.setText(Constatus.WORD_COMPLITED_LIST.size() + " Complited Words Loaded");
                }

                checkLoadingDataFinish("wordsLoaded");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    //TODO DONE load5, load6
    public void loadKnownAndUnknownWords() {
        load5.setText("Known Words Loading...");
        load6.setText("Unknown Words Loading...");
        load5.setVisibility(View.VISIBLE);
        load6.setVisibility(View.VISIBLE);

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(Constatus.LOGGED_USER.getUserId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // if (dataSnapshot.hasChild("Words")) {
                    // check count of unknown words and rand new words if need
                    Constatus.UNKNOWN_WORD_LIST.addAll(Constatus.WORD_COMPLITED_LIST);

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

                if ((Constatus.KNOWN_WORD_LIST.isEmpty())) {load5.setText("0 Known Words Loaded");} else {
                    load5.setText(Constatus.KNOWN_WORD_LIST.size() + " Known Words Loaded");}
                if ((Constatus.UNKNOWN_WORD_LIST.isEmpty())) {load6.setText("0 Unknown Words Loaded");} else {
                    load6.setText(Constatus.UNKNOWN_WORD_LIST.size() + " Unknown Words Loaded");}

                checkLoadingDataFinish("checkFillupknownList");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //TODO DONE load7
    public void checkFillupknownList(){
        load7.setVisibility(View.VISIBLE);
        if ((Constatus.KNOWN_WORD_LIST.isEmpty() || Constatus.KNOWN_WORD_LIST.size() < 10)) {
            //FIRST RUN
            load7.setText("Checking New Words... ");

            int count = 10 - Constatus.KNOWN_WORD_LIST.size();
            randNewWords(count);
        } else {

            if ((Constatus.KNOWN_WORD_LIST.isEmpty())) {
                load5.setText("0 Known Words Loaded");
            } else {
                load5.setText(Constatus.KNOWN_WORD_LIST.size() + " Known Words Loaded");
            }
            if ((Constatus.UNKNOWN_WORD_LIST.isEmpty())) {
                load6.setText("0 Unknown Words Loaded");
            } else {
                load6.setText(Constatus.UNKNOWN_WORD_LIST.size() + " Unknown Words Loaded");
            }

        load7.setText("CLICK TO START!");

            Log.d("logii", "--------------USER_LIST--------------: '/n'" + Constatus.USER_LIST.toString());
            Log.d("logii", "--------------WORD_LIST--------------: " + Constatus.WORD_LIST.toString());
            Log.d("logii", "--------------LOGGED_USER--------------: " + Constatus.LOGGED_USER.toString());
            Log.d("logii", "--------------WORD_UNCOMPLITED_LIST--------------: " + Constatus.WORD_UNCOMPLITED_LIST.toString());
            Log.d("logii", "--------------WORD_COMPLITED_LIST--------------: " + Constatus.WORD_COMPLITED_LIST.toString());
            Log.d("logii", "--------------KNOWN_WORD_LIST--------------: " + Constatus.KNOWN_WORD_LIST.toString());
            Log.d("logii", "--------------UNKNOWN_WORD_LIST--------------: " + Constatus.UNKNOWN_WORD_LIST.toString());

            checkLoadingDataFinish("loadFinished");
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
            checkLoadingDataFinish("checkFillupknownList");
        }
    }


    public void addClick(){
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
    }


}
