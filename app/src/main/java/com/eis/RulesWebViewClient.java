package com.eis;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class RulesWebViewClient extends WebViewClient {
	 @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
}
