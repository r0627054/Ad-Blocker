package server.handlers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;

import client.commands.HTTPCommand;

public class ServerGetRequestHandler extends ServerRequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		File fileToServe = new File("resources/server" + command.getPath());
		System.out.println(command.getPath());
		if(!fileToServe.exists()) {
			System.out.println("not found");
			String outString = "HTTP/1.1 404 Not Found\n\n";
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(outString.getBytes());
			outputStream.close();
		} else
		{
			System.out.println("found");
			String outString = "HTTP/1.1 200 OK\n\n";
			OutputStream outputStream = socket.getOutputStream();
			byte[] filebytes = Files.readAllBytes(fileToServe.toPath());
			outputStream.write(outString.getBytes());
			outputStream.write(filebytes);
			outputStream.close();
		}
		
//      output.write(outString.getBytes());
		
	}
	
	
	

}
