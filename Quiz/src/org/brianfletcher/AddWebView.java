package org.brianfletcher;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class AddWebView extends Activity {
	private WebView mWebView;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.addwebview);

	    mWebView = (WebView) findViewById(R.id.webview);
	    mWebView.getSettings().setJavaScriptEnabled(true);
	    mWebView.loadUrl(getString(R.string.add_url));
	}
}
