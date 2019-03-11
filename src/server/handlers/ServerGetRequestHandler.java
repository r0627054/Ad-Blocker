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
	public void handle(HTTPCommand command, Socket socket) throws Exception {
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

	private String writeContentLength(int length) throws IOException {
		return "Content-Length: " + length +"\n";
		
	}

	private String writeContentType(Path path) throws IOException {
		return "Content-Type: " + getContentTypeFromPath(path) + "\n";
		
	}

	private String getContentTypeFromPath(Path path) {
		String extension = "";
		String fileName = path.getFileName().toString();
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		switch (extension) {
		case "html":
			return "text/html";
		case "png":
			return "image/png";
		case "jpg":
			return "image/jpg";
		default:
			return "text/plain";
		}
	}

	private String writeDate() throws IOException {
		return "Date: " + java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC)) + "\n";
	}

	private void respond404(Socket socket) throws IOException {
		System.out.println("not found");
		String outString = "HTTP/1.1 404 Not Found\n\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
		outputStream.close();
		
	}
	
	
	

}
