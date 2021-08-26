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

import com.squareup.picasso.Picasso;

public class NewsDetailsActivity extends AppCompatActivity {

    TextView tvNewsTitle, tvNewsSectionName, tvNewsDate, tvNewsDescription;
    ImageView ivNewsImage;
    WebView webView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        tvNewsTitle = findViewById(R.id.tvNewsTitle);
        tvNewsSectionName = findViewById(R.id.tvNewsSectionName);
        tvNewsDate = findViewById(R.id.tvNewsDate);
        tvNewsDescription = findViewById(R.id.tvDesc);
        ivNewsImage = findViewById(R.id.imageView);
        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.webViewLoader);
        progressBar.setVisibility(View.VISIBLE);
        //-------------------

        Intent intent = getIntent();
        String newsTitle = intent.getStringExtra("news_title");
        String newsImageLink = intent.getStringExtra("imageLink");
        String newsDate = intent.getStringExtra("createdDate");
        String source = intent.getStringExtra("section_name");
        String newsDesc = intent.getStringExtra("description");
        String newsLink = intent.getStringExtra("news_link");


        tvNewsTitle.setText(newsTitle);
        tvNewsSectionName.setText(source);
        tvNewsDate.setText(newsDate);
        tvNewsDescription.setText(newsDesc);

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
