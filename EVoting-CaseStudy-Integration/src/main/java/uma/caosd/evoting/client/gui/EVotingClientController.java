package uma.caosd.evoting.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uma.caosd.evoting.User;
import uma.caosd.evoting.Vote;
import uma.caosd.evoting.client.EVotingClient;
import uma.caosd.evoting.impl.SimpleUser;
import uma.caosd.evoting.impl.SimpleVote;
import uma.caosd.evoting.impl.UserVote;

public class EVotingClientController implements ActionListener {
	private EVotingClient modelClient;
	private EVotingClientWindow viewClient;
	
	public EVotingClientController(EVotingClient modelClient, EVotingClientWindow viewClient) {
		this.modelClient = modelClient;
		this.viewClient = viewClient;
	}
	
	public void actionPerformed(ActionEvent event) {
		String command = event.getActionCommand();
		if (command.equals(EVotingClientPane.QUIT_ACTION)) {	
			quitAction();
		} else if (command.equals(EVotingClientPane.VOTE_ACTION)) {
			voteAction();
		} else if (command.equals(EVotingClientPane.COUNT_ACTION)) {
			countAction();
		}
	}
	
	private void quitAction() {
		System.exit(0);
	}
	
	private void voteAction() {
		String choice = viewClient.getContentPane().getChoice();
		String userID = viewClient.getContentPane().getUser();
		
		Vote vote = null;
		if (choice != null && !choice.trim().equals("")) {
			if (userID == null || userID.trim().equals("")) {
				vote = new SimpleVote(choice);
			} else {
				User user = new SimpleUser(userID);
				vote = new UserVote(user, choice);
			}
			String response = modelClient.vote(vote);
			viewClient.showInformation(">>Server: " + response);
		}
	}
	
	private void countAction() {
		String choice = viewClient.getContentPane().getChoice();
		
		if (choice != null && !choice.trim().equals("")) {
			String response = modelClient.countVotes(choice);
			viewClient.showInformation(">>Server: " + response);
		}
	}
	
}
