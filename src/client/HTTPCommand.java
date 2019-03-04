package client;

import java.net.URI;

public class HTTPCommand {

	private URI uri;
	private int port;
	private HTTPMethod httpMethod;
	
	
	public HTTPCommand(URI uri, int port, HTTPMethod httpMethod) {
		this.setUri(uri);
		this.setPort(port);
		this.setHttpmethod(httpMethod);
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

}
