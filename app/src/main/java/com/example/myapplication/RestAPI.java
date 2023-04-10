package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPI {
    public static HotelsRestAPI getClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.4.131:8080/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        HotelsRestAPI api = retrofit.create(HotelsRestAPI.class);

        return api;
    }
}
