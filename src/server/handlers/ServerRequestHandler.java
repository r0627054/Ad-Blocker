package server.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import client.commands.HTTPCommand;
import server.HTTPRequestHeader;
import shared.handlers.HttpRequestHandler;

public abstract class ServerRequestHandler extends HttpRequestHandler {

	public abstract void handle(HTTPCommand command, byte[] body, HTTPRequestHeader header, Socket socket)throws Exception;
	
	public void respond304(Socket socket) throws IOException {
		String output = "HTTP/1.1 304 Not Modified\n";
		output += writeDate();
		output += writeContentLength(0);
		output += "\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(output.getBytes());
		outputStream.close();
	}
}
