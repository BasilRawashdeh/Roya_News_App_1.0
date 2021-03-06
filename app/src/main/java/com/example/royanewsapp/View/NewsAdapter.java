package com.example.royanewsapp.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.royanewsapp.NewsModel;
import com.example.royanewsapp.NewsDetailsActivity;
import com.example.royanewsapp.R;
import com.example.royanewsapp.databinding.NewsItemBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends PagedListAdapter<NewsModel,NewsAdapter.NewsViewHolder> {

    Context context;
    List<NewsModel> newsModels = new ArrayList<>();


    public NewsAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding newsItemBinding = NewsItemBinding.inflate(layoutInflater, parent,false);
        return new NewsViewHolder(newsItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder newsViewHolder, int position) {

        final NewsModel newsModel = newsModels.get(position);

        //bind newsModel to the variable "newsListItem" in the news_item.xml.
        newsViewHolder.newsItemBinding.setNewsListItem(newsModel);

        Picasso.with(context).load(newsModel.getNewsImageLink()).into(newsViewHolder.newsImageView);
        newsViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("news_title",newsModel.getNewsTitle());
                intent.putExtra("imageLink",newsModel.getNewsImageLink());
                intent.putExtra("createdDate", newsModel.getNewsCreatedDate());
                intent.putExtra("section_name",newsModel.getNewsSectionName());
                intent.putExtra("description",newsModel.getNewsDescription());
                intent.putExtra("news_link",newsModel.getNewsLink());
                context.startActivity(intent);
            }
        });

    }

    //DiffUtil is a utility class that calculates the difference between two lists and outputs a list of update operations that converts the first list into the second one.
    private static DiffUtil.ItemCallback<NewsModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NewsModel>() {
                @Override
                public boolean areItemsTheSame(NewsModel oldItem, NewsModel newItem) {
                    return oldItem.newsID == newItem.newsID;
                }

                @Override
                public boolean areContentsTheSame(NewsModel oldItem, NewsModel newItem) {
                    return oldItem.newsTitle.equals(newItem.newsTitle);
                }
            };

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public void setNews(List<NewsModel> newsModels) {
        this.newsModels = newsModels;
        notifyDataSetChanged();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView newsImageView;
        CardView cardView;

        NewsItemBinding newsItemBinding;

        public NewsViewHolder(@NonNull NewsItemBinding newsItemBinding) {
            super(newsItemBinding.getRoot());

            this.newsItemBinding = newsItemBinding;
            newsImageView = itemView.findViewById(R.id.ivNewsImage);
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

    /*public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
     */
}