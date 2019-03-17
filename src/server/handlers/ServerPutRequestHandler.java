package server.handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import client.commands.HTTPCommand;
import server.HTTPRequestHeader;

/**
 * The ServerPutRequestHandler is a serverRequestHandler which handles the put request on the server side.
 */
public class ServerPutRequestHandler extends ServerRequestHandler {

	/**
	 * It handles the given put command, and if the client did not send a body with the request it will print an empty String.
	 * @param command
	 *        | the incoming put command which will be handled
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
		if(body == null || body.length==0) {
			respond200(socket, "".getBytes());
			//respond400(socket);
		}
		else {
			respond200(socket, body);
		}

	}

	/**
	 * It responds back to the client with a 200 status code.
	 * @param socket
	 *        | the socket used to handle the command
	 * @param body
	 *        | the body of the client request
	 * @throws IOException
	 */
	private void respond200(Socket socket, byte[] body) throws IOException {
		String bodyString = bytesToString(body);
		saveBodyToFile(socket.getInetAddress().toString(), bodyString);
		OutputStream outputStream = socket.getOutputStream();
		String headerString = "HTTP/1.1 200 OK\n";
		headerString += writeDate() +"\n";
		headerString += writeContentLength(0);
		headerString +=  "\r\n\r\n";
		outputStream.write(headerString.getBytes());
		//outputStream.close();
	}

	/**
	 * It saves a body to the file (new file): with name the currentTime in milliseconds.
	 * @param remoteAddress
	 *        | the address of the client
	 * @param bodyString
	 *        | the body of the client request.
	 */
	private void saveBodyToFile(String remoteAddress, String bodyString) {
		File fileToSave = new File("resources/server/" + System.currentTimeMillis() + ".txt");
		try {
			PrintWriter out = new PrintWriter(fileToSave);
			out.write(remoteAddress + ": " + bodyString);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
