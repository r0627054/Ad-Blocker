package client.commands;

import httpproperties.HTTPMethod;

/**
 * The HTTP PUT request method creates a new resource or
 *  replaces a representation of the target resource with the request payload.
 *  
 * The HTTPPUTCommand is a subclass of the HTTPCommand.
 *  This class is specifically made for the PUT REQUEST.
 *
 */
public class HTTPPutCommand extends HTTPCommand {

	/**
	 * Initialises a HTTP PUT command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 * @param httpVersionString
	 *        | the http version 
	 */
	public HTTPPutCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	/**
	 * Initialises a HTTP PUT command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 */
	public HTTPPutCommand(String uriString, int port) {
		super(uriString, port);
	}

	/**
	 * Returns the HTTPMethod: PUT
	 */
	@Override
	public HTTPMethod getHttpmethod() {
		return HTTPMethod.PUT;
	}

	/**
	 * Returns the generated header String in the correct format
	 */
	@Override
	public String getHeader() {
		String result = "";
		result += "PUT " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\r\n\r\n";
		return result;
	}
	
	/**
	 * Returns the generated header String in the correct format.
	 * 
	 * @param contentType
	 *        | The type of the content used in the body of the post request
	 * @param contentLength
	 *        | the length of the body of the post request.
	 * @return the fully created string, of the header.
	 */
	public String getFullHeader(String contentType, int contentLength) {
		String result = "";
		result += "PUT " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n";
		result += "Content-Type: " + contentType + "\n" ;
		result += "Content-Length: " + Integer.toString(contentLength) + "\r\n\r\n";
		return result;
	}

}
