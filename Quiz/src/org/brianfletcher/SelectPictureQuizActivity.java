package org.brianfletcher;

import org.brianfletcher.question.FactParser;
import org.brianfletcher.question.Question;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SelectPictureQuizActivity extends Activity {
	protected Question question;
	
	private ImageButton button[] = new ImageButton[3];
	private Button selectButton;
	protected TextView timerTextView;
	
	protected int questionNo;
	protected int selectedAnswer;
	protected int correctAnswerNo;
	protected int correctAnswerCount;
	protected int numberOfQuestions;
	protected Bundle levelParameters;
	
	protected FactParser factParser;
	
	private AsyncTask<Integer,Integer, Boolean> timertask;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // set up GUI layout
        setContentView(R.layout.selectpicturelayout);
        setupGUI();
        createDialog();
        // set variables
       	questionNo = 0;
       	correctAnswerCount = 0;
       	levelParameters = getIntent().getExtras();
       	Resources resources = getResources();
       	factParser = new FactParser(resources,levelParameters.getString("quizQuestionsFile"),getPackageName());
       	
       	numberOfQuestions = levelParameters.getInt("numberOfQuestions");
       	
        timertask = new TimerTask();
         	
        refreshQuestionGui();
    }
    
    public void selectAnswer(int number){
    	if (number != -1){
			if (number == correctAnswerNo){
				correctAnswerCount ++;
			}
			if (questionNo != numberOfQuestions){
				refreshQuestionGui();
			} else{
				nextLevelOrGameOver();
			}
		}
    }
    
    public void setupGUI(){
    	
    	// get handles to buttons
        button[0] = (ImageButton)findViewById(R.id.imageButton1);
        button[1] = (ImageButton)findViewById(R.id.imageButton2);
        button[2] = (ImageButton)findViewById(R.id.imageButton3);
        //selectButton = (Button) findViewById(R.id.select_button);
        
        timerTextView = (TextView) findViewById(R.id.timer);
        
        // set up the on click events of the buttons
        for (int i = 0; i < button.length; i++){
        	final int buttonNo = i;
        	button[i].setOnClickListener(new View.OnClickListener() {
     			
     			public void onClick(View v) {
     				button[buttonNo].setBackgroundColor(Color.BLUE);
     				button[(buttonNo + 1) % 3 ].setBackgroundColor(R.color.background);
     				button[(buttonNo + 2) % 3].setBackgroundColor(R.color.background);
     				selectedAnswer = buttonNo;
     				selectAnswer(selectedAnswer);
     			}
     		});
        }
       
    	
    	/*selectButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				selectAnswer(selectedAnswer);
			}
		});*/
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
    
    public void showResult() {
    	Intent intent = new Intent(SelectPictureQuizActivity.this,ResultsActivity.class);
    	
    	Bundle bundle = new Bundle();
    	bundle.putInt("correctAnswerCount", correctAnswerCount);
    	bundle.putString("levelName", "Text to Picture");
    	bundle.putInt("numberOfQuestions", numberOfQuestions);
    	
    	intent.putExtras(bundle);
    	startActivity(intent);
    	finish();

	}

	public void refreshQuestionGui(){
    	button[0].setBackgroundColor(R.color.background);
		button[1].setBackgroundColor(R.color.background);
		button[2].setBackgroundColor(R.color.background);
		
		questionNo += 1;

    	selectedAnswer = -1;
    	correctAnswerNo = -1;
    	TextView scoreTextView = (TextView)findViewById(R.id.score_text_view_level1);
    	
    	scoreTextView.setText("Q " + questionNo +"\n" + correctAnswerCount + " / " + numberOfQuestions);
    	
    	
    	question = new Question(factParser);
    	
    	for (int i = 0; i<button.length;i++){
    		button[i].setImageResource(question.getFacts()[i].getImageResourceId(getResources(), getPackageName()));
    	}  	
    	
        correctAnswerNo = question.getAnswerFactId();
        
        TextView hintTextView = (TextView)findViewById(R.id.hint_text_view);
        hintTextView.setText(question.getCorrectAnswer().getDescription());
        
    }
	
	@Override
	protected void onPause() {
		super.onPause();
		timertask.cancel(true);
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
	
}