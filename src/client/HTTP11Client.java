package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

public class HTTP11Client extends HTTPClient {

	public HTTP11Client() {
		
	}
	
	

	@Override
	public void handleCommand(HTTPCommand command) throws UnknownHostException, IOException {
		if(this.socket == null) {
			this.socket = new Socket(command.getHost(), command.getPort());
		}
		DataOutputStream outStream = new DataOutputStream(this.getSocket().getOutputStream());
		BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		outStream.writeBytes(command.getHeader());
		outStream.flush();
		//String response = inFromServer.readLine();
		//System.out.println(response);
		String response = inFromServer.lines().collect(Collectors.joining());
		System.out.println(response);
		
		
		outStream.close();
		inFromServer.close();
		this.socket.close();
		}

	
}
