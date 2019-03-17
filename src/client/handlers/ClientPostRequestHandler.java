package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import client.HTTPHeader;
import client.commands.HTTPCommand;
import client.commands.HTTPPostCommand;

/**
 * The ClientPostRequestHandler is a subclass of the Client Request handler.
 *  It is made for handling the POST request at client side.
 *  
 *  The HTTP POST method sends data to the server.
 *  The type of the body of the request is indicated by the Content-Type header.
 *  
 */
public class ClientPostRequestHandler extends ClientRequestHandler {

	/**
	 * The handle method sends a HTTP Post request using the socket.
	 *  Requests the POST command message, using a JOptionPane.
	 *  And show the response in the console.
	 * 
	 * @param command
	 *        | the Http command which needs to be handled
	 * @param socket
	 *        | the socket used to handle the command
	 * @throws Exception
	 */
	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		//show an input dialog
		String contentString = JOptionPane.showInputDialog(null, "Please enter POST content:","Post Request",JOptionPane.QUESTION_MESSAGE);
		if(contentString == null) {
			contentString = "";
		}
		
		HTTPPostCommand postCommand = (HTTPPostCommand) command;
		byte[] contentBytes = contentString.getBytes();
		String headerString = postCommand.getFullHeader("text/plain", contentBytes.length);	
		//open input and outputStream
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		
		outputStream.writeBytes(headerString);
		outputStream.writeBytes(contentString);
		outputStream.flush();
		
		
		InputStream inputStream = socket.getInputStream();
		
		String responseHeaderString = this.getHeaderString(inputStream);
		HTTPHeader responseHeader = new HTTPHeader(responseHeaderString);
		byte[] responseContentBytes = handleOneRequest(command, inputStream, responseHeader,false);
		
		//SYSOUT the full response
		System.out.println(responseHeaderString);
		System.out.println(this.bytesToString(responseContentBytes));
	}

}
