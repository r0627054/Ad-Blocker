package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;


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
	
		
		InputStream inputStream = socket.getInputStream();
		String headerString = this.getHeaderString(inputStream);
		HTTPHeader header = new HTTPHeader(headerString);
		System.out.println(headerString);
		if(header.containsHeader("Content-Length")) {
	    	int contentLength = header.getContentLength();
	    	byte[] content = this.getBytes(contentLength, inputStream);
	    	this.saveFile(content, command.getHost(), command.getBaseFileName(), header.getContentSubTypeResponse());
	    }else if(header.containsHeader("Transfer-Encoding") && "chunked".equals(header.getHeaderValue("Transfer-Encoding")) ) {
	    	this.readChunks(inputStream);
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
