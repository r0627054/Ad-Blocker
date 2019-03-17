package server.handlers;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

import client.commands.HTTPCommand;
import server.HTTPRequestHeader;

/**
 * The serverHeadRequestHandler is a serverRequestHandler which is created to handle HEAD requests.

 */
public class ServerHeadRequestHandler extends ServerRequestHandler {

	/**
	 * It handles the HEAD request,
	 *  and checks if the resource can be found and what the last modified scince header is.
	 * @param command
	 *        | the incoming command which will be handled
	 * @param body
	 *        | the body of the incoming http request
	 * @param header
	 *        | the HTTPRequestHeader of the request
	 * @param socket
	 *        | the socket used to handle the command
	 * @throws Exception
	 */
	@Override
	public void handle(HTTPCommand command, byte[] body, HTTPRequestHeader header, Socket socket) throws Exception {
		File fileToServe = new File("resources/server" + command.getPath());
		
		if(!fileToServe.exists() || !fileToServe.isFile()) {
			response404Head(socket);
		}else {
			if(header.containsHeader("If-Modified-Since")) {
				Long modifiedDate = fileToServe.lastModified();
				SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Date sendedDate = format.parse(header.getHeaderValue("If-Modified-Since"));
				
				if(modifiedDate > sendedDate.getTime()) {
					//if modified after
					response200Head(socket, fileToServe);
				}else {
					//was NOT modified
					response304Head(socket);
				}
			}else {
				response200Head(socket, fileToServe);
			}
		}
	}
	
	
	/**
	 * Responds with a 304 code: Not Modified. With only the head
	 * @param socket 
	 *        | the socket used to write the output to the client.
	 * @throws IOException
	 */
	private void response304Head(Socket socket) throws IOException {
		String output = "HTTP/1.1 304 Not Modified\n";
		output += writeDate() +"\n";
		output += writeContentLength(0);
		output +=  "\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(output.getBytes());
	}
	
	
	/**
	 * Creates a 404 response with only the head.
	 * @param socket
	 *        | the socket on which it should be outputted.
	 * @throws IOException 
	 */
	private void response404Head(Socket socket) throws IOException {
		System.out.println("Head Request: not found");
		String outString = "HTTP/1.1 404 Not Found" + "\n";
		outString += writeDate() +"\n";
		outString += writeContentLength(0);
		outString+=  "\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
	}
	
	/**
	 * Creates a 200 status code response with only the head (needed for HEAD request)
	 * @param socket
	 *        | the socket on which it should be outputted. 
	 * @param fileToServe
	 *        | the file which was requested (needed to calculated the contentLength)
	 * @throws IOException
	 */
	private void response200Head(Socket socket, File fileToServe) throws IOException {
		System.out.println("Head Request: 200");
		String headerString = "HTTP/1.1 200 OK\n";
		byte[] filebytes;
		OutputStream outputStream = socket.getOutputStream();
		try {
			filebytes = Files.readAllBytes(fileToServe.toPath());
			headerString += writeDate() +"\n";
			headerString += writeContentType(fileToServe.toPath()) + "\n";
			headerString += writeContentLength(filebytes.length);
			headerString += "\r\n\r\n";
			outputStream.write(headerString.getBytes());
		} catch (AccessDeniedException e) {
			response401AccesDenied(socket);
		}
	}
	
	/**
	 * Creates a 401 status code response with only the head (needed for the request)
	 * @param socket
	 *        | the socket on which it should be outputted. 
	 * @throws IOException
	 */
	private void response401AccesDenied(Socket socket) throws IOException {
		//String headerString = "HTTP/1.1 401 Unauthorized\r\n\r\n";
		String headerString = "HTTP/1.1 401 Unauthorized" +"\n";
		headerString += writeDate() +"\n";
		headerString += writeContentLength(0);
		headerString +=  "\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(headerString.getBytes());
	}
	

}
