package com.andras.realmdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmObject;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        realm = Realm.getDefaultInstance();

        Button saveButton = findViewById(R.id.save_button);
        Button loadButton = findViewById(R.id.load_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataToRealm();
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                startActivity(intent);
            }
        });

    }

    private void saveDataToRealm() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Note note1 = new Note();
                note1.setId(1);
                note1.setTitle("Realm in Action");
                note1.setContent("Dummy");

                Note note2 = new Note();
                note2.setId(2);
                note2.setTitle("New Realm");
                note2.setContent("Dummy");

                RealmList<Note> notes = new RealmList<>();
                notes.add(note1);
                notes.add(note2);

                realm.insertOrUpdate(notes);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
