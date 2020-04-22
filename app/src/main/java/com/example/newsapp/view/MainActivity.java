package com.example.newsapp.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsapp.R;
import com.example.newsapp.data.NewsDatabase;
import com.example.newsapp.data.NewsEntity;
import com.example.newsapp.presenter.MainViewInterface;
import com.example.newsapp.presenter.NewsPresenter;
import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainViewInterface {

    ImageView imgReload;
    RecyclerView rvNews;
    NewsPresenter newsPresenter;
    RecyclerView.Adapter adapter;
    ProgressBar pieProgress;
    private String TAG = "MainActivity";
    private SwipeRefreshLayout swipeContainer;
    NewsDatabase database;
    List<NewsEntity> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);
        database = NewsDatabase.getDatabaseInstance(this);
        setUpViews();
    }

    public void setUpViews() {
        imgReload = findViewById(R.id.imgReload);
        rvNews = findViewById(R.id.rvNews);
        rvNews = findViewById(R.id.rvNews);
        pieProgress = findViewById(R.id.pieProgress);
        swipeContainer = findViewById(R.id.swipeContainer);

        rvNews.setLayoutManager(new LinearLayoutManager(this));

        newsPresenter = new NewsPresenter(this, this);
        getNews();

        swipeContainer.setOnRefreshListener(() -> newsPresenter.getNews());
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        Animation animation = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setRepeatCount(-1);
        animation.setDuration(2000);

        imgReload.setOnClickListener(v -> {
            imgReload.setAnimation(animation);
            newsPresenter.getNews();
        });

        newsPresenter.getAllNews().observe(this, newsEntities -> {
            newsList.clear();
            newsList.addAll(newsEntities);
            adapter = new NewsAdapter(newsList, MainActivity.this);
            rvNews.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        });
    }

    public void getNews() {
        newsPresenter.getNews();
    }

    @Override
    public void showToast(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }


    @Override
    public void displayError(String s) {
        showToast(s);
    }

    @Override
    public void showProgress() {
        pieProgress.setVisibility(View.VISIBLE);
        rvNews.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        imgReload.setAnimation(null);
        swipeContainer.setRefreshing(false);
        pieProgress.setVisibility(View.GONE);
        rvNews.setVisibility(View.VISIBLE);
    }
}
