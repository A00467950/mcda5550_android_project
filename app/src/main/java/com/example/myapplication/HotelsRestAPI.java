package com.example.myapplication;

import java.util.List;

import kotlin.ParameterName;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HotelsRestAPI {
    @GET("hotels")
    public Call<List<HotelListData>>  getHotels();

    @Headers({"Accept: application/json"})
    @POST("bookhotel")
    public Call<String> book(@Body BookingDetails hotel);
}
