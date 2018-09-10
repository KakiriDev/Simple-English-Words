package com.kakiridev.simpleenglishwords.AddNewWord;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kakiridev.simpleenglishwords.MainView;
import com.kakiridev.simpleenglishwords.R;
import com.kakiridev.simpleenglishwords.ShowListWords.WordListView;

import java.util.HashMap;

public class AddWordView extends AppCompatActivity {

    EditText ET_PL;
    EditText ET_EN;
    Boolean isEdit = false;
    String id = "";
    Boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word_view);

        //back button
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        ET_PL = findViewById(R.id.PL);
        ET_EN = findViewById(R.id.EN);

        Bundle bundle = getBundle();
        isEdit = checkIsEdit(bundle);

        if (isEdit) {
            setET(bundle);
        }

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkNewWordInDatabase();

            }
        });
    }

    //back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //set ET if is in edit mode
    public void setET(Bundle bundle) {
        ET_PL.setText(bundle.getString("nazwaPl"));
        ET_EN.setText(bundle.getString("nazwaEn"));
        id = bundle.getString("id");
    }

    public boolean checkIsEdit(Bundle bundle) {
        if (bundle != null) {
            if (bundle.containsKey("isEdit")) {
                return true;
            }
        }
        return false;
    }

    public Bundle getBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        return bundle;
    }

    // Add new word to firebase
    private void saveWord(final String nazwaPl, final String nazwaEn, Boolean isEdit, String id) {
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
                if (task.isSuccessful()) {
                    if (checkIsEdit(getBundle())) {
                        startListViewActivity();
                        Toast.makeText(AddWordView.this, "Edytowano słowo: " + nazwaPl + " / " + nazwaEn + ".", Toast.LENGTH_LONG).show();
                    } else {
                        startMainActivity();
                        Toast.makeText(AddWordView.this, "Dodano nowe słowo: " + nazwaPl + " / " + nazwaEn + ".", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AddWordView.this, "Error.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void checkNewWordInDatabase() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Words");
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("DTAG", "status ppp");


//                for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
//                    String en = appleSnapshot.child("nazwaEn").getValue().toString().toLowerCase();
//
//                    en.equals(En);
//                }

                if (ET_EN.getText().length() <= 0) {
                    Toast.makeText(AddWordView.this, "Wprowadź poprawne dane.", Toast.LENGTH_LONG).show();
                } else {
                    String Pl = ET_PL.getText().toString();
                    String En = ET_EN.getText().toString();
                    if (isEdit) {
                        saveWord(Pl, En, true, id);
                    } else {
                        Log.e("DTAG", "status new");

                        for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                            String en = appleSnapshot.child("nazwaEn").getValue().toString().toLowerCase();
                            if (en.equals(En)){
                                status = false;
                            }
                        }
                        if (status) {
                            saveWord(Pl, En, false, id);
                        } else {
                            Log.e("DTAG", "już jest");
                        }
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DTAG", "onCancelled", databaseError.toException());
            }
        });

    }

    public void startMainActivity() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
    }

    public void startListViewActivity() {
        Intent intent = new Intent(this, WordListView.class);
        startActivity(intent);
    }
}
