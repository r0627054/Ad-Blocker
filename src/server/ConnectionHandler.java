package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import client.commands.HTTPCommand;
import client.commands.HTTPCommandFactory;
import client.handlers.ClientRequestHandler;
import client.handlers.ClientRequestHandlerFactory;
import httpproperties.HTTPMethod;
import server.handlers.ServerRequestHandler;
import server.handlers.ServerRequestHandlerFactory;
import shared.handlers.HttpRequestHandler;

public class ConnectionHandler implements Runnable{
	private Socket clientSocket;
	HttpRequestHandler helperHandler;
	
	public ConnectionHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
		this.helperHandler = new HttpRequestHandler();
	}
	
	
	@Override
	public void run() {
		try {
			HTTPRequestHeader header = new HTTPRequestHeader(helperHandler.getHeaderString(clientSocket.getInputStream()));
			
			byte[] body = null;
			
			if(header.getContentLength()>=0) {
				body = helperHandler.getBytes(header.getContentLength(), clientSocket.getInputStream());			
			}
			
			HTTPMethod method = HTTPMethod.valueOf(header.getHttpMethod());
			String uriString = header.getHeaderValue("Host") + header.getHeaderValue("Path");
			HTTPCommand command = HTTPCommandFactory.getHTTPCommand(method, uriString, clientSocket.getLocalPort());
			
			ServerRequestHandler requestHandler = ServerRequestHandlerFactory.getHandler(command.getHttpmethod());
			requestHandler.handle(command, body, clientSocket);
			
		} catch (Exception e) {
			try {
				helperHandler.respond404(clientSocket);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	

}
