package com.example.royanewsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.royanewsapp.databinding.ActivityNewsDetailsBinding;
import com.example.royanewsapp.databinding.NewsItemBinding;
import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    ImageView ivNewsImage;
    WebView webView;
    ProgressBar progressBar;
    ActivityNewsDetailsBinding newsDetailsBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        newsDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_details);
        setContentView(newsDetailsBinding.getRoot());

        ivNewsImage = findViewById(R.id.imageView);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.webViewLoader);
        progressBar.setVisibility(View.VISIBLE);
        //-------------------
        Intent intent = getIntent();
        String newsTitle = intent.getStringExtra("news_title");
        String newsImageLink = intent.getStringExtra("imageLink");
        String newsCreatedDate = intent.getStringExtra("createdDate");
        String newsSectionName = intent.getStringExtra("section_name");
        String newsDescription = intent.getStringExtra("description");
        String newsLink = intent.getStringExtra("news_link");

        newsDetailsBinding.setNewsTitle(newsTitle);
        newsDetailsBinding.setNewsCreatedDate(newsCreatedDate);
        newsDetailsBinding.setNewsSectionName(newsSectionName);
        newsDetailsBinding.setNewsDescription(newsDescription);

        Picasso.with(NewsDetailsActivity.this).load(newsImageLink).into(ivNewsImage);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(newsLink);
        if (webView.isShown()){
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
