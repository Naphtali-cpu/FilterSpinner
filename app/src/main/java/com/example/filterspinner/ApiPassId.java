package com.example.filterspinner;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiPassId {

    String BASE_URL="http://167.172.46.142:1337/";
    @GET("admin-level-twos")
    Call <String> getState();

    @GET("admin-level-threes/{id}")
    Call <String> getDistrict(@Path("id")int id);

    @GET("getSubDistrict/{id}")
    Call <String> getSubDistrict(@Path("id")int id);
}
