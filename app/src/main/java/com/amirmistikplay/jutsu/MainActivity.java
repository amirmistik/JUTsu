package com.amirmistikplay.jutsu;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FloatingActionButton MainFloatingButton, FAQButton, HomeButton;

    boolean isAllFabsVisible;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        MainFloatingButton = findViewById(R.id.mainbutton);

        //mini buttons
        FAQButton = findViewById(R.id.faqbutton);
        HomeButton = findViewById(R.id.homebutton);

        HomeButton.setVisibility(View.GONE);
        FAQButton.setVisibility(View.GONE);

        WebView webView = (WebView) findViewById (R.id.webView);

        webView.setWebViewClient(new WebViewClientOptions());
        webView.setWebChromeClient(new ChromeClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUserAgentString(webSettings.getUserAgentString());
        WebView.setWebContentsDebuggingEnabled(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webView.loadUrl("https://jut.su/");

        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        MainFloatingButton.setOnClickListener(this::onClick);

        FAQButton.setOnClickListener(this::onClick);
        HomeButton.setOnClickListener(this::onClick);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        WebView webView = (WebView) findViewById (R.id.webView);
        switch (view.getId()){
            case R.id.mainbutton:
                if(!isAllFabsVisible){
                    HomeButton.show();
                    FAQButton.show();

                    isAllFabsVisible = true;
                } else {
                    HomeButton.hide();
                    FAQButton.hide();

                    isAllFabsVisible = false;
                }
                return;
            case R.id.faqbutton:
                webView.loadUrl("https://forum.jut.su/");
                return;
            case R.id.homebutton:
                webView.loadUrl("https://jut.su/");
        }
    }

    @Override
    public void onBackPressed(){
        WebView webView = (WebView) findViewById (R.id.webView);
        if(webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}