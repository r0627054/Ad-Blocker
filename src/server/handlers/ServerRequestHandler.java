package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import client.commands.HTTPCommand;
import server.HTTPRequestHeader;
import shared.handlers.HttpRequestHandler;

/**
 * The Server RequestHandler is a HttpRequestHandler specially made for the client.
 *
 */
public abstract class ServerRequestHandler extends HttpRequestHandler {

	/**
	 * It handles the command on the server with the given information.
	 * @param command
	 *        | the incoming command which will be handled
	 * @param body
	 *        | the body of the incoming http request
	 * @param header
	 *        | the HTTPRequestHeader of the request
	 * @param socket
	 *        | the socket used to handle the command
	 * @throws Exception
	 */
	public abstract void handle(HTTPCommand command, byte[] body, HTTPRequestHeader header, Socket socket)throws Exception;
	
	/**
	 * Responds with a 304 code: Not Modified.
	 * @param socket 
	 *        | the socket used to write the output to the client.
	 * @throws IOException
	 */
	public void respond304(Socket socket) throws IOException {
		String output = "HTTP/1.1 304 Not Modified\n";
		output += writeDate() +"\n";
		output += writeContentLength(0);
		output +=  "\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(output.getBytes());
		//outputStream.close();
	}
}
