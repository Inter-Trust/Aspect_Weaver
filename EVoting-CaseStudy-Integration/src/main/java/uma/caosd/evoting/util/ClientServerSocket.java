package uma.caosd.evoting.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientServerSocket {
	private static ObjectOutputStream outStream;
	private static ObjectInputStream inStream;
	
	public static void sendObject(Socket s, Object o) throws IOException {
		outStream = new ObjectOutputStream(s.getOutputStream());
		outStream.writeObject(o);
		//outStream.flush();
		outStream.close();
	}
	
	public static Object receiveObject(Socket s) throws IOException, ClassNotFoundException {
		inStream = new ObjectInputStream(s.getInputStream());
		Object res = inStream.readObject();
		//inStream.close();
		return res;
	}
}
