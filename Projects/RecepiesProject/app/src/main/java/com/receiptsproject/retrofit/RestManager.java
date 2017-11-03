package com.receiptsproject.retrofit;


import android.support.annotation.NonNull;

import com.receiptsproject.util.Consts;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private static ApiService apiService;

    @NonNull
    public static ApiService getApiService(){
        //I know that double checked locking is not a good pattern, but it's enough here
        ApiService service = apiService;
        if (service == null){
            synchronized (RestManager.class){
                service = apiService;
                if (service == null){
                    service = apiService = createService();
                }
            }
        }
        return service;
    }

    @NonNull
    private static ApiService createService() {

        return new Retrofit.Builder()
                .baseUrl(Consts.GOOGLE_SHORTENER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }
}
