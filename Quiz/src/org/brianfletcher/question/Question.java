package org.brianfletcher.question;

import java.util.ArrayList;

public class Question {
	private Fact[] facts;
	private int answerFactId;
	
	public Question(FactParser factParser ){
		facts = new Fact[3];
		ArrayList<Fact> allFacts = new ArrayList<Fact>(factParser.getFacts());
		for(int i = 0; i < facts.length; i++ ){
			int selectedFact = (int) (Math.random() * (allFacts.size()));
			facts[i] = allFacts.remove(selectedFact);
		}
		
		answerFactId =  (int) (Math.random() * 3);
	}
	
	public Fact[] getFacts(){
		return facts;
	}
	
	public Fact getCorrectAnswer(){
		return facts[getAnswerFactId()];
	}
	
	public int getAnswerFactId(){
		return answerFactId;
	}
}
