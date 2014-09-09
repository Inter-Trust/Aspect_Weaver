package uma.caosd.evoting.client;

import uma.caosd.evoting.Vote;
import uma.caosd.evoting.server.EVotingServer;
import uma.caosd.evoting.util.ClientSocketConnection;

public class EVotingClient {
	public static final int SERVER_PORT = 4444;
	public static final String SERVER_HOST = null;
	
	private String serverHost;
	private int serverPort;
	private ClientSocketConnection socket;
	
	public EVotingClient() {
		this.serverHost = SERVER_HOST;
		this.serverPort = SERVER_PORT;
	}
	
	public EVotingClient(String serverHost, int serverPort) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}
	
	public String countVotes(String choice) {
		socket = new ClientSocketConnection(serverHost, serverPort);
		socket.sendObject(EVotingServer.COUNT_ACTION);
		socket.sendObject(choice);
		String response = (String) socket.receiveObject();
		socket.closeSocketConnection();
		return response;
	}	
	
	public String vote(Vote v) {
		socket = new ClientSocketConnection(serverHost, serverPort);
		socket.sendObject(EVotingServer.VOTE_ACTION);
		socket.sendObject(v);
		String response = (String) socket.receiveObject();
		socket.closeSocketConnection();
		return response;
	}	
}
