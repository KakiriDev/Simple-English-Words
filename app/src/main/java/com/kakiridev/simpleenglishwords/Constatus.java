package com.kakiridev.simpleenglishwords;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Constatus {
    public static boolean INITIALIZED_LISTENERS = false;
    public static ArrayList<User> USER_LIST = new ArrayList<User>();
    public static ArrayList<Word> WORD_LIST = new ArrayList<Word>();
    public static ArrayList<Word> WORD_UNCOMPLITED_LIST = new ArrayList<Word>();
    public static ArrayList<Word> KNOWN_WORD_LIST = new ArrayList<Word>();
    public static ArrayList<Word> UNKNOWN_WORD_LIST = new ArrayList<Word>();

    public void getUsersListener(){
        Log.d("FB", "getUsers started");
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
                Log.d("FB", "getUsers finished, count of users: " + Constatus.USER_LIST.size() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getWordsListener() {
        Log.d("FB", "getWords started");
        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Words");
        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            Word wordDB = dataSnapshot.child(taskId).getValue(Word.class);

                            if(wordDB.getmNazwaPl().equals("")){
                                Constatus.WORD_UNCOMPLITED_LIST.add(wordDB);
                            }
                            Constatus.WORD_LIST.add(wordDB);
                        }
                    }
                }
                Log.d("FB", "getWords finished, count of words: " + Constatus.WORD_LIST.size() + ", Uncomplited: " + Constatus.WORD_UNCOMPLITED_LIST.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }


}
