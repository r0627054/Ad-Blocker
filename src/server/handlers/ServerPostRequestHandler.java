package server.handlers;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import client.commands.HTTPCommand;
import server.HTTPRequestHeader;

public class ServerPostRequestHandler extends ServerRequestHandler {

	@Override
	public void handle(HTTPCommand command, byte[] body,HTTPRequestHeader header, Socket socket) throws Exception {
		if(body == null || body.length==0) {
			respond400(socket);
		}
		else {
			respond200(socket, body);
		}

	}

	private void respond200(Socket socket, byte[] body) throws IOException {
		String bodyString = bytesToString(body);
		saveBodyToFile(socket.getInetAddress().toString(), bodyString);
		String headerString = "HTTP/1.1 200 OK\n";
		headerString += writeDate();
		headerString += "\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(headerString.getBytes());
		outputStream.close();
		
	}

	private void saveBodyToFile(String remoteAddress, String bodyString) {
		File fileToSave = new File("resources/server/postbodies.txt");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave, true));
			out.write(remoteAddress + ": " + bodyString + "\n");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



}
