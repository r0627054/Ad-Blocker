package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class GetRequestHandler extends RequestHandler {

	@Override
	public void handle(HTTPCommand command, Socket socket) throws Exception {
		DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
		outStream.writeBytes(command.getHeader());
	outStream.flush();
	
	
	String outStr;
	BufferedReader inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    //Prints each line of the response 
	String r = "";
    while((outStr = inFromServer.readLine()) != null){
        System.out.println(outStr);
    }
	
	
    outStream.close();
	inFromServer.close();
	}

}
