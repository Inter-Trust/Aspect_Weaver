package uma.caosd.evoting.impl;

import java.util.Date;

import uma.caosd.evoting.Vote;


public class SimpleVote implements Vote {
	private String choice;
	private Date timestamp;
	
	public SimpleVote() {
		this.timestamp = new Date();
	}
	
	public SimpleVote(String choice) {
		this.choice = choice;
		this.timestamp = new Date();
	}
	
	public void setChoice(String choice) {
		this.choice = choice;
		this.timestamp = new Date();
	}

	public String getChoice() {
		return choice;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
}
