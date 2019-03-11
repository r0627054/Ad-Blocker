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
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import client.commands.HTTPCommand;

public class ServerGetRequestHandler extends ServerRequestHandler {

	@Override
	public void handle(HTTPCommand command, byte[] body, Socket socket) throws Exception {
		File fileToServe = new File("resources/server" + command.getPath());
		if(!fileToServe.exists() || !fileToServe.isFile()) {
			respond404(socket);
		} else
		{
			respond200(socket, fileToServe);
		}
	}

	private void respond200(Socket socket, File fileToServe) throws IOException {
		System.out.println("found " + fileToServe.getPath().toString());
		String headerString = "HTTP/1.1 200 OK\n";
		byte[] filebytes;
		OutputStream outputStream = socket.getOutputStream();
		try {
			filebytes = Files.readAllBytes(fileToServe.toPath());
			headerString += writeDate();
			headerString += writeContentType(fileToServe.toPath());
			headerString += writeContentLength(filebytes.length);
			headerString += "\n";
			outputStream.write(headerString.getBytes());
			outputStream.write(filebytes);
		} catch (AccessDeniedException e) {
			respond404(socket);
		}
		outputStream.close();
	}

	
	
	
	

}
