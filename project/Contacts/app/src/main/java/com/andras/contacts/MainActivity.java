package com.andras.contacts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView contactsListView;
    private ArrayList<String> contactsList;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsListView = findViewById(R.id.contactsListView);
        contactsList = new ArrayList<>();

        // Check for contacts permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, read and display contacts
            readContacts();
            displayContacts();
        } else {
            // Request contacts permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        }

        readContacts();
        displayContacts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, read and display contacts
                readContacts();
                displayContacts();
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "Read contacts permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readContacts() {
        String[] projection = {ContactsContract.Contacts.DISPLAY_NAME};

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contactsList.add(contactName);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    private void displayContacts() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, contactsList);

        contactsListView.setAdapter(adapter);
    }
}