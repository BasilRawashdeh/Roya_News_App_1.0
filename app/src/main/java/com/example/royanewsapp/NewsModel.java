package com.example.royanewsapp;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.squareup.picasso.Picasso;

@Entity(tableName = "news_table")
public class NewsModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "news_id")
    public int newsID;

    @ColumnInfo(name = "news_image")
    public String newsImage;

    @ColumnInfo(name = "news_title")
    public String newsTitle;

    @ColumnInfo(name = "news_section_name")
    public String newsSectionName;

    @ColumnInfo(name = "news_created_date")
    public String newsCreatedDate;

    @ColumnInfo(name = "news_link")
    public String newsLink;

    @ColumnInfo(name = "news_description")
    public String newsDescription;


    //------------------------------

    public NewsModel(String newsImage, String newsTitle, String newsSectionName, String newsCreatedDate, String newsLink, String newsDescription) {
        this.newsImage = newsImage;
        this.newsTitle = newsTitle;
        this.newsSectionName = newsSectionName;
        this.newsCreatedDate = newsCreatedDate;
        this.newsLink = newsLink;
        this.newsDescription = newsDescription;
    }

    public String getNewsImage() {
        return newsImage;
    }

    public String getNewsCreatedDate() {
        return newsCreatedDate;
    }

    public void setNewsCreatedDate(String newsCreatedDate) {
        this.newsCreatedDate = newsCreatedDate;
    }

    public String getNewsLink() {
        return newsLink;
    }

    public void setNewsLink(String newsLink) {
        this.newsLink = newsLink;
    }

    public String getNewsDescription() {
        return newsDescription;
    }

    public void setNewsDescription(String newsDescription) {
        this.newsDescription = newsDescription;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsImageLink() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsSectionName() {
        return newsSectionName;
    }

    public void setNewsSectionName(String newsSectionName) {
        this.newsSectionName = newsSectionName;
    }

    /*@BindingAdapter("android:loadImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);
    }

     */
}
