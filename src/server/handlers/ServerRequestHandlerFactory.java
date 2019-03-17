package server.handlers;

import httpproperties.HTTPMethod;

/**
 * ServerRequestHandlerFactory is a class which uses the factory design pattern.
 * It creates the corresponding ServerRequestHandler using the accompanied httpMethod.
 *   The requestHandler is initiated.
 *
 */
public class ServerRequestHandlerFactory {
	
	/**
	 * Returns a ServerRequestHandler which matches the given httpMethod.
	 * @param httpMethod
	 *        | the httpMethod used in the incoming request.
	 */
	public static ServerRequestHandler getHandler(HTTPMethod httpMethod) {
		if(httpMethod == null) {
			throw new IllegalArgumentException("Cannot create a handler when httpMethod is null.");
		}
		ServerRequestHandler requestHandler;
		switch (httpMethod) {
		case GET:
			requestHandler = new ServerGetRequestHandler();
			break;
		case POST:
			requestHandler = new ServerPostRequestHandler();
			break;
		case PUT:
			requestHandler = new ServerPutRequestHandler();
			break;
		case HEAD:
			requestHandler = new ServerHeadRequestHandler();
			break;
		default:
			requestHandler = new ServerGetRequestHandler();
			break;
		}
		return requestHandler;
	}

}
