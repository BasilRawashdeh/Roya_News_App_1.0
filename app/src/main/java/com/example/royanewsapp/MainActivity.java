package com.example.royanewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.os.Bundle;
import android.widget.Toast;

import com.example.royanewsapp.Model.NewsModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NewsViewModel newsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getAllNewsLiveData().observe(this, new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {
                ///todo: update recyclerView
                Toast.makeText(getApplicationContext(), "MainActivity-onChanged", Toast.LENGTH_SHORT).show();
            }
        });

    }
}