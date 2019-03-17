package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

import client.commands.HTTPCommand;

/**
 * The HTTP HEAD method requests the headers that are returned if the specified resource
 *   would be requested with an HTTP GET method.
 * Such a request can be done before deciding to download a large resource to save
 *   bandwidth, for example.
 */
public class ClientHeadRequestHandler extends ClientRequestHandler {

	/**
	 * The handle method sends a HTTP HEAD request using the socket.
	 *  It prints out the received headerString
	 * 
	 * @param command
	 *        | the Http command which needs to be handled
	 * @param socket
	 *        | the socket used to handle the command
	 * @throws Exception
	 */
	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		outputStream.writeBytes(command.getHeader());
		outputStream.flush();
		
		InputStream inputStream = socket.getInputStream();
		String headerString = this.getHeaderString(inputStream);
		
		//SYSOUT the header received form the HEAD command
		System.out.println(headerString);
	}

}
