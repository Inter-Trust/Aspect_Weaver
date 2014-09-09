package uma.caosd.evoting.impl;

import java.util.Date;

import uma.caosd.evoting.User;
import uma.caosd.evoting.Vote;

public class UserVote implements Vote {
	private String choice;
	private Date timestamp;
	private User user;
	
	public UserVote() {
		this.timestamp = new Date();
	}
	
	public UserVote(User user, String choice) {
		this.user = user;
		this.choice = choice;
		this.timestamp = new Date();
	}
	
	public void setUser(User user) {
		this.user = user;
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

	public User getUser() {
		return user;
	}
	
}
