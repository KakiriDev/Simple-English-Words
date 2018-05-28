package com.kakiridev.simpleenglishwords;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDatabase {
    long count;
    public FirebaseResponseListener listener;
    public FirebaseGetAllWordsListener getListOfWordsListener;
    ArrayList<Word> fbWords;

    public void getCountWordsFromFirebase() {
        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Words");
        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = dataSnapshot.getChildrenCount();
                listener.onFirebaseResponseReceived((int) count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getListOfWords(final boolean isBlank) {

        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Words");

        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fbWords = new ArrayList<Word>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    String id = dsp.child("id").getValue().toString();
                    String pl = dsp.child("nazwaPl").getValue().toString();
                    String en = dsp.child("nazwaEn").getValue().toString();
                    String category = dsp.child("category").getValue().toString();

                    if (isBlank) { //true only blanks
                        if(pl.equals("")){
                            Word rekord = new Word(id, pl, en, category);
                            fbWords.add(rekord);
                        }
                    } else {
                        Word rekord = new Word(id, pl, en, category);
                        fbWords.add(rekord);
                    }

                }
                getListOfWordsListener.onFirebaseGetAllWordsListener(fbWords);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }
}

