package com.andras.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class VolleyCallActivity extends AppCompatActivity {

    private TextView responseTextView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_call);
        context = this;

        responseTextView = (TextView) findViewById(R.id.responseTextView);
        String url = "https://www.google.com";
        VolleyHelper.performGetRequest(context, url, new VolleyHelper.RequestListener() {
            @Override
            public void onSuccess(String response) {
                responseTextView.setText(response);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(VolleyCallActivity.this, "Request failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}