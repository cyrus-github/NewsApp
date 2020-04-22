package com.example.newsapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsClient {

    public static Retrofit retrofit;
    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static final long REQUEST_OKHTTP_TIMEOUT = 10L;


    public static Retrofit getRetrofit(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(REQUEST_OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_OKHTTP_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        if(retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

        }

        return retrofit;
    }

}
