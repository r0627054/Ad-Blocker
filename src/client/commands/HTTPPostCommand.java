package client.commands;

import httpproperties.HTTPMethod;

public class HTTPPostCommand extends HTTPCommand {

	
	public HTTPPostCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	public HTTPPostCommand(String uriString, int port) {
		super(uriString, port);
	}
		
	@Override
	public HTTPMethod getHttpmethod() {
		return HTTPMethod.POST;
	}

	@Override
	public String getHeader() {
		String result = "";
		result += "POST " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n\n";
		return result;
	}
	
	
	public String getFullHeader(String contentType, int contentLength) {
		String result = "";
		result += "POST " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n";
		result += "Content-Type: " + contentType + "\n" ;
		result += "Content-Length: " + Integer.toString(contentLength) + "\n\n";
		return result;
	}

}
