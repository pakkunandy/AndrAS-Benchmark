package com.andras.sqlite;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText contentEditText;
    private TextView notesTextView;

    private AppDatabase appDatabase;
    private NoteDao noteDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        titleEditText = findViewById(R.id.title_edit_text);
        contentEditText = findViewById(R.id.content_edit_text);
        notesTextView = findViewById(R.id.notes_text_view);
        Button saveButton = findViewById(R.id.save_button);
        Button retrieveButton = findViewById(R.id.retrieve_button);

        // Create database instance
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class, "notes-database")
                .allowMainThreadQueries()
                .build();

        // Get NoteDao instance
        noteDao = appDatabase.noteDao();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                Note note = new Note(title, content);
                noteDao.insert(note);
                updateNotesTextView();
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotesTextView();
            }
        });
    }

    private void updateNotesTextView() {
        List<Note> notes = noteDao.getAllNotes();
        StringBuilder stringBuilder = new StringBuilder();
        for (Note note : notes) {
            stringBuilder.append("Title: ").append(note.getTitle())
                    .append("\nContent: ").append(note.getContent())
                    .append("\n\n");
        }
        notesTextView.setText(stringBuilder.toString());
    }
}
