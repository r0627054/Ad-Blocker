package client.commands;

import httpproperties.HTTPMethod;

/**
 * The HTTPPostCommand is a subclass of the HTTPCommand.
 *  This class is specifically made for the POST REQUEST.
 *
 */
public class HTTPPostCommand extends HTTPCommand {

	/**
	 * Initialises a HTTP POST command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 * @param httpVersionString
	 *        | the http version 
	 */
	public HTTPPostCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	/**
	 * Initialises a HTTP POST command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 */
	public HTTPPostCommand(String uriString, int port) {
		super(uriString, port);
	}
		
	/**
	 * Returns the HTTPMethod: POST
	 */
	@Override
	public HTTPMethod getHttpmethod() {
		return HTTPMethod.POST;
	}

	/**
	 * Returns the generated header String in the correct format.
	 * WATCH OUT: this does not contain content.
	 */
	@Override
	public String getHeader() {
		String result = "";
		result += "POST " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n\n";
		return result;
	}
	
	/**
	 * Returns the generated header String in the correct format.
	 * 
	 * 
	 * @param contentType
	 *        | The type of the content used in the body of the post request
	 * @param contentLength
	 *        | the length of the body of the post request.
	 * @return the fully created string, of the header.
	 */
	public String getFullHeader(String contentType, int contentLength) {
		String result = "";
		result += "POST " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n";
		result += "Content-Type: " + contentType + "\n" ;
		result += "Content-Length: " + Integer.toString(contentLength) + "\n\n";
		return result;
	}

}
