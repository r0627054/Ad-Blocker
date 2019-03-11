package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import client.commands.HTTPCommand;
import client.commands.HTTPPostCommand;

public class ClientPostRequestHandler extends ClientRequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		String contentString = JOptionPane.showInputDialog("Please enter post content:");
		HTTPPostCommand postCommand = (HTTPPostCommand) command;
		byte[] contentBytes = contentString.getBytes();
		String headerString = postCommand.getFullHeader("text/plain", contentBytes.length);

		
		
		
		//open input and outputStream
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		System.out.println(headerString);
		System.out.println(contentString);
		outputStream.writeBytes(headerString);
		outputStream.writeBytes(contentString);
		outputStream.flush();
		InputStream inputStream = socket.getInputStream();
		System.out.println("TO DO client Post Request: print to the response");
		
		
	}

}
