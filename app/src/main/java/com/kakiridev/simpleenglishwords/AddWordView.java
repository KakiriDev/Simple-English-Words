package com.kakiridev.simpleenglishwords;

import android.content.Intent;
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
import com.google.firebase.database.Query;

import java.util.HashMap;

public class AddWordView extends AppCompatActivity {

    EditText ET_PL;
    EditText ET_EN;
    Boolean isEdit = false;
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_view);

        ET_PL = findViewById(R.id.PL);
        ET_EN = findViewById(R.id.EN);

        Bundle bundle = getBundle();
        isEdit = checkIsEdit(bundle);

        if(isEdit){
            setET(bundle);
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ET_EN.getText().length() <= 0) {
                    Toast.makeText(AddWordView.this, "Wprowadź poprawne dane.", Toast.LENGTH_LONG).show();
                } else {
                    String Pl = ET_PL.getText().toString();
                    String En = ET_EN.getText().toString();
                    if(isEdit) {
                        saveWord(Pl, En, true, id);
                    } else {
                        saveWord(Pl, En, false, id);
                    }
                }
            }
        });
    }

    public void setET(Bundle bundle){
        ET_PL.setText(bundle.getString("nazwaPl"));
        ET_EN.setText(bundle.getString("nazwaEn"));
        id = bundle.getString("id");
    }

    public boolean checkIsEdit(Bundle bundle){
        if (bundle!=null) {
            if(bundle.containsKey("isEdit")) {
                return true;
            }
        }
        return false;
    }

    public Bundle getBundle (){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return bundle;
    }

    // Add new word to firebase
    private void saveWord(final String nazwaPl, final String nazwaEn, Boolean isEdit, String id){
       /** DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Words"); //pobranie referencji do bazy
        String key;
**/
        String key;
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        if (isEdit) {
            key = id;
        } else {
            key = mDatabase.child("Words").push().getKey();
        }

        HashMap<String, String> dataMap = new HashMap<String, String>(); //kolejność wczytania do bazy odwrotna niz zapisu
        dataMap.put("id", key);
        dataMap.put("nazwaPl", nazwaPl);
        dataMap.put("nazwaEn", nazwaEn);
        dataMap.put("category", "Other");

        //sprawdzenie poprawnosci zapisu do bazy
        mDatabase.child("Words").child(key).setValue(dataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if (checkIsEdit(getBundle())) {
                        startListViewActivity();
                        Toast.makeText(AddWordView.this, "Edytowano słowo: "+ nazwaPl + " / " + nazwaEn + "." , Toast.LENGTH_LONG).show();
                    } else {
                        startMainActivity();
                        Toast.makeText(AddWordView.this, "Dodano nowe słowo: "+ nazwaPl + " / " + nazwaEn + "." , Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AddWordView.this, "Error.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void startMainActivity(){
        Intent intent = new Intent(this, StartView.class);
        startActivity(intent);
    }

    public void startListViewActivity(){
        Intent intent = new Intent(this, WordListView.class);
        startActivity(intent);
    }
}
