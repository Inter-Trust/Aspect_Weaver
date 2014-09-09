package uma.caosd.evoting.server;

import uma.caosd.evoting.Ballot;
import uma.caosd.evoting.Vote;
import uma.caosd.evoting.impl.SimpleBallot;
import uma.caosd.evoting.impl.UserVote;
import uma.caosd.evoting.util.ServerSocketConnection;

public class EVotingServer {
	public static final int SERVER_PORT = 4444; 
	public static final String VOTE_ACTION = "VOTE";
	public static final String COUNT_ACTION = "COUNT";
	
	private ServerSocketConnection socket;
	private int serverPort;
	private Ballot ballot;
	
	public EVotingServer() {
		this.serverPort = SERVER_PORT;
		ballot = new SimpleBallot();
	}
	
	public EVotingServer(int serverPort) {
		this.serverPort = serverPort;
	}
	
	public void initServer() {
		socket = new ServerSocketConnection(serverPort);
		while (true) {
			socket.acceptClient();
			Object action = (String) socket.receiveObject();
			if (action.equals(VOTE_ACTION)) {
				voteAction();
			} else if (action.equals(COUNT_ACTION)) {
				countAction();
			}
			socket.leaveClient();
		}
	}
	
	private void voteAction() {
		Vote vote = (Vote) socket.receiveObject();
		ballot.vote(vote);
		String response = "Vote for '" + vote.getChoice() + "' at "  + vote.getTimestamp();
		if (vote instanceof UserVote) {
			response += " sent by the user " + ((UserVote)vote).getUser().getID();
		}
		socket.sendObject(response);
	}
	
	private void countAction() {
		String choice = (String) socket.receiveObject();
		int result = ballot.count(choice);
		String response = "The total votes for '" + choice + "' is:" + result;
		socket.sendObject(response);
	}
}