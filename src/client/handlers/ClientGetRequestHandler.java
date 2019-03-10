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
			
		DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
		outStream.writeBytes(command.getHeader());
		outStream.flush();
	
		
		InputStream inputStream = socket.getInputStream();
		String headerString = this.getHeaderString(inputStream);
		HTTPHeader header = new HTTPHeader(headerString);
		//sysout the header
		//System.out.println(headerString);
		
		//At this moment we support content-length and transfer-encoding: chunked
		/*if(header.containsHeader("Content-Length")) {
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
	    }*/
		
		byte[] contentBytes = handleOneRequest(command, inputStream, header);
		getOtherResources(command, socket, contentBytes, header);
		
		
		
	}
	
	
	private byte[] handleOneRequest(HTTPCommand command, InputStream inputStream, HTTPHeader header) throws Exception{
		byte[] content = null;
		if(header.containsHeader("Content-Length")) {
			//reads the correct amount of bytes and  stores it in a file
	    	int contentLength = header.getContentLength();
	    	 content = this.getBytes(contentLength, inputStream);
	    	this.saveFile(content, command.getHost(), command.getBaseFileName(), header.getContentSubTypeResponse());	    	
	    }else if(header.containsHeader("Transfer-Encoding") && "chunked".equals(header.getHeaderValue("Transfer-Encoding")) ) {
	    	//reads all the chunks and stores it in a file
	    	content = this.readChunks(inputStream);
	    	this.saveFile(content, command.getHost(), command.getBaseFileName(), header.getContentSubTypeResponse());
	    }else {
	    	System.out.println("Nor Content-Length nor Transfer-Encoding is used in the headers");
	    }
		return content;
	}
	
	
	public void getOtherResources(HTTPCommand command, Socket socket, byte[] contentBytes, HTTPHeader header) throws Exception {
		if("html".equals(header.getContentSubTypeResponse())) {
			String content = this.bytesToString(contentBytes);
			//get all the source patterns out of the file
			Pattern p = Pattern.compile("<img[^>]*src=[\"']([^\"^']*)", Pattern.CASE_INSENSITIVE);
		    Matcher m = p.matcher(content);
		    while (m.find()) {
		        System.out.println(m.group(1));
		    }
		}
	}
	
	
	
	public void saveFile(byte[] content, String host, String filename, String filetype) {
		new FileSaver( content,  host, filename, filetype);
	}
	
	
	
	

}
