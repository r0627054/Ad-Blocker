package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

import client.commands.HTTPCommand;
import client.commands.HTTPCommandFactory;
import httpproperties.HTTPMethod;
import server.handlers.ServerRequestHandler;
import server.handlers.ServerRequestHandlerFactory;
import shared.handlers.HttpRequestHandler;

/**
 * The connectionHandler handles one unique connection.
 *  
 */
public class ConnectionHandler implements Runnable{
	
	/**
	 *  The clientSocket used in the connection with the client.
	 */
	private Socket clientSocket;
	
	/**
	 * The HttpRequestHandler, which will handle the actual incoming request.
	 */
	private HttpRequestHandler helperHandler;
	
	/**
	 * Initialise a ConnectionHandler with the given clientSocket.
	 * 
	 * @param clientSocket
	 *        | the socket which know everything about the client.
	 */
	public ConnectionHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.helperHandler = new HttpRequestHandler();
	}
	
	/**
	 * The run method is called when the thread is created.
	 * It reads the header of the client and creates the matching serverRequestHandler; which will handle the the actual http request.
	 */
	@Override
	public void run() {
		try (
		        BufferedInputStream bis = new BufferedInputStream(clientSocket.getInputStream());
		        BufferedOutputStream bos = new BufferedOutputStream(clientSocket.getOutputStream());) {

		        boolean alive = true;
		        while (alive) {
		            alive = false;
		            try {
		                HTTPRequestHeader header = null;
		                try {
		                	String headerString = helperHandler.getHeaderString(bis);
		                	if(!headerString.trim().isEmpty()) {
		                		System.out.println(headerString);
		                		header = new HTTPRequestHeader(headerString);
			                	byte[] body = null;

			        			if(header.getContentLength()>=0) {
			        				body = helperHandler.getBytes(header.getContentLength(), bis);			
			        			}
			        			if(!header.containsHeader("Host")) {
			        				throw new BadRequestException("No host header present.");
			        			}

			        			HTTPMethod method = HTTPMethod.valueOf(header.getHttpMethod());
			        			String uriString = header.getHeaderValue("Host") + header.getHeaderValue("Path");
			        			HTTPCommand command = HTTPCommandFactory.getHTTPCommand(method, uriString, clientSocket.getLocalPort());
			        			ServerRequestHandler requestHandler = ServerRequestHandlerFactory.getHandler(command.getHttpmethod());
			        			requestHandler.handle(command, body, header, clientSocket);
		                	}
		                }catch (BadRequestException e) {
							helperHandler.respond400(clientSocket);
						}
		                catch (Exception e) {
							helperHandler.respond500(clientSocket);
							e.printStackTrace();
						}
		         
		                alive = true;                    
		            } catch (Exception e) {
		                alive = false;
		            }
		        }
		    } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	}
	

}
