package org.brianfletcher;

import com.phonegap.*;
import android.os.Bundle;
import android.view.WindowManager;

public class AddWebView extends DroidGap {
	 
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    super.loadUrl(getString(R.string.add_url));
	    
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                WindowManager.LayoutParams.FLAG_FULLSCREEN | 
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
	}
}
