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
 * 
 * @author Dries Janse
 *
 */
public class ClientHeadRequestHandler extends ClientRequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		outputStream.writeBytes(command.getHeader());
		outputStream.flush();
		
		InputStream inputStream = socket.getInputStream();
		String headerString = this.getHeaderString(inputStream);
		System.out.println(headerString);
	}

}
