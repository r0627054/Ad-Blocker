package client.commands;

import client.HTTPMethod;
import client.handlers.GetRequestHandler;

public class HTTPCommandFactory {

	public static HTTPCommand getHTTPCommand(HTTPMethod httpMethod, String uriString, int port) {
		if(httpMethod == null) {
			throw new IllegalArgumentException("Cannot create a HTTPCommand when httpMethod is null.");
		}
		HTTPCommand httpCommand = null;
		switch (httpMethod) {
		case GET:
			//HTTPCommand = new HTTPGetCommand();
			break;
		//other cases go here
		default:
			//requestHandler = new GetRequestHandler();
			break;
		}
		return httpCommand;
	}
}
