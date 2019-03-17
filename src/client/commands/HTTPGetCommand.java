package client.commands;

import httpproperties.HTTPMethod;
/**
 * The HTTPGetCommand is a subclass of the HTTPCommand.
 *  This class is specifically made the GET REQUEST.
 *
 */
public class HTTPGetCommand extends HTTPCommand {

	/**
	 * Initialises a HTTP GET command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 * @param httpVersionString
	 *        | the http version 
	 */
	public HTTPGetCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	/**
	 * Initialises a HTTP GET command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 */
	public HTTPGetCommand(String uriString, int port) {
		super(uriString, port);
	}

	/**
	 * Returns the HTTPMethod: GET
	 */
	@Override
	public HTTPMethod getHttpmethod() {
		return HTTPMethod.GET;
	}

	/**
	 * Returns the generated header String in the correct format.
	 */
	@Override
	public String getHeader() {
		String result = "";
		result += "GET " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\r\n\r\n";
		return result;
	}

}
