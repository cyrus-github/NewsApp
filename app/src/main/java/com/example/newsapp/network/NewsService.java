package com.example.newsapp.network;


import com.example.newsapp.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {

    @GET("top-headlines")
    Call<NewsResponse> getLatestNews(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);


}
