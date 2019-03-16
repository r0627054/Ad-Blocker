package client.commands;

import java.net.URI;
import java.net.URISyntaxException;

import httpproperties.HTTPMethod;
import httpproperties.HTTPVersion;

/**
 * A HTTPCommand is a HTTP command; it contains a uri, a port and a httpVersion.
 *
 */
public abstract class HTTPCommand {

	/**
	 * Variable storing the uri
	 */
	private URI uri;
	
	/**
	 * Variable storing the port
	 */
	private int port;
	
	/**
	 * Variable storing the http version
	 */
	private HTTPVersion httpVersion;
	
	/**
	 * Initialises a HTTP command with the given variables.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 * @param httpVersionString
	 *        | the http version 
	 */
	public HTTPCommand(String uriString, int port, String httpVersionString) {
		this.setPort(port);
		this.setUri(this.createURI(uriString));
		this.setHttpVersion(this.createHTTPVersion(httpVersionString));
	}
	
	/**
	 * Initialises a HTTP command with the given variables.
	 * By default HTTP version 1.1 is used.
	 * @param uriString
	 *        | the uri given in string format
	 * @param port
	 *        | the port which needs to be connected with
	 */
	public HTTPCommand(String uriString, int port) {
		this(uriString,port,HTTPVersion.HTTP11.getVersionCode());
	}

	/**
	 * Creates the uri out of the given string.
	 * @param uriString
	 *        | the 
	 * @return
	 */
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
	
	/**
	 * Updates the URI of the command with a new URI.
	 * @param uriString
	 *        | the uri string
	 */
	public void updateToNewURI(String uriString) {
		this.setUri(this.createURI(uriString));
	}
	
	/**
	 * Returns the host of the URI.
	 */
	public String getHost() {
		return uri.getHost();
	}
	
	/**
	 * Returns the path of the uri.
	 */
	public String getPath() {
		return uri.getPath();
	}
	
	/**
	 * Returns the URI op the command
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * Sets the URI of the HTTP Command.
	 * @param uri
	 *        | The uri in string format.
	 *  @throws IllegalArgumentException if the uri equals null
	 */
	private void setUri(URI uri) {
		if(uri == null) {
			throw new IllegalArgumentException("The uri cannot be null.");
		}
		this.uri = uri;
	}

	/**
	 * Returns the port of the command
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port of the http Command
	 * @param port
	 *        | the port of the http command
	 * @throws IllegalArgumentException when the port is negative. This is an incorrect value
	 */
	private void setPort(int port) {
		if(port < 0) {
			throw new IllegalArgumentException("A port cannot be negative.");
		}
		this.port = port;
	}

	/**
	 * Returnst the httpMethod the command uses
	 */
	public abstract HTTPMethod getHttpmethod();
	
	/**
	 * Returns the genrated Header String
	 */
	public abstract String getHeader();
	
	/**
	 * Returns the HTTP version of the HTTP Command
	 */
	public HTTPVersion getHttpVersion() {
		return this.httpVersion;
	}
	
	/**
	 * Sets the Http Version of the httpCommand
	 * @param httpVersion
	 *        | the version of HTTP used
	 * @throws IllegalArgumentException when the version equals null
	 */
	public void setHttpVersion(HTTPVersion httpVersion) {
		if(httpVersion == null) {
			throw new IllegalArgumentException("httpVersion cannot be null.");
		}
		this.httpVersion = httpVersion;
	}
	
	/**
	 * Creates the HTTP version out of the Http Version String
	 * @param v
	 *       | the String used for the version
	 * @return The HTTP Version
	 * @throws IllegalArgumentException if the given parameter equals null or if the current version is invalid.
	 */
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
	
	/**
	 * Returns the base filename of the requested resource.
	 */
	public String getBaseFileName() {
		int dashIndex = getPath().lastIndexOf('/');
		int dotIndex = getPath().lastIndexOf('.');
		if(dashIndex <0 || dotIndex<0 || dotIndex< dashIndex) {
			return "index";
		}else {
			return getPath().substring(dashIndex+1, getPath().lastIndexOf('.'));
		}
	}
	

}
