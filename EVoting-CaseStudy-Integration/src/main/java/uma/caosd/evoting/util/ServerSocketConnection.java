package uma.caosd.evoting.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketConnection {
	public static final int SERVER_PORT = 4444;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ObjectOutputStream outStream;
	private ObjectInputStream inStream;

	public ServerSocketConnection(int port) {
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void acceptClient() {
		try {
			clientSocket = serverSocket.accept();
			outStream = new ObjectOutputStream(clientSocket.getOutputStream());
			inStream = new ObjectInputStream(clientSocket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void leaveClient() {
		try {
			outStream.close();
			inStream.close();
			clientSocket.close();
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
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
