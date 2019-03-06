package client.handlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import client.HTTPHeader;
import client.commands.HTTPCommand;
import files.FileSaver;

public class GetRequestHandler extends RequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
		outStream.writeBytes(command.getHeader());
		outStream.flush();
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		/*String s ="";
		while( (s = inFromServer.readLine()) != null ) {
			System.out.println(s);
		}*/
		//this.saveFile(b, "host", "filename", "html");
		
		
		String headerString = getHeaderString(inFromServer);
	    HTTPHeader header = new HTTPHeader(headerString);
	   
	    if(header.containsHeader("Content-Length")) {
	    	int contentLength = header.getContentLength();
	    	byte[] content = this.getBytes(contentLength, inFromServer);
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
