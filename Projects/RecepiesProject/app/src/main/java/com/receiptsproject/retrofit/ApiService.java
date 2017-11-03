package com.receiptsproject.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    @POST("/urlshortener/v1/url")
    Call<ShortenerResponse> registerUser(@Body ShortenerBody body, @Query("key") String apiKey);
}
