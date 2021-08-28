package com.example.royanewsapp.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.royanewsapp.NewsModel;
import com.example.royanewsapp.Model.NewsRepository;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.royanewsapp.MainActivity.API_URL;
import static com.example.royanewsapp.MainActivity.mainActivityContext;
import static com.example.royanewsapp.MainActivity.pageNum;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;
    private LiveData<List<NewsModel>> allNewsLiveData;
    RequestQueue mQueue;


    public NewsViewModel(@NonNull @NotNull Application application) {
        super(application);

        newsRepository = new NewsRepository(application);
        allNewsLiveData = newsRepository.getAllNewsLiveData();

        // Creates a default instance of the worker pool and calls {@link RequestQueue#start()} on it.
        mQueue = Volley.newRequestQueue(mainActivityContext);

    }

    public void loadFirstPage() {
        pageNum = 1;
        fetchJson();
    }

    public void loadNextPage() {
        pageNum++;
        fetchJson();
    }

    private void fetchJson() {
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

                                insertNews(new NewsModel(newsImageLink, newsTitle, newsSectionName, newsCreatedDate, newsLink, newsDescription));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        mQueue.add(request);
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
