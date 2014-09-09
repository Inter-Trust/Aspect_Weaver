package uma.caosd.evoting.server.gui;

public aspect EVotingServerGUIConsole {
	
	pointcut initializeServer(): call(public uma.caosd.evoting.server.EVotingServer.new(..));
	pointcut initServer(): execution(public void uma.caosd.evoting.server.EVotingServer.initServer());
	pointcut waitingRequest(): execution(private Object uma.caosd.evoting.server.EVotingServer.getClientRequest());
	pointcut voteAction(): execution(private void uma.caosd.evoting.server.EVotingServer.voteAction());
	pointcut countAction(): execution(private void uma.caosd.evoting.server.EVotingServer.countAction());
	pointcut actions(): voteAction() || countAction();
	
	pointcut waitingClients(): waitingRequest() && cflow(initServer());
	
	before(): initializeServer() {
		System.out.println("Server>>Initializing...");
	}
	
	after(): initializeServer() {
		System.out.println("Server>>Server ready.");
	}
	
	before(): waitingClients() {
		System.out.println("Server>>Waiting for clients...");
	}
	
	before(): voteAction() {
		System.out.println("Server>>Client voting...");
	}
	
	before(): countAction() {
		System.out.println("Server>>Client requesting information...");
	}
	
	after(): voteAction() {
		System.out.println("Server>>Vote received.");
	}
}
