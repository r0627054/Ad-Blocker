package server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * The serverApp used to run the server.
 *
 */
public class ServerApp {
	/**
	 * The variable holding the port which the server is running
	 */
	private static final int PORT = 80;
	
	/**
	 * The variable storing the server socket.
	 */
	private static ServerSocket serverSocket;
	
	/**
	 * The variable storing the connectionHandler:
	 *    Connection handling one connection (1 socket) with a client
	 */
	private static ConnectionHandler connectionHandler;
	
	/**
	 * The variable storing the thread
	 */
	private static Thread thread;

	/**
	 *  Establishing the Connection: Server socket object is initialized and inside a while loop a socket object continuously accepts incoming connection.
	 * Creating a handler object: After obtaining the streams and port number, a new clientHandler object (the above class) is created with these parameters.
	 * Invoking the start() method : The start() method is invoked on this newly created thread object.
	 *
	 */
	public static void main(String[] args) throws IOException {
		serverSocket = new ServerSocket(PORT);
		
		while(true) {
			connectionHandler = new ConnectionHandler(serverSocket.accept());
			System.out.println("A socket is created");
			thread = new Thread(connectionHandler);
			thread.start();
		}

	}
	
	/**
	 * The finalize, closes the serverSocket at shutdown.
	 */
	protected void finalize() throws IOException {
		serverSocket.close();
	}

}
