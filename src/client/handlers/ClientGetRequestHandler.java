package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.HTTPHeader;
import client.commands.HTTPCommand;
import client.commands.HTTPGetCommand;
import files.FileSaver;

public class ClientGetRequestHandler extends ClientRequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		//request the resource
			
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		outputStream.writeBytes(command.getHeader());
		outputStream.flush();
	
		
		InputStream inputStream = socket.getInputStream();
		String headerString = this.getHeaderString(inputStream);
		HTTPHeader header = new HTTPHeader(headerString);
		//sysout the header
		//System.out.println(headerString);
				
		byte[] contentBytes = handleOneRequest(command, inputStream, header,true);
		if("html".equals(header.getContentSubTypeResponse())) {
			//Print the html file
			//UNCOMMENT THIS TO DISPLAY THE HTML FILE
			//System.out.println(new String(contentBytes));
			getOtherResources(command, inputStream, outputStream, contentBytes, header);
			
		}
	}
		
	public void getOtherResources(HTTPCommand command, InputStream inputStream, DataOutputStream outputStream, byte[] contentBytes, HTTPHeader header) throws Exception {
		if("html".equals(header.getContentSubTypeResponse())) {
			String content = this.bytesToString(contentBytes);
			//get all the source patterns out of the file
			Pattern p = Pattern.compile("<img[^>]*src=[\"']([^\"^']*)", Pattern.CASE_INSENSITIVE);
		    Matcher m = p.matcher(content);
		    //request all the resoucres
		    while(m.find()) {
		    	
		    	//UPDATE the current command with the new resource
		    	String newPath = m.group(1);
		    	if(!newPath.startsWith("/")) {
		    		newPath = "/" + newPath;
		    	}
		    	String newURIString = command.getHost() + newPath;
		    	command.updateToNewURI(newURIString);
		    	
		    	//OUTPUT the the new header
		    	outputStream.writeBytes(command.getHeader());
		    	outputStream.flush();
		    	
		    	
		    	// THE STORES ALL THE RESOURCES
		    	String resourceHeaderString = this.getHeaderString(inputStream);
				HTTPHeader resourceHeader = new HTTPHeader(resourceHeaderString);
				this.handleOneRequest(command, inputStream, resourceHeader,true);
		    }
		}
	}
	
	
	
	
	
	
	
	

}
