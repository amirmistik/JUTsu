package com.amirmistikplay.jutsu;

import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class WebViewClientOptions extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return false;
    }

    private WebResourceResponse getTextWebResource(InputStream data) {
        return new WebResourceResponse("text/plain", "UTF-8", data);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view,String url) {
        if(url.contains("google")||url.contains("facebook")){
            InputStream textStream = new ByteArrayInputStream("".getBytes());
            return getTextWebResource(textStream);
        }
        return super.shouldInterceptRequest(view, url);
    }
}
