package uma.caosd.evoting.server.gui;

import javax.swing.JFrame;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class EVotingServerWindow extends JFrame {
	public static final String TITLE = "e-Voting Server";
	private static EVotingServerPane contentPane;

	/**
	 * Create the frame.
	 */
	public EVotingServerWindow() {
		contentPane = new EVotingServerPane();
		
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

	public static JTextArea getConsole() {
		return contentPane.getTextAreaInformation();
	}
}
