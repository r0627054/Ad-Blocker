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
		System.out.println("found");
		String outString = "HTTP/1.1 200 OK\n\n";
		byte[] filebytes;
		OutputStream outputStream = socket.getOutputStream();
		try {
			filebytes = Files.readAllBytes(fileToServe.toPath());
			writeDate(outputStream);
			writeContentType(outputStream, fileToServe.toPath());
			writeContentLength(outputStream, filebytes.length);
			outputStream.write(outString.getBytes());
			outputStream.write(filebytes);
		} catch (AccessDeniedException e) {
			respond404(socket);
		}
		outputStream.close();		
	}

	private void writeContentLength(OutputStream outputStream, int length) throws IOException {
		String outString = "Content-Length: " + length;
		System.out.println(outString);
		outputStream.write(outString.getBytes());
		
	}

	private void writeContentType(OutputStream outputStream, Path path) throws IOException {
		String outString = "Content-Type: " + getContentTypeFromPath(path);
		outputStream.write(outString.getBytes());
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

	private void writeDate(OutputStream outputStream) throws IOException {
		String outString = "Date: " + java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
		outputStream.write(outString.getBytes());
	}

	private void respond404(Socket socket) throws IOException {
		System.out.println("not found");
		String outString = "HTTP/1.1 404 Not Found\n\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
		outputStream.close();
		
	}
	
	
	

}
