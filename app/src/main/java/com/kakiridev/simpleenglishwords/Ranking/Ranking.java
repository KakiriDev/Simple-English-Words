package com.kakiridev.simpleenglishwords.Ranking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.Constatus;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.ShowListWords.WordListViewAdapter;
import com.kakiridev.simpleenglishwords.User;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ranking extends AppCompatActivity {

    ArrayList<User> user_list = new ArrayList<User>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);


/**
 * get all users
 * add users to list
 * sort list
 * show first 20 users with scores
 *
 *
 * implement adding score to firebase on user
 */

    }

    //TODO DONE load1
    public void loadUsers() {
        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            User userDB = new User();
                            userDB.setUserName(dataSnapshot.child(taskId).child("userName").getValue().toString());
                            userDB.setUserEmail(dataSnapshot.child(taskId).child("userEmail").getValue().toString());
                            userDB.setUserScore(dataSnapshot.child(taskId).child("userScore").getValue().toString());
                            userDB.setUserId(dataSnapshot.child(taskId).child("userId").getValue().toString());
                            user_list.add(userDB);
                        }
                    }
                }

              //  setAdapter(user_list);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setAdapter(ArrayList<User> userList){
        ArrayList<User> users = new ArrayList<User>();
        users.addAll(userList);

        //sort list - descending
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User lhs, User rhs) {
                int lhs_int = Integer.parseInt(lhs.getUserScore());
                int rhs_int = Integer.parseInt(rhs.getUserScore());

                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs_int > rhs_int ? -1 : (lhs_int < rhs_int) ? 1 : 0;
            }
        });

        adapter = new WordListViewAdapter(this, R.layout.word_listview_row, word_list);
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }


}
