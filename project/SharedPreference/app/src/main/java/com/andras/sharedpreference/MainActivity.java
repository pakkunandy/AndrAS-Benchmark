package com.andras.sharedpreference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputText;
    private TextView savedText;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputText = findViewById(R.id.input_text);
        savedText = findViewById(R.id.saved_text);
        Button saveButton = findViewById(R.id.save_button);
        Button retrieveButton = findViewById(R.id.retrieve_button);

        // Get default SharedPreferences instance
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = inputText.getText().toString();
                saveText(text);
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String savedTextValue = getText();
                savedText.setText(savedTextValue);
            }
        });
    }

    private void saveText(String text) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sample_text", text);
        editor.apply();
    }

    private String getText() {
        return sharedPreferences.getString("sample_text", "");
    }
}