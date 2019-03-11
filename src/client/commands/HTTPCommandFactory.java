package client.commands;

import httpproperties.HTTPMethod;

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
		case PUT:
			httpCommand = new HTTPPutCommand(uriString,port);
			break;
		case POST:
			httpCommand = new HTTPPostCommand(uriString, port);
		//other cases go here
		default:
			httpCommand = new HTTPGetCommand(uriString,port);
			break;
		}
		return httpCommand;
	}
}
