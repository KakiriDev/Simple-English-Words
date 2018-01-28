package com.kakiridev.simpleenglishwords;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class StartView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_view);

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
    }

    public void startActivity_AddNewWord (){
        Log.v("DTAG", "Class name: " + Thread.currentThread().getStackTrace()[2].getClassName() + " Method name: " + Thread.currentThread().getStackTrace()[2].getMethodName());

        Intent i = new Intent(this, AddWordView.class);
        startActivity(i);
    }

    public void startActivity_WordListView (){
        Log.v("DTAG", "Class name: " + Thread.currentThread().getStackTrace()[2].getClassName() + " Method name: " + Thread.currentThread().getStackTrace()[2].getMethodName());

        Intent i = new Intent(this, WordListView.class);
        startActivity(i);
    }


    // When sb add new word to firebase
    public void addNewWordToList(){

    }

    // When sb update word in firebase
    public void updateWord(){

    }

    // When sb delete word from firebase
    public void deleteWrd(){

    }


}
