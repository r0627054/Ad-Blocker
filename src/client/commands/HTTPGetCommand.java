package client.commands;

import client.HTTPMethod;

public class HTTPGetCommand extends HTTPCommand {

	public HTTPGetCommand(String uriString, int port, HTTPMethod httpMethod, String httpVersionString) {
		super(uriString,port,httpMethod,httpVersionString);
	}
	
	public HTTPGetCommand(String uriString, int port, HTTPMethod httpMethod) {
		super(uriString, port, httpMethod);
	}

}
