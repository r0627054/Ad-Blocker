package client.handlers;

import httpproperties.HTTPMethod;

public class ClientRequestHandlerFactory {

	public static ClientRequestHandler getHandler(HTTPMethod httpMethod) {
		if(httpMethod == null) {
			throw new IllegalArgumentException("Cannot create a handler when httpMethod is null.");
		}
		ClientRequestHandler requestHandler;
		switch (httpMethod) {
		case GET:
			requestHandler = new ClientGetRequestHandler();
			break;
		//other cases go here
		default:
			requestHandler = new ClientGetRequestHandler();
			break;
		}
		return requestHandler;
	}
}
