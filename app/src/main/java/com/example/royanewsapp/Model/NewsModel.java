package com.example.royanewsapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

    //------------------------------
    public NewsModel(String newsImage, String newsTitle, String newsSectionName) {
        this.newsImage = newsImage;
        this.newsTitle = newsTitle;
        this.newsSectionName = newsSectionName;
    }

    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getNewsImage() {
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
}
