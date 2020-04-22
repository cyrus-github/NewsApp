package com.example.newsapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapp.R;

public class SplashActivity extends AppCompatActivity {
    Handler handler;
    ImageView imgTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        imgTitle = findViewById(R.id.imgTitle);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        imgTitle.setAnimation(fadeIn);


        handler = new Handler();

        final Runnable r = () -> {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        };

        handler.postDelayed(r, 1500);


    }
}
