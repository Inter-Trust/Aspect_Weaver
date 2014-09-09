package uma.caosd.evoting.server.gui;

public aspect EVotingServerGUISwing {
	private EVotingServerWindow window;
	
	pointcut initializeServer(): call(public uma.caosd.evoting.server.EVotingServer.new(..));
	pointcut initServer(): execution(public void uma.caosd.evoting.server.EVotingServer.initServer());
	pointcut waitingRequest(): execution(private Object uma.caosd.evoting.server.EVotingServer.getClientRequest());
	pointcut voteAction(): execution(private void uma.caosd.evoting.server.EVotingServer.voteAction());
	pointcut countAction(): execution(private void uma.caosd.evoting.server.EVotingServer.countAction());
	pointcut actions(): voteAction() || countAction();
	
	pointcut waitingClients(): waitingRequest() && cflow(initServer());
	
	before(): initializeServer() {
		window = new EVotingServerWindow();
		window.showInformation(">>Initializing...");
	}
	
	after(): initializeServer() {
		window.showInformation(">>Server ready.");
	}
	
	before(): waitingClients() {
		window.showInformation(">>Waiting for clients...");
	}
	
	before(): voteAction() {
		window.showInformation(">>Client voting...");
	}
	
	before(): countAction() {
		window.showInformation(">>Client requesting information...");
	}
	
	after(): voteAction() {
		window.showInformation("Server>>Vote received.");
	}
}
