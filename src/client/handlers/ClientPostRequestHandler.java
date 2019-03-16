package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import client.HTTPHeader;
import client.commands.HTTPCommand;
import client.commands.HTTPPostCommand;

public class ClientPostRequestHandler extends ClientRequestHandler {

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
		
		//Prints the full response
		System.out.println(responseHeaderString);
		System.out.println(new String(responseContentBytes));
	}

}
