package client.handlers;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adblocker.AdBlocker;
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

				
		byte[] contentBytes = handleOneRequest(command, inputStream, header,true);
		if("html".equals(header.getContentSubTypeResponse())) {
			//raw content html
			String rawContent = this.bytesToString(contentBytes);
			
			//add block the html
			List<String> adBlockKeyWords = new ArrayList<>();
			//removes all the images containg ad
			adBlockKeyWords.add("ad");
			AdBlocker adBlocker = new AdBlocker(rawContent, adBlockKeyWords);
			adBlocker.removeAdImages();
			String blockedHtml = adBlocker.getHtml();
			
			System.out.println(blockedHtml);
			
			getOtherResources(command, inputStream, outputStream, blockedHtml, header);
		}
	}
		
	public void getOtherResources(HTTPCommand command, InputStream inputStream, DataOutputStream outputStream, String content, HTTPHeader header) throws Exception {
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
