package org.brianfletcher.question;

import java.util.ArrayList;

public class Question {
	private Fact[] facts;
	private int answerFactId;
	
	public Question(FactParser factParser ){
		facts = new Fact[3];
		
		do {
			ArrayList<Fact> allFacts = new ArrayList<Fact>(factParser.getFacts());
			for(int i = 0; i < facts.length; i++ ){
				int selectedFact = (int) (Math.random() * (allFacts.size()));
				facts[i] = allFacts.remove(selectedFact);
			}
			answerFactId =  (int) (Math.random() * 3);
		} while ( testDuplicateFacts() );
	}
	
	public boolean testDuplicateFacts(){
		if (facts[0].getDescription().equals(facts[1].getDescription())){
			return true;
		} else if (facts[0].getDescription().equals(facts[2].getDescription())){
			return true;
		} else if (facts[1].getDescription().equals(facts[2].getDescription())){
			return true;
		}
		return false;
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
