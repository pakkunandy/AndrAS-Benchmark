package com.andras.realmdatabase;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmResults;

public class DisplayActivity extends AppCompatActivity {

    private TextView notesTextView;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        // Initialize views
        notesTextView = findViewById(R.id.notes_text_view);

        // Initialize Realm
        realm = Realm.getDefaultInstance();

        RealmResults<Note> books;
        realm.executeTransaction(new Realm.Transaction() {
         @Override
         public void execute(Realm realm) {
             RealmResults<Note> books = realm.where(Note.class).findAll();
             StringBuilder sb = new StringBuilder();
             for (Note book : books) {
                 sb.append("Title: ").append(book.getTitle()).append("\n");
                 sb.append("Author: ").append(book.getContent()).append("\n\n");
             }

             notesTextView.setText(sb.toString());
         }
     });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}