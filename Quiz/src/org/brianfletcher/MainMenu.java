package org.brianfletcher;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainMenu extends Activity {
	Dialog infoDialog;
	Dialog helpDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		
		Button startButton = (Button)findViewById(R.id.start);

		Button infoButton = (Button)findViewById(R.id.info);
		
		Button helpButton = (Button)findViewById(R.id.help);
		
		Button shareButton = (Button)findViewById(R.id.share);
		
		startButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Quiz quiz = (Quiz) getApplicationContext();
				quiz.launchNextLevel();
			}
		});
		
		infoButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				infoDialog = createDialog(R.string.infoTitle,R.string.infoText);
			}
		});
		helpButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				helpDialog = createDialog(R.string.helpTitle,R.string.helpText);
			}
		});
		
		shareButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
				shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://market.android.com/details?id=" + getPackageName());

				startActivity(Intent.createChooser(shareIntent, "Share"));
			}
		});
	}
	
	
	
	protected Dialog createDialog(int title, int text){
		Dialog dialog = new Dialog(this);

		dialog.setContentView(R.layout.info);
		dialog.setTitle(title);

		TextView textView = (TextView) dialog.findViewById(R.id.text);
		textView.setText(text);
		dialog.show();
		return dialog;
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		if(infoDialog != null){
			infoDialog.dismiss();
		}
		if(helpDialog != null){
			helpDialog.dismiss();
		}
	}
	
	public void onAddClickListener(View target){
		Intent browserIntent = new Intent(this,AddWebView.class);
		startActivity(browserIntent);
	}
}
