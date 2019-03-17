package lecture.example;

import java.io.*;
import java.net.*;

/**
 * This is an example given, in the slides of during the lecture.
 */
public class TCPClient {

	
	public static void main(String[] args) throws Exception {
		//read from input
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		// host/port to connect to
		Socket clientSocket = new Socket("localhost",9876);
		
		DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		
		//read server data
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		String sentence = inFromUser.readLine();
		
		outToServer.writeBytes(sentence + '\n');
		
		String modifiedSentence = inFromServer.readLine();
		
		System.out.println("FROM SERVER: " + modifiedSentence);
		
		clientSocket.close();
		
	}
}
