package com.eis;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;



	public class ProgramRules extends CustomTitle {

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.programrules);
		WebView wvRules=(WebView)findViewById(R.id.wvRules);
		wvRules.getSettings().setJavaScriptEnabled(true);
		wvRules.loadUrl("www.gmail.com");
		wvRules.setWebViewClient(new WebViewClient());
	}
	
	}
