package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerApp {
	private static final int PORT = 9000;
	private static ServerSocket serverSocket;
	private static ConnectionHandler connectionHandler;
	private static Thread thread;

	public static void main(String[] args) throws IOException {
		serverSocket = new ServerSocket(PORT);
		
		while(true) {
			connectionHandler = new ConnectionHandler(serverSocket.accept());
			thread = new Thread(connectionHandler);
			thread.start();
		}

	}
	
	protected void finalize() throws IOException {
		serverSocket.close();
	}

}
