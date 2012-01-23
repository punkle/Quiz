package org.brianfletcher;

import org.brianfletcher.question.Fact;
import org.brianfletcher.question.FactParser;
import org.brianfletcher.question.Question;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectTextQuizActivity extends Activity {
	private int selectedAnswer;
	private int questionNumber = 1;
	private int correctAnswerCount;
	private ImageView hintImage;
	private Button[] possibleAnswersButtons;
	private Question question;
	private TextView scoreTextView ;
	private TextView timerTextView ;
	private AsyncTask<Integer,Integer, Boolean> timertask;
	private int numberOfQuestions;
	private FactParser factParser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecttextlayout);
		createDialog();
		hintImage = (ImageView)findViewById(R.id.hintImage);
		possibleAnswersButtons = new Button[3];
		//final Button selectButton = (Button)findViewById(R.id.select_button);
		scoreTextView = (TextView) findViewById(R.id.score_text_view_level2);
		
		possibleAnswersButtons[0] = (Button)findViewById(R.id.answer1_button);
		possibleAnswersButtons[1] = (Button)findViewById(R.id.answer2_button);
		possibleAnswersButtons[2] = (Button)findViewById(R.id.answer3_button);
		
		Intent intent = getIntent();
		String quizQuestionsFile = intent.getStringExtra("quizQuestionsFile");
		
		Resources resources = getResources();
       	
		factParser = new FactParser(resources,quizQuestionsFile,getPackageName());
       	
       	numberOfQuestions = intent.getIntExtra("numberOfQuestions",-1);
       	
		timerTextView = (TextView) findViewById(R.id.timer2);
        timertask = new TimerTask();
        
        
        for (int i = 0; i < possibleAnswersButtons.length; i++){
			final int buttonId = i;
			//possibleAnswersButtons[buttonId].setBackgroundColor(Color.GRAY);
			possibleAnswersButtons[buttonId].setOnClickListener(new View.OnClickListener() {

				public void onClick(View arg0) {
					
					possibleAnswersButtons[buttonId].setPressed(true);
					possibleAnswersButtons[(buttonId + 1) % 3].setPressed(false);
					possibleAnswersButtons[(buttonId + 2) % 3].setPressed(false);
					selectedAnswer = buttonId;
					selectAnswer(selectedAnswer);
				}
			});
		}
		refreshGUI();
		/*selectButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				selectAnswer(selectedAnswer);

			}
		}); */

		
	}
	
	protected void createDialog(){
		int numberOfSeconds = getIntent().getIntExtra("numberOfSeconds",-1);
		int numberOfQuestions = getIntent().getIntExtra("numberOfQuestions",-1);
		String instruction_text = getIntent().getStringExtra("instruction_text");
		
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("You will have " + numberOfSeconds + " seconds to answer " + numberOfQuestions + " questions." + instruction_text)
		       .setCancelable(false)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   timertask.execute(getIntent().getIntExtra("numberOfSeconds",-1));           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public void selectAnswer(int selectedAnswer){
			if (selectedAnswer == question.getAnswerFactId()){
				correctAnswerCount ++;
			}
			if (questionNumber == numberOfQuestions){
				nextLevelOrGameOver();
			} else {
				questionNumber ++;
				refreshGUI();
			}
	}
	
	public void refreshGUI(){
		scoreTextView.setText("Q " + questionNumber +"\n" + correctAnswerCount + " / " + numberOfQuestions);
		
		selectedAnswer = -1;
		question = new Question(factParser);
		
		Fact[] facts = question.getFacts();
		hintImage.setImageResource(question.getCorrectAnswer().getImageResourceId(getResources(), getPackageName()));
		
		for (int i = 0; i < facts.length; i++){
			final int buttonId = i;
			possibleAnswersButtons[buttonId].setText(facts[buttonId].getDescription());			
		}
		
		for (int i = 0; i < possibleAnswersButtons.length; i++){
			final int buttonId = i;
			possibleAnswersButtons[buttonId].setPressed(false);
		}
	}
	
	public void showResult() {
    	Intent intent = new Intent(SelectTextQuizActivity.this,ResultsActivity.class);
    	
    	Bundle bundle = new Bundle();
    	bundle.putInt("correctAnswerCount", correctAnswerCount);
    	bundle.putString("levelName", "Picture to Text");
    	bundle.putInt("numberOfQuestions", numberOfQuestions);
    	
    	intent.putExtras(bundle);
    	startActivity(intent);
    	try {
			this.finalize();
		} catch (Throwable e) {
			
			e.printStackTrace();
		}
    	finish();
	}
	
	public class TimerTask extends AsyncTask<Integer, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(Integer... params) {
			int numberOfSeconds = params[0];
			while (numberOfSeconds >= 0){
				publishProgress(numberOfSeconds);
				numberOfSeconds --;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			timerTextView.setText(Integer.toString(values[0]));
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			nextLevelOrGameOver();
		}
	}
	
	 public void nextLevelOrGameOver(){
	    	Quiz quiz = (Quiz) getApplicationContext();
	    	Bundle levelResult = new Bundle();
	    	levelResult.putInt("numberOfQuestions", numberOfQuestions);
	    	levelResult.putInt("correctAnswerCount", correctAnswerCount);
	    	
	    	quiz.putLevelResults(levelResult);
	    	
	    	try {
				quiz.incrementLevel();
				quiz.launchNextLevel();
			} catch (GameOverException e) {
				quiz.launchGameOver();
			}	
	    }
	
	@Override
	protected void onPause() {
		super.onPause();
		timertask.cancel(true);
		finish();
	}
}

