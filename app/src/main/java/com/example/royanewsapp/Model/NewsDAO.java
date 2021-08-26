package com.example.royanewsapp.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.royanewsapp.NewsModel;

import java.util.List;

@Dao
public interface NewsDAO {
    @Query("SELECT * FROM news_table")
    LiveData<List<NewsModel>> getAllNews();

    @Query("SELECT * FROM news_table WHERE news_id IN (:newsIds)")
    List<NewsModel> getAllNewsByIds(int[] newsIds);

    @Query("SELECT * FROM news_table WHERE news_title LIKE :newsTitle")
    NewsModel getNewsByTitle(String newsTitle);

    @Query("DELETE FROM news_table")
    void deleteAllNews();

    @Insert
    void insertAll(NewsModel... newsModels);

    @Insert
    void insertNews(NewsModel newsModel);

    @Update
    void updateNews(NewsModel newsModel);

    @Delete
    void deleteNews(NewsModel newsModel);
}
