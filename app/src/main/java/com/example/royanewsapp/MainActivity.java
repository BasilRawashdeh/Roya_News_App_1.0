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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.royanewsapp.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    NewsViewModel newsViewModel;

    private RequestQueue mQueue;
    public static final String API_URL = "https://beta.royanews.tv/api/section/get/1/info/";

    ActivityMainBinding activityMainBinding;

    SwipeRefreshLayout swipeRefreshLayout;

    public String title = "أبرز ألعناوين !!";

    int pageNum = 10;

    public static Context mainActivityContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivityContext = getApplicationContext();

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.setMainActivityBinding(title);

        // Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
        mQueue = Volley.newRequestQueue(this);

        //requestData();

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
                //requestData();
            }
        });
    }

    private void jsonParse() {
        swipeRefreshLayout.setRefreshing(true);
        pageNum++;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL + pageNum, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("section_info");
                            Log.i("Volly onResponse","Json Array Response :-" + jsonArray);

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
                            swipeRefreshLayout.setRefreshing(false);
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

    private void requestData() {
        Call<JSONObject> call;
        call = ApiClient.getInstance().getApi().getInfo(1);

        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, @NotNull retrofit2.Response<JSONObject> response) {
                Log.i("MainActivity", "callBack Responded");
                if (response.isSuccessful() && response.body() != null) {

                    swipeRefreshLayout.setRefreshing(false);

                    try {



                        JSONArray pageNewsJSONArray = response.body().getJSONArray("section_info");
                        Log.i("MainActivity", "callBack Responded response body=" + pageNewsJSONArray.get(0).toString());



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
/*
                    RoyaApiResponse royaApiResponse = (JSONObject)response.body();
                    Log.i("MainActivity", "callBack Responded response body=" + royaApiResponse.news_title);
                    Toast.makeText(getApplicationContext(), "Retrofit-Response : " + response.body().toString().toString(), Toast.LENGTH_SHORT).show();

 */
                }
                else
                    Toast.makeText(getApplicationContext(), "else Response : " + response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.i("MainActivity", "callBack failed to Respond");
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}