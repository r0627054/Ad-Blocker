package client.commands;

import client.HTTPMethod;

public class HTTPCommandFactory {

	public static HTTPCommand getHTTPCommand(HTTPMethod httpMethod, String uriString, int port) {
		if(httpMethod == null) {
			throw new IllegalArgumentException("Cannot create a HTTPCommand when httpMethod is null.");
		}
		HTTPCommand httpCommand;
		switch (httpMethod) {
		case GET:
			httpCommand = new HTTPGetCommand(uriString,port);
			break;
		//other cases go here
		default:
			httpCommand = new HTTPGetCommand(uriString,port);
			break;
		}
		return httpCommand;
	}
}
