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
 * The ServerGetRequestHandler is a ServerRequestHandler which handles the GET request on the server side.
 */
public class ServerGetRequestHandler extends ServerRequestHandler {

	/**
	 * It handles the given GET command,
	 *  If the resource is not found it responds with a 404 response.
	 *  If the is was NOT modified since the date mentioned it responds with a code 304.
	 *  If it was found it returns the resouce with a 200 found message.
	 *  
	 * @param command
	 *        | the incoming get command which will be handled
	 * @param body
	 *        | the body of the incoming http request
	 * @param header
	 *        | the HTTPRequestHeader of the request
	 * @param socket
	 *        | the socket used to handle the command
	 * @throws Exception
	 */
	@Override
	public void handle(HTTPCommand command, byte[] body,HTTPRequestHeader header, Socket socket) throws Exception {
		File fileToServe = new File("resources/server" + command.getPath());
		if(!fileToServe.exists() || !fileToServe.isFile()) {
			respond404(socket);
		} else
		{
			if(header.containsHeader("If-Modified-Since")) {
				Long modifiedDate = fileToServe.lastModified();
				SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
				Date sendedDate = format.parse(header.getHeaderValue("If-Modified-Since"));
				
				if(modifiedDate > sendedDate.getTime()) {
					//if modified after
					respond200(socket, fileToServe);
				}else {
					//was NOT modified
					respond304(socket);
				}
			}else {
				respond200(socket, fileToServe);
			}
		}
	}

	/**
	 * The actual 200 response of a get message. It writes the header and the actual file to the outputStream.
	 * @param socket
	 *        | the socket used to send the response
	 * @param fileToServe
	 *        | the file which is requested.
	 * @throws IOException
	 */
	private void respond200(Socket socket, File fileToServe) throws IOException {
		System.out.println("found " + fileToServe.getPath().toString());
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
			outputStream.write(filebytes);
		} catch (AccessDeniedException e) {
			respond404(socket);
		}
	}

	
	
	
	

}
