package com.kakiridev.simpleenglishwords;

import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDatabaseUsers {


    /***********
     ** USERS **
     ***********/

    /**
     * return string userid
     **/
    private String getFirebaseUserId() {
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = currentFirebaseUser.getUid().toString();
        return uid;
    }


    /**
     * get firebase user and return user object
     **/
    public User getFirebaseUser() {

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        User user = new User();

        user.setUserId(currentFirebaseUser.getUid().toString());
        user.setUserName(currentFirebaseUser.getDisplayName().toString());
        user.setUserEmail(currentFirebaseUser.getEmail().toString());

        return user;
    }

    /**
     * get list of users
     **/
    public void getUsersFromDB(final GetUsersInterface getUsersInterface) {

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> userList = new ArrayList<User>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            User userDB = dataSnapshot.child(taskId).getValue(User.class);

                            userList.add(userDB);
                        }
                    }
                }
                getUsersInterface.getUsersList(userList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    /**
     * add user to firebase after login
     **/
    public void addUserToFirebase() {

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // User user = new User();
              //  user = getFirebaseUser();

                DatabaseReference refUser = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users");
                refUser.child(Constatus.LOGGED_USER.userId.toString()).setValue(Constatus.LOGGED_USER);
                //Constatus.LOGGED_USER = user;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
