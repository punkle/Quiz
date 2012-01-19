package org.brianfletcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity {
	Dialog infoDialog;
	Dialog helpDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		Button startButton = (Button)findViewById(R.id.start);
		//startButton.getBackground().setColorFilter(new LightingColorFilter(0xEE99FFCC, 0));
		//Button playGame1Button = (Button)findViewById(R.id.startpictotext);
		//Button playGame2Button = (Button)findViewById(R.id.starttexttopic);
		Button infoButton = (Button)findViewById(R.id.info);
		//infoButton.getBackground().setColorFilter(new LightingColorFilter(0xFF99FFCC, 0));
		
		Button helpButton = (Button)findViewById(R.id.help);
		//helpButton.getBackground().setColorFilter(new LightingColorFilter(0xFF99FFCC, 0));
		//Button exitButton = (Button)findViewById(R.id.help);
		startButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Quiz quiz = (Quiz) getApplicationContext();
				quiz.launchNextLevel();
			}
		});
		
		/*playGame1Button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this,SelectPictureQuizActivity.class);
		    	startActivity(intent);
			}
		});
		
		playGame2Button.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this,SelectTextQuizActivity.class);
		    	startActivity(intent);
			}
		});*/
		
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
		// when activity goes into the background then finish the activity
		//finish();
		if(infoDialog != null){
			infoDialog.dismiss();
		}
		if(helpDialog != null){
			helpDialog.dismiss();
		}
	}
	
	public void onAddClickListener(View target){
		//Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.add_url)));
		Intent browserIntent = new Intent(this,AddWebView.class);
		startActivity(browserIntent);
	}
}
