package com.example.royanewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.royanewsapp.View.NewsAdapter;
import com.example.royanewsapp.ViewModel.NewsViewModel;
import com.example.royanewsapp.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NewsViewModel newsViewModel;

    RequestQueue mQueue;

    ActivityMainBinding activityMainBinding;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView newsRecyclerView;

    public String title = "أبرز ألعناوين !!";
    public static int pageNum = 1;
    public static final String API_URL = "https://beta.royanews.tv/api/section/get/1/info/";
    public static Context mainActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivityContext = getApplicationContext();

        //DataBinding
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.setMainActivityBinding(title);

        // initialize swipeRefreshLayout to update the view with newest news.
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        newsRecyclerView = findViewById(R.id.newsRecyclerView);

        // Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
        mQueue = Volley.newRequestQueue(mainActivityContext);

        LinearLayoutManager newsLayoutManager = new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(newsLayoutManager);
        newsRecyclerView.setHasFixedSize(true);

        final NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this);
        newsRecyclerView.setAdapter(newsAdapter);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        newsViewModel.loadFirstPage();

        //Observer on the liveData to notify onChange.
        newsViewModel.getAllNewsLiveData().observe(this, new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {
                if(newsModels.size() > 0)
                    newsModels.remove(0);
                newsAdapter.setNews(newsModels);
            }
        });

        //listeners on recycler view and refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 1000ms
                        newsRecyclerView.scrollToPosition(0);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 500);
                //fetchJson();
            }
        });

        newsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) { //check for scroll down

                    int pastVisiblesItems, visibleItemCount, totalItemCount;
                    visibleItemCount = newsLayoutManager.getChildCount();
                    totalItemCount = newsLayoutManager.getItemCount();
                    pastVisiblesItems = newsLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        swipeRefreshLayout.setRefreshing(true);
                        final Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 1000ms
                                newsViewModel.loadNextPage();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        }, 1500);
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        swipeRefreshLayout.setRefreshing(true);
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 1000ms
                newsRecyclerView.scrollToPosition(0);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 500);
    }
}