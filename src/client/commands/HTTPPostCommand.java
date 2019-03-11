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
		// TODO Auto-generated method stub
		return null;
	}

}
