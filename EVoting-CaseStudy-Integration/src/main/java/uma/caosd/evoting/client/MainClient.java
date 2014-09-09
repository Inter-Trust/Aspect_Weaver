package uma.caosd.evoting.client;

import uma.caosd.evoting.client.gui.EVotingClientController;
import uma.caosd.evoting.client.gui.EVotingClientWindow;

public class MainClient {

	public static void main(String[] args) {
		EVotingClient model = new EVotingClient();
		EVotingClientWindow view = new EVotingClientWindow();
		EVotingClientController controller = new EVotingClientController(model, view);
		view.getContentPane().registerController(controller);
		
		view.showInformation(">>Client ready.");
		
	}

}
