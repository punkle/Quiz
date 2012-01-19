package org.brianfletcher.question;

import android.content.res.Resources;

public class Fact {
	private String imageKey;
	private String description;
	
	public String getImageKey() {
		return imageKey;
	}
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Fact(String imageKey, String description) {
		super();
		this.imageKey = imageKey;
		this.description = description;
	}
	
	public int getImageResourceId(Resources resources, String packageName){
		return resources.getIdentifier(imageKey, "drawable", packageName);
	}
	
	@Override
	public boolean equals(Object o) {
		Fact incomingFact = (Fact)o;
		if (description == incomingFact.getDescription() && imageKey == incomingFact.getImageKey()){
			return true;
		}
		else return false;
	}
	
}
