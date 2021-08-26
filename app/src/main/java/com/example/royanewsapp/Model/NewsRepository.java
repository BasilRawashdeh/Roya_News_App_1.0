package com.example.royanewsapp.Model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.royanewsapp.NewsModel;

import java.util.List;

/**
 * this class works as abstraction layer between Room and ViewModel, in order to increase modularity and reduce change-propagation.
**/

public class NewsRepository {

    private NewsDAO newsDAO;
    private LiveData<List<NewsModel>> allNewsLiveData;

    public NewsRepository(Application application) {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(application);
        newsDAO = newsDatabase.newsDAO();
        allNewsLiveData = newsDAO.getAllNews();
    }

    public void insertNews(NewsModel newsModel) {
        new InsertNewsAsyncTask(newsDAO).execute(newsModel);
    }

    public void updateNews(NewsModel newsModel) {
        new UpdateNewsAsyncTask(newsDAO).execute(newsModel);
    }

    public void deleteNews(NewsModel newsModel) {
        new DeleteNewsAsyncTask(newsDAO).execute(newsModel);
    }

    public void deleteAllNews() {
        new DeleteAllNewsAsyncTask(newsDAO).execute();
    }

    public LiveData<List<NewsModel>> getAllNewsLiveData() {

        return allNewsLiveData;
    }



    public static class InsertNewsAsyncTask extends AsyncTask<NewsModel, Void, Void> {

        private NewsDAO newsDAO;

        private InsertNewsAsyncTask(NewsDAO newsDAO) {
            this.newsDAO = newsDAO;
        }

        @Override
        protected Void doInBackground(NewsModel... newsModels) {
            newsDAO.insertNews(newsModels[0]);

            return null;
        }
    }

    public static class UpdateNewsAsyncTask extends AsyncTask<NewsModel, Void, Void> {

        private NewsDAO newsDAO;

        private UpdateNewsAsyncTask(NewsDAO newsDAO) {
            this.newsDAO = newsDAO;
        }

        @Override
        protected Void doInBackground(NewsModel... newsModels) {
            newsDAO.updateNews(newsModels[0]);

            return null;
        }
    }

    public static class DeleteNewsAsyncTask extends AsyncTask<NewsModel, Void, Void> {

        private NewsDAO newsDAO;

        private DeleteNewsAsyncTask(NewsDAO newsDAO) {
            this.newsDAO = newsDAO;
        }

        @Override
        protected Void doInBackground(NewsModel... newsModels) {
            newsDAO.deleteNews(newsModels[0]);

            return null;
        }
    }

    public static class DeleteAllNewsAsyncTask extends AsyncTask<Void, Void, Void> {

        private NewsDAO newsDAO;

        private DeleteAllNewsAsyncTask(NewsDAO newsDAO) {
            this.newsDAO = newsDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDAO.deleteAllNews();

            return null;
        }
    }

}
