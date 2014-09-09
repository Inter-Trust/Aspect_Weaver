package uma.caosd.evoting.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocketConnection {
	private Socket socket;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;
	
	public ClientSocketConnection(String host, int serverPort) {
		try {
			socket = new Socket(host, serverPort);
			outStream = new ObjectOutputStream(socket.getOutputStream());
			inStream = new ObjectInputStream(socket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendObject(Object o) {
		try {
			outStream.writeObject(o);
			outStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object receiveObject() {
		try {
			return inStream.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void closeSocketConnection() {
		try {
			outStream.close();
			inStream.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
