package client.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

import client.commands.HTTPCommand;

public abstract class RequestHandler {

	//public abstract String getRequestHeader();
	
	public abstract void handle(HTTPCommand command, Socket socket)throws Exception;
	
	protected byte[] getBytes(int numberOfBytes, BufferedReader bufferedReader) throws IOException {
		byte[] result =new byte[numberOfBytes];
		for(int i = 0; i < numberOfBytes; i++) {
			result[i] = (byte) bufferedReader.read();
		}
		return result;
	}
	
	protected String bytesToString(byte[] bytes) {
		return new String(bytes);
	}
	
	protected String getHeaderString(BufferedReader bufferedReader) throws IOException {
		String header = "";
		String line = "";
		//keeps reading line, if exist, until blank line
		//the header are separated form the body with a blank line
	    while( ((line = bufferedReader.readLine()) != null) && (!line.trim().isEmpty())){
	    	header += line +"\n";
	    }
	    return header;
	}
	
	
	
	
	
	
	
	/*public Map<String, String> getHeaderParameters(Socket socket){
		return null;
	}*/
}
