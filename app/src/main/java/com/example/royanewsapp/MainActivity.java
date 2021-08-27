package com.example.royanewsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.royanewsapp.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NewsViewModel newsViewModel;

    private RequestQueue mQueue;
    public static final String API_URL = "https://beta.royanews.tv/api/section/get/1/info/1";

    ActivityMainBinding activityMainBinding;

    SwipeRefreshLayout swipeRefreshLayout;

    public String title = "أبرز ألعناوين !!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.setMainActivityBinding(title);

        // Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
        mQueue = Volley.newRequestQueue(this);

        // initialize swipeRefreshLayout to update the view with newest news.
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);

        RecyclerView newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsRecyclerView.setHasFixedSize(true);

        final NewsAdapter newsAdapter = new NewsAdapter(MainActivity.this);
        newsRecyclerView.setAdapter(newsAdapter);

        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.getAllNewsLiveData().observe(this, new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(List<NewsModel> newsModels) {
                newsAdapter.setNews(newsModels);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                jsonParse();
            }
        });
    }

    private void jsonParse() {

        swipeRefreshLayout.setRefreshing(true);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("section_info");
                            swipeRefreshLayout.setRefreshing(false);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject newsJSONObject = jsonArray.getJSONObject(i);

                                String newsTitle = newsJSONObject.getString("news_title");
                                String newsImageLink = newsJSONObject.getString("imageLink");
                                String newsCreatedDate = newsJSONObject.getString("createdDate");
                                String newsSectionName = newsJSONObject.getString("section_name");
                                String newsDescription = newsJSONObject.getJSONObject("section").getString("description");
                                String newsLink = newsJSONObject.getString("news_link");

                                Log.i("News Model" + i + ":=", newsTitle);
                                Log.i("News Model" + i + ":=", newsSectionName);
                                Log.i("News Model" + i + ":=", newsImageLink);
                                Log.i("News Model" + i + ":=", newsCreatedDate);
                                Log.i("News Model" + i + ":=", newsDescription);
                                Log.i("News Model" + i + ":=", newsLink);

                                newsViewModel.insertNews(new NewsModel(newsImageLink, newsTitle, newsSectionName, newsCreatedDate, newsLink, newsDescription));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swipeRefreshLayout.setRefreshing(false);
                        error.printStackTrace();
                    }
                });

        mQueue.add(request);
    }
}