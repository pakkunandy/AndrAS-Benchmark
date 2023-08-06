package com.andras.keystore;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView originalTextView;
    private TextView encryptedTextView;
    private TextView decryptedTextView;
    private Button encryptButton;
    private Button decryptButton;

    private KeyStoreHelper keyStoreHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        originalTextView = findViewById(R.id.original_text_view);
        encryptedTextView = findViewById(R.id.encrypted_text_view);
        decryptedTextView = findViewById(R.id.decrypted_text_view);
        encryptButton = findViewById(R.id.encrypt_button);
        decryptButton = findViewById(R.id.decrypt_button);

        try {
            keyStoreHelper = new KeyStoreHelper();
            keyStoreHelper.createKey();
        } catch (Exception e) {
            e.printStackTrace();
        }

        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String originalText = originalTextView.getText().toString();
                try {
                    byte[] encryptedData = keyStoreHelper.encryptData(originalText.getBytes());
                    String encryptedText = Base64.encodeToString(encryptedData, Base64.DEFAULT);
                    encryptedTextView.setText(encryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encryptedText = encryptedTextView.getText().toString();
                byte[] encryptedData = Base64.decode(encryptedText, Base64.DEFAULT);
                try {
                    byte[] decryptedData = keyStoreHelper.decryptData(encryptedData, new byte[16]);
                    String decryptedText = new String(decryptedData);
                    decryptedTextView.setText(decryptedText);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
