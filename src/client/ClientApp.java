package client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class ClientApp {

	
	//input:     GET www.tinyos.net/ 80 HTTP/1.1
	public static void main(String[] args) throws URISyntaxException, UnknownHostException, IOException {
		//all the parameters
		HTTPMethod httpMethod = HTTPMethod.valueOf(args[0]);		
		String uri = args[1];
		int port = Integer.parseInt(args[2]);
		HTTPVersion httpVersion = getHTTPVersion(args[3]);
		
		//create a HTTP Command with the given parameters
		HTTPCommand command = new HTTPCommand(uri, port, httpMethod);
	
		HTTPClient client = new HTTP11Client();
		client.handleCommand(command);
	}
	
	private static HTTPVersion getHTTPVersion(String v) {
		for (HTTPVersion version : HTTPVersion.values()) {
			if(version.getVersionCode().equals(v)) {
				return version;
			}
		}
		throw new IllegalArgumentException("Invalid HTTP version");
	}
}
