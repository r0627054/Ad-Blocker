package client.commands;

import httpproperties.HTTPMethod;

/**
 * The HTTP PUT request method creates a new resource or
 *  replaces a representation of the target resource with the request payload.
 *
 */
public class HTTPPutCommand extends HTTPCommand {

	
	public HTTPPutCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	public HTTPPutCommand(String uriString, int port) {
		super(uriString, port);
	}

	@Override
	public HTTPMethod getHttpmethod() {
		return HTTPMethod.PUT;
	}

	@Override
	public String getHeader() {
		String result = "";
		result += "PUT " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n\n";
		return result;
	}
	
	public String getFullHeader(String contentType, int contentLength) {
		String result = "";
		result += "PUT " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n";
		result += "Content-Type: " + contentType + "\n" ;
		result += "Content-Length: " + Integer.toString(contentLength) + "\n\n";
		return result;
	}

}
