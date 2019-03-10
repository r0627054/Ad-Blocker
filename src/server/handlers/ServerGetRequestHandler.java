package server.handlers;

import java.net.Socket;

import client.commands.HTTPCommand;

public class ServerGetRequestHandler extends ServerRequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		System.out.println(command.getHeader());
		
	}
	
	
	

}
