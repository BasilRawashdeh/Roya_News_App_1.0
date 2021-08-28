package com.example.royanewsapp;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("info")
    Call<JSONObject> getInfo(
            @Query("page") int pageNum
    );
}
