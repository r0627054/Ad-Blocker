package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import client.HTTPHeader;
import client.commands.HTTPCommand;
import client.commands.HTTPPutCommand;

public class ClientPutRequestHandler extends ClientRequestHandler {

	//Tested on https://webhook.site/
	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		//show an input dialog
		String contentString = JOptionPane.showInputDialog(null,"Please enter PUT content:","Put Request",JOptionPane.QUESTION_MESSAGE);

		if(contentString == null) {
			contentString = "";
		}
		HTTPPutCommand putCommand = (HTTPPutCommand) command;
		byte[] contentBytes = contentString.getBytes();
		String headerString = putCommand.getFullHeader("text/plain", contentBytes.length);	
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
