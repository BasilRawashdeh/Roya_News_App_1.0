package com.example.royanewsapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.royanewsapp.Model.NewsRepository;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;
    private LiveData<List<NewsModel>> allNewsLiveData;
    public NewsViewModel(@NonNull @NotNull Application application) {
        super(application);

        newsRepository = new NewsRepository(application);
        allNewsLiveData = newsRepository.getAllNewsLiveData();

    }

    public void insertNews(NewsModel newsModel) {
        newsRepository.insertNews(newsModel);
    }

    public void updateNews(NewsModel newsModel) {
        newsRepository.updateNews(newsModel);
    }

    public void deleteNews(NewsModel newsModel) {
        newsRepository.deleteNews(newsModel);
    }

    public void deleteAllNews() {
        newsRepository.deleteAllNews();
    }

    public LiveData<List<NewsModel>> getAllNewsLiveData() {
        return allNewsLiveData;
    }

}
