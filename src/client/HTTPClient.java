package client;

import java.net.Socket;

import client.commands.HTTPCommand;

/**
 * Every HTTP client should have a socket and will need to handle a command.
 *
 */
public abstract class HTTPClient {

	/**
	 * Variable storing the socket.
	 */
	protected Socket socket;
	
	/**
	 * Handles the given command
	 * @param command
	 *        | the http command
	 * @throws Exception
	 */
	public abstract void handleCommand(HTTPCommand command) throws Exception;

	/**
	 * Returns the socket of the client.
	 */
	public Socket getSocket() {
		return this.socket;
	}
	
}
