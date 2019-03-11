package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import client.commands.HTTPCommand;
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
			HTTPCommand command = helperHandler.getHttpCommandFromHeader(clientSocket);
			ServerRequestHandler requestHandler = ServerRequestHandlerFactory.getHandler(command.getHttpmethod());
			requestHandler.handle(command, clientSocket);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
//		try {
//            InputStream input  = clientSocket.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//            StringBuilder result = new StringBuilder();
//            String line;
//            while((line = reader.readLine()) != null) {
//                result.append(line);
//            }
//            System.out.println(result.toString());
//            
//            OutputStream output = clientSocket.getOutputStream();
//            String outString = "HTTP/1.1 200 OK\n\n";
//            output.write(outString.getBytes());
//            
//            input.close();
//            output.close();
//            System.out.println("Request processed");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
		
	}
	

}
