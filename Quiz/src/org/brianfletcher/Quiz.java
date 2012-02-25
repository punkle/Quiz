package org.brianfletcher;

import java.util.HashMap;

import android.app.Application;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;

public class Quiz extends Application {
	private Integer noOfLevels;
	private int currentLevel;
	private HashMap<Integer, Bundle> levelResults = new HashMap<Integer,Bundle>();
	
	public int getNoOfLevels(){
		return noOfLevels;
	}
	
	public int getCurrentLevel() {
		return currentLevel;
	}
	
	public Quiz(){
		currentLevel = 1;
	}
	
	public void resetQuiz(){
		currentLevel = 1;
		levelResults = new HashMap<Integer,Bundle>(); 
	}
	
	public void resetLevel(){
		currentLevel = 1;
	}
	
	public void putLevelResults(Bundle levelResult){
		levelResults.put(currentLevel, levelResult);
	}
	
	public HashMap<Integer, Bundle> getLevelResults(){
		return levelResults;
	}
	
	public void incrementLevel() throws GameOverException{
		if (noOfLevels == null) {
			Resources res = getResources();
			noOfLevels = res.getInteger(R.integer.no_of_levels);
		}
		if (noOfLevels == currentLevel){
			throw new GameOverException();
		} else {
			currentLevel ++;
		}
	}
	public void launchNextLevel(){
		try {
			TypedArray array = getResources().obtainTypedArray(getResources().getIdentifier("level"+currentLevel, "array",this.getPackageName()));
			String className = array.getString(0);
			Intent intent = new Intent(this,(Class.forName(className)));
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			Bundle levelParameters = new Bundle();
			levelParameters.putInt("numberOfQuestions", array.getInt(1, -1));
			levelParameters.putInt("numberOfSeconds", array.getInt(2, -1));
			String quizQuestionsFile = array.getString(3);
			levelParameters.putString("quizQuestionsFile", quizQuestionsFile);
			String instruction_text = array.getString(4);
			levelParameters.putString("instruction_text", instruction_text);
			String level_name = array.getString(5);
			levelParameters.putString("level_name", level_name);
		
			intent.putExtras(levelParameters);
			
			startActivity(intent);	
		} catch (ClassNotFoundException e) {	
			e.printStackTrace();
		}
	}

	public void launchGameOver() {
		Intent intent = new Intent(this,ResultsActivity.class);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}
