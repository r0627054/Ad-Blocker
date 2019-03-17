package server.handlers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

import client.commands.HTTPCommand;
import server.HTTPRequestHeader;

public class ServerGetRequestHandler extends ServerRequestHandler {

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
