package com.kakiridev.simpleenglishwords;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.kakiridev.simpleenglishwords.LoginView.LoginView;
import com.kakiridev.simpleenglishwords.Ranking.Ranking;
import com.kakiridev.simpleenglishwords.ShowListWords.WordListView;
import com.kakiridev.simpleenglishwords.Statistic.Statistic;

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

    LinearLayout anim1, anim2, anim3;
    GridLayout black_tab;
    Animation fromLeft1, fromLeft2, fromLeft3, fromTop, toolbar;
    TextView hello, score, ranked;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(); //TODO TOOLBAR
        setContentView(R.layout.activity_main_view);

        startAnim();

        InitializeAuth();

        startCountWordsListener();
        initializeButtons();

    }

    //TODO TOOLBAR
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
    //TODO TOOLBAR
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

    private void initializeButtons(){
        /** start game **/
        Button btnToListView = findViewById(R.id.btn_start_round);
        btnToListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity_RoundView();
            }
        });

        /** TODO logout **/
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                Constatus.LOGGED_USER = null;
                startActivity_LoginView();
            }
        });

        /** word list **/
        Button buttonRand = findViewById(R.id.btn_show_word_list);
        buttonRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity_WordListView();
            }
        });

        /** statistic **/
        Button buttonStats = findViewById(R.id.btn_show_ranking);
        buttonStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity_Statistic();
            }
        });

        /** Ranking **/
        LinearLayout buttonRanking = findViewById(R.id.table);
        buttonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity_Ranking();
            }
        });

    }

    private void startAnim(){

        fromLeft1 = AnimationUtils.loadAnimation(this, R.anim.from_left1);
        fromLeft2 = AnimationUtils.loadAnimation(this, R.anim.from_left2);
        fromLeft3 = AnimationUtils.loadAnimation(this, R.anim.from_left3);
        fromTop = AnimationUtils.loadAnimation(this, R.anim.from_top);

        anim1 = findViewById(R.id.LL);
        anim1.setAnimation(fromLeft1);

        anim2 = findViewById(R.id.LL1);
        anim2.setAnimation(fromLeft2);

        anim3 = findViewById(R.id.LL2);
        anim3.setAnimation(fromLeft3);

        black_tab = findViewById(R.id.black_tab);
        black_tab.setAnimation(fromTop);


        //eraserdust +
        //eraserregular ++
        //grafipaint -
        //chawp +
        //squeaky +++

        Typeface typeface = ResourcesCompat.getFont(this, R.font.squeaky);
        Typeface typeface1 = ResourcesCompat.getFont(this, R.font.squeaky);
        Typeface typeface2 = ResourcesCompat.getFont(this, R.font.squeaky);

        hello = findViewById(R.id.hello);
        score = findViewById(R.id.score);
        ranked= findViewById(R.id.ranked);

        hello.setTypeface(typeface);
        score.setTypeface(typeface1);
        ranked.setTypeface(typeface2);
    }

    public void startActivity_RoundView() {
        Intent i = new Intent(this, RoundView.class);
        startActivity(i);
        finish();
    }

    public void startActivity_Ranking() {
        Intent i = new Intent(this, Ranking.class);
        startActivity(i);
        finish();
    }

    public void startActivity_WordListView() {
        Intent i = new Intent(this, WordListView.class);
        startActivity(i);
        finish();
    }

    public void startActivity_LoginView() {
        Intent i = new Intent(this, LoginView.class);
        startActivity(i);
        finish();
    }

    public void startActivity_Statistic() {
            Intent i = new Intent(this, Statistic.class);
            startActivity(i);
        finish();
    }

    public void startCountWordsListener() {
        FirebaseDatabase fb = new FirebaseDatabase();
        fb.listener = this;
        fb.getCountWordsFromFirebase();
    }

    @Override
    public void onBackPressed() { }


    @Override
    public void onFirebaseResponseReceived(int count) {
        setCoutWords(count);
    }

    public void setCoutWords(long count) {
        Log.e("DTAG", "setCount0" + count);

        TV_wordsCount = (TextView) findViewById(R.id.words_Count);
        TV_wordsCount.setText(Long.toString(count));

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
