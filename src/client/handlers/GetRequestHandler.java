package client.handlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import client.HTTPHeader;
import client.commands.HTTPCommand;

public class GetRequestHandler extends RequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
		outStream.writeBytes(command.getHeader());
		outStream.flush();
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String headerString = getHeaderString(inFromServer);
	    HTTPHeader header = new HTTPHeader(headerString);
	   
	    if(header.containsHeader("Content-Length")) {
	    	int contentLength = header.getContentLength();
	    	byte[] b = this.getBytes(contentLength, inFromServer);
	    	System.out.println(bytesToString(b));
	    }else if(header.containsHeader("Transfer-Encoding")) {
	    	
	    }else {
	    	System.out.println("Nor Content-Length nor Transfer-Encoding is used in the headers");
	    }

	    
    //outStream.close();
	//inFromServer.close();
	}
	
	
	
	

}
