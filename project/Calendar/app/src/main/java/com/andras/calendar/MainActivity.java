package com.andras.calendar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView eventsListView;
    private ArrayList<String> eventsList;
    private static final int PERMISSIONS_REQUEST_READ_CALENDAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventsListView = findViewById(R.id.eventsListView);
        eventsList = new ArrayList<>();

        // Check for calendar permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            // Permission is granted, read and display calendar events
            readCalendarEvents();
            displayEvents();
        } else {
            // Request calendar permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALENDAR}, PERMISSIONS_REQUEST_READ_CALENDAR);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CALENDAR) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted, read and display calendar events
                readCalendarEvents();
                displayEvents();
            } else {
                // Permission denied, inform the user
                Toast.makeText(this, "Read calendar permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readCalendarEvents() {
        String[] projection = {CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART};

        Cursor cursor = getContentResolver().query(CalendarContract.Events.CONTENT_URI,
                projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String eventTitle = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                @SuppressLint("Range") long eventStart = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                eventsList.add(eventTitle + " - " + formatEventTime(eventStart));
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    private String formatEventTime(long eventStart) {
        // Implement your own logic to format the event time as needed
        // For simplicity, this example uses a basic formatting. You can use SimpleDateFormat for more sophisticated formatting.
        return String.valueOf(eventStart);
    }

    private void displayEvents() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, eventsList);

        eventsListView.setAdapter(adapter);
    }
}