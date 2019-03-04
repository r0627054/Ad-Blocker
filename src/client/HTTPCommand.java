package client;

import java.net.URI;
import java.net.URISyntaxException;

public class HTTPCommand {

	private URI uri;
	private int port;
	private HTTPMethod httpMethod;
	
	
	public HTTPCommand(String uriString, int port, HTTPMethod httpMethod) {
		this.createURI(uriString);
		this.setPort(port);
		this.setHttpmethod(httpMethod);
	}

	private void createURI(String uriString) {
		String u =null;
		if(uriString.startsWith("http")) {
			u = uriString;
		}else {
			u = "http://" + uriString;
		}
		try {
			this.setUri(new URI(u));
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException("HTTPCommand cannot have an invalid URI.");
		}
	}
	
	public String getHost() {
		return uri.getHost();
	}
	
	public String getPath() {
		return uri.getPath();
	}
	
	public URI getUri() {
		return uri;
	}

	private void setUri(URI uri) {
		if(uri == null) {
			throw new IllegalArgumentException("The uri cannot be null.");
		}
		this.uri = uri;
	}

	public int getPort() {
		return port;
	}

	private void setPort(int port) {
		if(port < 0) {
			throw new IllegalArgumentException("A port cannot be negative.");
		}
		this.port = port;
	}

	public HTTPMethod getHttpmethod() {
		return httpMethod;
	}

	private void setHttpmethod(HTTPMethod httpMethod) {
		if(httpMethod == null) {
			throw new IllegalArgumentException("HTTPMethod cannot be null.");
		}
		this.httpMethod = httpMethod;
	}
	
	public String getHeader() {
		String result = "";
		result += "GET " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n\n";
		
		
		return result;
	}
	

}
