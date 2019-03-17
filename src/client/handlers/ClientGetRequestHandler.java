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

/**
 * The ClientGetRequestHandler is a subclass of the Client Request handler.
 *  It is made for handling the GET request at client side.
 *
 */
public class ClientGetRequestHandler extends ClientRequestHandler {

	/**
	 * The handle method sends a HTTP GET request using the socket.
	 *  If the requested resource is a HTML file, it filters out the advertisements and looks if there are images which can be downloaded.
	 * 
	 * @param command
	 *        | the Http command which needs to be handled
	 * @param socket
	 *        | the socket used to handle the command
	 * @throws Exception
	 */
	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		//request the resource
		DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
		outputStream.writeBytes(command.getHeader());
		outputStream.flush();
	
		
		InputStream inputStream = socket.getInputStream();
		//the server does not respond
		String headerString = this.getHeaderString(inputStream);
		//System.out.println(headerString);
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
		
	/**
	 * Requests the Other resources specified in the html file.
	 *  Currently it only supports images to be dowloaded.
	 * 
	 * @param command
	 *        | the original HTTPCommand
	 * @param inputStream
	 *        | the inputStream of the request
	 * @param outputStream
	 *        | the outputStream of the request
	 * @param content
	 *        | the content of the html file with already filtered out the images
	 * @param header
	 *        | the header of the original command.
	 * @throws Exception
	 */
	public void getOtherResources(HTTPCommand command, InputStream inputStream, DataOutputStream outputStream, String content, HTTPHeader header) throws Exception {
			//get all the source patterns out of the file
			Pattern p = Pattern.compile("<img[^>]*src=[\"']([^\"^']*)", Pattern.CASE_INSENSITIVE);
		    Matcher m = p.matcher(content);
		    //request all the resources
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
