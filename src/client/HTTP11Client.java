package client;

import java.net.Socket;

import client.commands.HTTPCommand;
import client.handlers.ClientRequestHandler;
import client.handlers.ClientRequestHandlerFactory;

/**
 * The HTTP11Client is a subclass of a HTTPClient.
 *   The client makes use of the version 1.1 of HTTP.
 *
 */
public class HTTP11Client extends HTTPClient {

	/**
	 * Initialises the HTTPClient
	 */
	public HTTP11Client() {
		
	}
	
	/**
	 * Handles the given command.
	 * It handles the command by requesting the correct handler,
	 * the handler needs to handle the command further.
	 * @param command
	 *        | the http command
	 * @throws Exception
	 */
	@Override
	public void handleCommand(HTTPCommand command) throws Exception {
		if(this.socket == null) {
			this.socket = new Socket(command.getHost(), command.getPort());
		}
		ClientRequestHandler requestHandler = ClientRequestHandlerFactory.getHandler(command.getHttpmethod());
		try {
			requestHandler.handle(command, socket);
		} catch (Exception e) {
			//throw new Exception("Couldn't handle the request");
		e.printStackTrace();
		}

		//socket is closed
		this.socket.close();
		}

	
}
