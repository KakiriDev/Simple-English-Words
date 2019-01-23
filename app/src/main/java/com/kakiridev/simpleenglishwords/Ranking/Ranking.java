package com.kakiridev.simpleenglishwords.Ranking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.Constatus;
import com.kakiridev.simpleenglishwords.MainView;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.ShowListWords.WordListViewAdapter;
import com.kakiridev.simpleenglishwords.User;
import com.kakiridev.simpleenglishwords.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Ranking extends AppCompatActivity {

    UserListViewAdapter adapter;
    ListView listview;
    ArrayList<User> user_list = new ArrayList<User>();
    ImageView IV_back_to_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar();
        setContentView(R.layout.activity_ranking);
        loadUsers();
        IV_back_to_main = findViewById(R.id.iv_tab_buttonBack);
        initOnClickListener();
    }


    @Override
    public void onBackPressed() {}

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void setToolbar(){
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /** initialize click listener and check clicked word **/
    private void initOnClickListener() {
        IV_back_to_main.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity_MainView();
            }
        });
    }

    /** start main activity when back button is clicked **/
    public void startActivity_MainView() {
        Intent i = new Intent(this, MainView.class);
        startActivity(i);
        finish();
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

                setAdapter(user_list);

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
                // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                return lhs.getUserScoreToInt() > rhs.getUserScoreToInt() ? -1 : (lhs.getUserScoreToInt() < rhs.getUserScoreToInt()) ? 1 : 0;
            }
        });

        adapter = new UserListViewAdapter(this, R.layout.user_listview_row, users);
        listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }


}
