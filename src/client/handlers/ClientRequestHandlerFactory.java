package client.handlers;

import httpproperties.HTTPMethod;

/**
 * ClientRequestHandlerFactory is a class which uses the factory design pattern.
 * It creates the corresponding ClientRequestHandler using the accompanied httpMethod.
 *   The requestHandler is initiated.
 *
 */
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
		case POST:
			requestHandler = new ClientPostRequestHandler();
			break;
		case PUT:
			requestHandler = new ClientPutRequestHandler();
			break;
		case HEAD:
			requestHandler = new ClientHeadRequestHandler();
			break;
		//other cases go here
		default:
			requestHandler = new ClientGetRequestHandler();
			break;
		}
		return requestHandler;
	}
}
