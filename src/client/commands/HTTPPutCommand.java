package client.commands;

import httpproperties.HTTPMethod;

public class HTTPPutCommand extends HTTPCommand {

	
	public HTTPPutCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	public HTTPPutCommand(String uriString, int port) {
		super(uriString, port);
		// TODO Auto-generated constructor stub
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

}
