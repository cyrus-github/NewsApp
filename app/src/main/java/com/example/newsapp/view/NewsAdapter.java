package com.example.newsapp.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.data.NewsEntity;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    List<NewsEntity> newsList;
    Context context;

    public NewsAdapter(List<NewsEntity> newsList, Context context) {
        this.newsList = newsList;
        this.context = context;
    }


    @NonNull
    @Override
    public NewsAdapter.NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        NewsHolder mh = new NewsHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsHolder holder, int position) {
        holder.tvTitle.setText(newsList.get(position).getTitle());
        holder.tvDescription.setText(newsList.get(position).getDescritption());
//        holder.tvReleaseDate.setText(movieList.get(position).getReleaseDate());
        Glide.with(context).load(newsList.get(position).getImageUrl()).into(holder.imgNews);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDescription, tvReleaseDate;
        ImageView imgNews;

        public NewsHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvTitle);
            tvDescription = v.findViewById(R.id.tvDescription);
//            tvReleaseDate = v.findViewById(R.id.tvReleaseDate);
            imgNews = v.findViewById(R.id.imgNews);
        }
    }


}


