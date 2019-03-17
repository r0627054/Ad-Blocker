package client.handlers;

import java.net.Socket;

import client.commands.HTTPCommand;
import shared.handlers.HttpRequestHandler;

/**
 * The Client RequestHandler is a HttpRequestHandler specially made for the client.
 *
 */
public abstract class ClientRequestHandler extends HttpRequestHandler{
	
	/**
	 * The clients handles the given command with the socket.
	 * @param command
	 *        | the Http command which needs to be handled
	 * @param socket
	 *        | the socket used to handle the command
	 * @throws Exception
	 */
	public abstract void handle(HTTPCommand command, Socket socket)throws Exception;
	
}
