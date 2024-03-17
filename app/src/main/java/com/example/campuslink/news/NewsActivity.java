package com.example.campuslink.news;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.example.campuslink.InitAll;
import com.example.campuslink.R;

public class NewsActivity extends AppCompatActivity implements InitAll {

    private ImageButton ibtnReturn;
    private WebView webView;

    private String webUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        HideTitle();
        initView();
        initData();
        initClick();

        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //设置能够解析Javascript
        webSettings.setJavaScriptEnabled(true);
        //设置适应Html5 重点是这个设置
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl(webUrl);

    }

    @Override
    public void HideTitle() {
        ActionBar actionBar= getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    public void initView() {
        ibtnReturn = findViewById(R.id.news_ibtn_return);
        webView = findViewById(R.id.news_web_view);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        webUrl = intent.getStringExtra("webUrl");
    }

    @Override
    public void initClick() {
        ibtnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}