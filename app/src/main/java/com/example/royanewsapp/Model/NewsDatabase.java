package com.example.royanewsapp.Model;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.royanewsapp.NewsModel;

import org.jetbrains.annotations.NotNull;

@Database(entities = NewsModel.class, version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    public static NewsDatabase instance;

    public abstract NewsDAO newsDAO();

    public static synchronized NewsDatabase getInstance(Context context) {

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "news_database")
                    .addCallback(roomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull @NotNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private NewsDAO newsDAO;

        private PopulateDbAsyncTask(NewsDatabase newsDatabase) {
            this.newsDAO = newsDatabase.newsDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            ///todo: insert news into database

            return null;
        }
    }

}
