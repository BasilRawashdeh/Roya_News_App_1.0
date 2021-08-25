package com.example.royanewsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.royanewsapp.Model.NewsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    Context context;
    List<NewsModel> newsModels = new ArrayList<>();


    public NewsAdapter() {
    }

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View newsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(newsView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder newsViewHolder, int position) {

        final NewsModel newsModel = newsModels.get(position);

        newsViewHolder.newsTitle.setText(newsModel.getNewsTitle());
        newsViewHolder.newsSectionName.setText(newsModel.getNewsSectionName());
        Picasso.with(context).load(newsModel.getNewsImageLink()).into(newsViewHolder.newsImageView);


        newsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("newsTitle",newsModel.getNewsTitle());
                intent.putExtra("newsImageUrl",newsModel.getNewsImageLink());
                //intent.putExtra("time",dateTime(newsModel.getPublishedAt()));
                //intent.putExtra("desc",newsModel.getDescription());
                //intent.putExtra("url",newsModel.getUrl());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public void setNews(List<NewsModel> newsModels) {
        this.newsModels = newsModels;
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView newsTitle, newsSectionName, newsDate;
        ImageView newsImageView;
        CardView cardView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.tvTitle);
            newsSectionName = itemView.findViewById(R.id.tvSource);
            newsDate = itemView.findViewById(R.id.tvDate);
            newsImageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.cardView);

        }
    }

/*
    public String dateTime(String t){
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
            Date date = simpleDateFormat.parse(t);
            time = prettyTime.format(date);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return time;

    }
*/
    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
