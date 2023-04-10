package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPI {
    public static HotelsRestAPI getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.4.131:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HotelsRestAPI api = retrofit.create(HotelsRestAPI.class);

        return api;
    }
}
