package client.handlers;

import client.HTTPMethod;

public class RequestHandlerFactory {

	public static RequestHandler getHandler(HTTPMethod httpMethod) {
		if(httpMethod == null) {
			throw new IllegalArgumentException("Cannot create a handler when httpMethod is null.");
		}
		RequestHandler requestHandler;
		switch (httpMethod) {
		case GET:
			requestHandler = new GetRequestHandler();
			break;
		//other cases go here
		default:
			requestHandler = new GetRequestHandler();
			break;
		}
		return requestHandler;
	}
}
