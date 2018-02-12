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

public class Testowe extends AppCompatActivity {

    private TextView nameTextView;
    private TextView emailTextView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testowe);

        Log.d("DTAG", "testowe");
        InitializeAuth();
        Log.d("DTAG", "InitializeAuth");
        if(isLogedUser()) {
            Log.d("DTAG", "T");
            setUserFields(getLogUser());
        }
        Log.d("DTAG", "setUserFields");

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singOut();
                startLoginActivity();
            }
        });
    }

    private boolean isLogedUser(){
        boolean loged = false;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            if(user.getDisplayName() != null) {
                loged = true;
            }
        } else {
            loged = false;
        }
        return loged;
    }

    public void startLoginActivity(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void InitializeAuth(){
        nameTextView = (TextView)findViewById(R.id.name_text_view);
        emailTextView = (TextView)findViewById(R.id.email_text_view);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

    private FirebaseUser getLogUser(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        return user;
    }

    private void setUserFields(FirebaseUser user){
        Log.d("DTAG", "name " + user.getDisplayName().toString());
        Log.d("DTAG", "mail " + user.getEmail().toString());
        nameTextView.setText("HI " + user.getDisplayName().toString());
        emailTextView.setText(user.getEmail().toString());

    }

    private void singOut(){
        FirebaseAuth.getInstance().signOut();
        emailTextView.setText(" ".toString());
        nameTextView.setText(" ".toString());

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                emailTextView.setText(" ".toString());
                nameTextView.setText(" ".toString());
            }
        });
    }
}
