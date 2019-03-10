package server.handlers;

import client.handlers.ClientGetRequestHandler;
import client.handlers.ClientRequestHandler;
import httpproperties.HTTPMethod;

public class ServerRequestHandlerFactory {
	
	public static ServerRequestHandler getHandler(HTTPMethod httpMethod) {
		if(httpMethod == null) {
			throw new IllegalArgumentException("Cannot create a handler when httpMethod is null.");
		}
		ServerRequestHandler requestHandler;
		switch (httpMethod) {
		case GET:
			requestHandler = new ServerGetRequestHandler();
			break;
		//other cases go here
		default:
			requestHandler = new ServerGetRequestHandler();
			break;
		}
		return requestHandler;
	}

}
