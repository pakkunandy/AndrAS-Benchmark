
package com.andras.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitCallActivity extends AppCompatActivity {

    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_call);

        responseTextView = findViewById(R.id.responseTextView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.google.com")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        // Create an instance of the GoogleApiService interface
        GoogleApiService apiService = retrofit.create(GoogleApiService.class);

        // Call the getContent() endpoint
        Call<String> call = apiService.getContent();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Update the TextView with the fetched content
                    responseTextView.setText(response.body());
                } else {
                    responseTextView.setText("Error fetching content");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                responseTextView.setText("Error fetching content: " + t.getMessage());
            }
        });
    }
}