package com.kakiridev.simpleenglishwords;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDatabase {
    long count;
    public FirebaseResponseListener listener;
    public FirebaseResponseListener getListOfWordsListener;


    public void getCountWordsFromFirebase() {
        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Words");
        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = dataSnapshot.getChildrenCount();
                listener.onFirebaseResponseReceived((int)count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getListOfWords() {

        DatabaseReference mDatabaseWords = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Words");
        mDatabaseWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = dataSnapshot.getChildrenCount();
                listener.onFirebaseResponseReceived((int)count);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
}
