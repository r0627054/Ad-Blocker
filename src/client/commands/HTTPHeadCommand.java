package client.commands;

import httpproperties.HTTPMethod;

public class HTTPHeadCommand extends HTTPCommand{

	public HTTPHeadCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	public HTTPHeadCommand(String uriString, int port) {
		super(uriString, port);
	}
	
	@Override
	public HTTPMethod getHttpmethod() {
		return HTTPMethod.HEAD;
	}

	@Override
	public String getHeader() {
		String result = "";
		result += "HEAD " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n\n";
		return result;
	}

}
