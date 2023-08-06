package com.andras.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GoogleApiService {
    @GET("/")
    Call<String> getContent();
}