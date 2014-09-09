package uma.caosd.evoting.client.gui;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class EVotingClientWindow extends JFrame {
	public static final String TITLE = "e-Voting Client";
	private EVotingClientPane contentPane;

	/**
	 * Create the frame.
	 */
	public EVotingClientWindow() {
		contentPane = new EVotingClientPane();
		
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void showInformation(String info) {
		contentPane.showInformation(info);
	}

	public EVotingClientPane getContentPane() {
		return contentPane;
	}
}
