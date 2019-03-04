package client;

import java.net.URI;
import java.net.URISyntaxException;

public class ClientApp {

	
	//input:     GET www.tinyos.net/ 80 HTTP/1.1
	public static void main(String[] args) throws URISyntaxException {
		//all the parameters
		HTTPCommand httpCommand = HTTPCommand.valueOf(args[0]);
		URI uri = new URI(args[1]);
		int port = Integer.parseInt(args[2]);
		HTTPVersion httpVersion = getHTTPVersion(args[3]);
	}
	
	public static HTTPVersion getHTTPVersion(String v) {
		for (HTTPVersion version : HTTPVersion.values()) {
			if(version.getVersionCode().equals(v)) {
				return version;
			}
		}
		throw new IllegalArgumentException("Invalid HTTP version");
	}
}
