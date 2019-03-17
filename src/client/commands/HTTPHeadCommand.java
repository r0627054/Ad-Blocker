package client.commands;

import httpproperties.HTTPMethod;

/**
 * The HTTPHeadCommand is a subclass of the HTTPCommand.
 *  This class is specifically made the HEAD REQUEST.
 *
 */
public class HTTPHeadCommand extends HTTPCommand{

	/**
	 * Initialises a HTTP HEAD command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 * @param httpVersionString
	 *        | the http version 
	 */
	public HTTPHeadCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	/**
	 * Initialises a HTTP HEAD command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 */
	public HTTPHeadCommand(String uriString, int port) {
		super(uriString, port);
	}
	
	/**
	 * Returns the HTTPMethod: HEAD
	 */
	@Override
	public HTTPMethod getHttpmethod() {
		return HTTPMethod.HEAD;
	}

	/**
	 * Returns the generated header String in the correct format
	 */
	@Override
	public String getHeader() {
		String result = "";
		result += "HEAD " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\r\n\r\n";
		return result;
	}

}
