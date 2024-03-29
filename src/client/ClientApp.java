package client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import client.commands.HTTPCommand;
import client.commands.HTTPCommandFactory;
import httpproperties.HTTPMethod;

public class ClientApp {

	
	//input:     GET www.tinyos.net/ 80 HTTP/1.1
	//nice example to download images: GET http://www.tutorialspoint.com/html/html_images.htm 80 HTTP/1.1
	//The request can easily be tested with: https://webhook.site/
	public static void main(String[] args) throws Exception {
		//all the parameters
		HTTPMethod httpMethod = HTTPMethod.valueOf(args[0]);		
		String uri = args[1];
		int port = Integer.parseInt(args[2]);
				
		HTTPCommand command = HTTPCommandFactory.getHTTPCommand(httpMethod, uri, port);
		
		
		HTTPClient client = new HTTP11Client();
		client.handleCommand(command);
	}
	
}
