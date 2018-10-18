package com.kakiridev.simpleenglishwords;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.AddNewWord.AddWordView;
import com.kakiridev.simpleenglishwords.KnowWords.RandW;
import com.kakiridev.simpleenglishwords.LoginView.LoginView;
import com.kakiridev.simpleenglishwords.ShowListWords.WordListView;

import java.util.ArrayList;

public class MainView extends AppCompatActivity implements FirebaseResponseListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //view objects
    private TextView textViewUserEmail;
    public TextView TV_wordsCount;

    public static ArrayList<User> userList;

    public static ArrayList<Word> getAllWords(){
        ArrayList<Word> words = new ArrayList<>();

        DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList = new ArrayList<User>();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (dataSnapshot.getChildren() != null) {
                        String taskId = dsp.getKey().toString();
                        if (dataSnapshot.child(taskId).getChildren() != null) {
                            User userDB = dataSnapshot.child(taskId).getValue(User.class);

                            userList.add(userDB);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return words;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        InitializeAuth();
        if (isLogedUser()) {
            setUserFields(getLogUser());
        }

        getAllWords();

        startCountWordsListener();

        Button btnToListView = findViewById(R.id.btn_show_wordslist);
        btnToListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity_WordListView();
            }
        });

        Button btnAddNewWord = findViewById(R.id.btn_add_word);
        btnAddNewWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity_AddNewWord();
            }
        });

        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Constatus.LOGGED_USER = null;
                startActivity_LoginView();
            }
        });

        Button buttonRand = findViewById(R.id.randWords);
        buttonRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity_RandW();
            }
        });
        Button buttonStats = findViewById(R.id.statistic);
        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity_Statistic();
            }
        });

    }

    /**
     * Start Activity
     **/
    public void startActivity_AddNewWord() {
        Intent i = new Intent(this, AddWordView.class);
        startActivity(i);
    }

    public void startActivity_WordListView() {
        Intent i = new Intent(this, WordListView.class);
        startActivity(i);
    }

    public void startActivity_LoginView() {
        Intent i = new Intent(this, LoginView.class);
        startActivity(i);
    }

    public void startActivity_RandW() {
        Intent i = new Intent(this, RandW.class);
        startActivity(i);
    }

    public void startActivity_Statistic() {
        Intent i = new Intent(this, Statistic.class);
        startActivity(i);
    }

    public void startCountWordsListener() {
        FirebaseDatabase fb = new FirebaseDatabase();
        fb.listener = this;
        fb.getCountWordsFromFirebase();
    }


    @Override
    public void onFirebaseResponseReceived(int count) {
        setCoutWords(count);
    }

    public void setCoutWords(long count) {
        Log.e("DTAG", "setCount0" + count);

        TV_wordsCount = (TextView) findViewById(R.id.words_Count);
        TV_wordsCount.setText(Long.toString(count));

    }


    /**
     * Login and Auth
     **/
    //check login status
    private boolean isLogedUser() {
        boolean loged = false;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if (user.getDisplayName() != null) {
                loged = true;
            }
        } else {
            loged = false;
        }
        return loged;
    }

    private void InitializeAuth() {
        textViewUserEmail = (TextView) findViewById(R.id.textViewUserEmail);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        /**
         mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        }
        }; **/
    }

    private FirebaseUser getLogUser() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        return user;
    }

    private void setUserFields(FirebaseUser user) {
        textViewUserEmail.setText("Witaj " + user.getDisplayName().toString());
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        textViewUserEmail.setText(" ".toString());

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                textViewUserEmail.setText(" ".toString());
            }
        });
    }


}
