package com.example.newsapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM newsEntity")
    LiveData<List<NewsEntity>> getAllNews();


    @Insert
    void insertAll(NewsEntity... users);

    @Query("DELETE FROM newsEntity")
     void deleteAll();
}
