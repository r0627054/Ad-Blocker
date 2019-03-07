package client.handlers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.HTTPHeader;
import client.commands.HTTPCommand;
import files.FileSaver;

public class GetRequestHandler extends RequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		//request the resource
		DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
		outStream.writeBytes(command.getHeader());
		outStream.flush();
		
		
		
		
		/*
		//VERSION 1 WITH bufferedReader 
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
		String headerString = getHeaderString(inFromServer);
	    HTTPHeader header = new HTTPHeader(headerString);
	   
	    if(header.containsHeader("Content-Length")) {
	    	int contentLength = header.getContentLength();
	    	byte[] content = this.getBytes(contentLength, inFromServer);
	    	this.saveFile(content, command.getHost(), "index", "html");
	    }else if(header.containsHeader("Transfer-Encoding")) {
	    	
	    }else {
	    	System.out.println("Nor Content-Length nor Transfer-Encoding is used in the headers");
	    }*/
		
		
		//VERSION 2 reading all bytes
				
		InputStream inputStream = socket.getInputStream();
		String headerString = this.getHeaderString(inputStream);
		HTTPHeader header = new HTTPHeader(headerString);
		if(header.containsHeader("Content-Length")) {
	    	int contentLength = header.getContentLength();
	    	byte[] content = this.getBytes(contentLength, inputStream);
	    	this.saveFile(content, command.getHost(), "index", "html");
	    }else if(header.containsHeader("Transfer-Encoding")) {
	    	
	    }else {
	    	System.out.println("Nor Content-Length nor Transfer-Encoding is used in the headers");
	    }
		

	    
    //outStream.close();
	//inFromServer.close();
	}
	
	
	
	public void saveFile(byte[] content, String host, String filename, String filetype) {
		new FileSaver( content,  host, filename, filetype);
	}
	
	
	
	

}
