package com.example.royanewsapp;

class NewsSection {
    public String sectionName;
    public boolean showInHomePage;
    public boolean showInApp;
    public int order;
    public Object iframe;
    public int thumbsImages;
    public String alias_ar;
    public String alias_en;
    public String ads_code;
    public String description;
    public Object delete_at;
}

public class RoyaApiResponse {
    public int id;
    public String news_title;
    public String created_at;
    public String image;
    public String section_id;
    public String section_name;
    public int news_id;
    public String created_age;
    public String main_image_path;
    public String createdDate;
    public String createdstamp;
    public String updatedstamp;
    public String news_section;
    public String news_link;
    public NewsSection newsSectionObj;
}