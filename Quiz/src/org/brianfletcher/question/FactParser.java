package org.brianfletcher.question;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import android.content.res.Resources;

import com.csvreader.CsvReader;

public class FactParser {
	
	private static ArrayList<Fact> facts;
	private String questionsFile;
	private Resources resources;
	private String thePackage;
	
	public FactParser(Resources resources, String questionsFile,String thePackage){
		facts = null;
		this.questionsFile = questionsFile;
		this.resources = resources;
		this.thePackage = thePackage;
	}
	
	public ArrayList<Fact> getFacts(){
    	if (facts == null) {
    		facts = new ArrayList<Fact>();
	    	CsvReader reader;
	    	try {
	 		 
	 		 InputStream is = resources.openRawResource(resources.getIdentifier(questionsFile, "raw", thePackage));
	 		 Reader isreader = new InputStreamReader(is);
	
	 		reader = new CsvReader(isreader);
	 		
	 		while(reader.readRecord()){
	 			Fact newFact = new Fact(reader.get(0),reader.get(1));
	 			facts.add(newFact);
	 				 		}
	 		} catch (FileNotFoundException e) {
	 			e.printStackTrace();
	 		} catch (IOException e) {
	 			e.printStackTrace();
	 		}
    	}
 		return facts;
    }
}
