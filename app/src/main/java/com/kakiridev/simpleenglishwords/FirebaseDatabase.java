package com.kakiridev.simpleenglishwords;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.AddNewWord.AddWordView;

import java.util.ArrayList;
import java.util.HashMap;

public class FirebaseDatabase {

    public FirebaseResponseListener listener;
    public FirebaseGetAllWordsListener getListOfWordsListener;
    public FirebaseGetAllKnownWordsListener getListOfKnownWordsListener;

    long count;
    ArrayList<Word> fbWords;
    ArrayList<KnownWord> fbKnownWords;


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
                        if (pl.equals("")) {
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

    //TODO get from allWordsList
    public void getListOfUnknownWords() {

        //check that unknown word list > 0
    }

    //TODO get all from knownList
    public void getListOfknownWords() {

    }

    //TODO rand new word from unknownList
    public void randNewWord() {

    }

    //TODO get Word by knownWordId
    public Word getWordByKnownWordId(String knownWordId) {
        Word word = new Word();

        return word;
    }

    //TODO add word to known list
    public void knowNewWord() {

    }

    //TODO check knonwList


    //TODO add new word to db





}

