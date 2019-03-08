package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		//sysout the header
		//System.out.println(headerString);
		
		//At this moment we support content-length and transfer-encoding: chunked
		
		if(header.containsHeader("Content-Length")) {
			//reads the correct amount of bytes and  stores it in a file
	    	int contentLength = header.getContentLength();
	    	byte[] content = this.getBytes(contentLength, inputStream);
	    	this.saveFile(content, command.getHost(), command.getBaseFileName(), header.getContentSubTypeResponse());
	    	this.getOtherResources(command, socket, content, header);
	    	
	    }else if(header.containsHeader("Transfer-Encoding") && "chunked".equals(header.getHeaderValue("Transfer-Encoding")) ) {
	    	//reads all the chunks and stores it in a file
	    	byte[] content = this.readChunks(inputStream);
	    	this.saveFile(content, command.getHost(), command.getBaseFileName(), header.getContentSubTypeResponse());
	    	this.getOtherResources(command, socket, content, header);
	    }else {
	    	System.out.println("Nor Content-Length nor Transfer-Encoding is used in the headers");
	    }
	}
	
	public void getOtherResources(HTTPCommand command, Socket socket, byte[] contentBytes, HTTPHeader header) throws Exception {
		if("html".equals(header.getContentSubTypeResponse())) {
			String content = this.bytesToString(contentBytes);
			Pattern p = Pattern.compile("src=\"(.*?)\"");
			//System.out.println(content);
		    Matcher m = p.matcher(content);
		    //System.out.println(content);
		    while (m.find()) {
		        System.out.println(m.group(1));
		    }
			//System.out.println(content.indexOf("src"));
		}
	}
	
	
	
	public void saveFile(byte[] content, String host, String filename, String filetype) {
		new FileSaver( content,  host, filename, filetype);
	}
	
	
	
	

}
