package client.commands;

import httpproperties.HTTPMethod;
/**
 * HTTPCommandFactory is a class which uses the factory design pattern.
 * It creates the corresponding httpCommand using the accompanied httpMethod.
 *   The httpCommand is initiated with the given uriString and port.
 *
 */
public class HTTPCommandFactory {

	/**
	 * Creates the corresponding httpCommand using the accompanied httpMethod.
     *   The httpCommand is initiated with the given uriString and port.
     *   By default a HTTPGetCommand is used.
	 * @param httpMethod
	 *        | the HTTP Method used
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port used by the socket
	 * @return the HTTP Command created
	 * @throws IllegalArgumentException if the given method equals null
	 */
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
			break;
		case HEAD:
			httpCommand = new HTTPHeadCommand(uriString, port);
			break;
		//other cases go here
		default:
			httpCommand = new HTTPGetCommand(uriString,port);
			break;
		}
		return httpCommand;
	}
}
