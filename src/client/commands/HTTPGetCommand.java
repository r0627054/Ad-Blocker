package client.commands;

import httpproperties.HTTPMethod;

public class HTTPGetCommand extends HTTPCommand {

	public HTTPGetCommand(String uriString, int port, String httpVersionString) {
		super(uriString,port,httpVersionString);
	}
	
	public HTTPGetCommand(String uriString, int port) {
		super(uriString, port);
	}

	@Override
	public HTTPMethod getHttpmethod() {
		return HTTPMethod.GET;
	}

	@Override
	public String getHeader() {
		String result = "";
		result += "GET " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n\n";
		return result;
	}

}
