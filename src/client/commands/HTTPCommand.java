package client.commands;

import java.net.URI;
import java.net.URISyntaxException;

import httpproperties.HTTPMethod;
import httpproperties.HTTPVersion;

public abstract class HTTPCommand {

	private URI uri;
	private int port;
	//private HTTPMethod httpMethod;
	private HTTPVersion httpVersion;
	
	public HTTPCommand(String uriString, int port, String httpVersionString) {
		this.setPort(port);
		this.setUri(this.createURI(uriString));
		//this.setHttpmethod(httpMethod);
		this.setHttpVersion(this.createHTTPVersion(httpVersionString));
	}
	
	public HTTPCommand(String uriString, int port) {
		this(uriString,port,HTTPVersion.HTTP11.getVersionCode());
	}

	public URI createURI(String uriString) {
		if(uriString == null) {
			throw new IllegalArgumentException("The uri string cannot be null.");
		}
		String u =null;
		if(uriString.startsWith("http")) {
			u = uriString;
		}else {
			u = "http://" + uriString;
		}
		try {
			return new URI(u);
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

	
	public abstract HTTPMethod getHttpmethod();
	
	/*public HTTPMethod getHttpmethod() {
		return httpMethod;
	}*/

	/*private void setHttpmethod(HTTPMethod httpMethod) {
		if(httpMethod == null) {
			throw new IllegalArgumentException("HTTPMethod cannot be null.");
		}
		this.httpMethod = httpMethod;
	}*/
	
	
	public abstract String getHeader();
	
	/*public String getHeader() {
		String result = "";
		result += "GET " + this.getPath() + " HTTP/1.1" + "\n";
		result += "Host: " + this.getHost() + "\n\n";
		return result;
	}*/
	
	public HTTPVersion getHttpVersion() {
		return this.httpVersion;
	}
	
	public void setHttpVersion(HTTPVersion httpVersion) {
		if(httpVersion == null) {
			throw new IllegalArgumentException("httpVersion cannot be null.");
		}
		this.httpVersion = httpVersion;
	}
	
	private HTTPVersion createHTTPVersion(String v) {
		if(v == null) {
			throw new IllegalArgumentException("The httpVersion String cannot be null");
		}
		for (HTTPVersion version : HTTPVersion.values()) {
			if(version.getVersionCode().equals(v)) {
				return version;
			}
		}
		throw new IllegalArgumentException("Invalid HTTP version");
	}
	

}
