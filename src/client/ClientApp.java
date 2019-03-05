package client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class ClientApp {

	
	//input:     GET www.tinyos.net/ 80 HTTP/1.1
	public static void main(String[] args) throws Exception {
		//all the parameters
		HTTPMethod httpMethod = HTTPMethod.valueOf(args[0]);		
		String uri = args[1];
		int port = Integer.parseInt(args[2]);
		
		//We dont use the HTTPVersion, only http/1.1 needs to be implemented
		//HTTPVersion httpVersion = getHTTPVersion(args[3]);
		
		
		HTTPCommand command = new HTTPCommand(uri, port, httpMethod);

		HTTPClient client = new HTTP11Client();
		client.handleCommand(command);
	}
	
}
