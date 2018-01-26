package com.kakiridev.simpleenglishwords;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddWordView extends AppCompatActivity {

    EditText ET_PL;
    EditText ET_EN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_view);

        ET_PL = findViewById(R.id.PL);
        ET_EN = findViewById(R.id.EN);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ET_EN.getText().length() <= 0) {
                    Toast.makeText(AddWordView.this, "Wprowadź poprawne dane.", Toast.LENGTH_LONG).show();
                } else {
                    String Pl = ET_PL.getText().toString();
                    String En = ET_EN.getText().toString();
                    saveWord(Pl, En);
                }
            }
        });
    }

    // Add new word to firebase
    private void saveWord(final String nazwaPl, final String nazwaEn){
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Words"); //pobranie referencji do bazy
        String key = mDatabase.push().getKey();

        HashMap<String, String> dataMap = new HashMap<String, String>(); //kolejność wczytania do bazy odwrotna niz zapisu
        dataMap.put("id", key);
        dataMap.put("nazwaPl", nazwaPl);
        dataMap.put("nazwaEn", nazwaEn);
        dataMap.put("category", "Other");

        //sprawdzenie poprawnosci zapisu do bazy
        mDatabase.child(key).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddWordView.this, "Dodano nowe słowo: "+ nazwaPl + " / " + nazwaEn + "." , Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddWordView.this, "Error.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
