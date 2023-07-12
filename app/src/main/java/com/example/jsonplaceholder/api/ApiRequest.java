package com.example.jsonplaceholder.api;

import com.example.jsonplaceholder.model.Photo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {
    @GET("photos")
    Call<List<Photo>> getPhotos();
}
