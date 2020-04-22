package com.example.newsapp.presenter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.newsapp.data.NewsDatabase;
import com.example.newsapp.data.NewsEntity;
import com.example.newsapp.model.Article;
import com.example.newsapp.model.NewsResponse;
import com.example.newsapp.network.NewsClient;
import com.example.newsapp.network.NewsService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsPresenter implements NewsPresenterInterface {
    MainViewInterface mvi;
    private String TAG = "MainPresenter";
    NewsDatabase database;
    Context context;
    LiveData<List<NewsEntity>> newsList;

    public NewsPresenter(MainViewInterface mvi, Context context) {
        this.mvi = mvi;
        this.context = context;
    }

    @Override
    public void getNews() {
        mvi.showProgress();
        database = NewsDatabase.getDatabaseInstance(context);
        NewsService apiService =
                NewsClient.getRetrofit().create(NewsService.class);

        Call<NewsResponse> call = apiService.getLatestNews("us", "business", "3402d72dc66c4323b105bc181f58ad52");
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                List<Article> news = response.body().getArticles();
                Log.d(TAG, "Number of movies received: " + news.toString());

                if (response.code() == 200) {
                    database.newsDao().deleteAll();

                    mvi.hideProgress();
                    Log.d(TAG, "Cyrus 200");

                    for (int i = 0; i < news.size(); i++) {
                        Log.d(TAG, "Cyrus Data" + news.get(i).getTitle());
                        NewsEntity newsEntity = new NewsEntity(news.get(i).getTitle(),news.get(i).getDescription(),news.get(i).getUrlToImage());
                        database.newsDao().insertAll(newsEntity);

                    }


                } else {
                    mvi.hideProgress();
                    Log.d(TAG, "Cyrus 400");

                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                mvi.hideProgress();
                mvi.displayError("Something is wrong!");
            }
        });
    }
    public LiveData<List<NewsEntity>> getAllNews() {
        database = NewsDatabase.getDatabaseInstance(context);
        return database.newsDao().getAllNews();
    }
}
