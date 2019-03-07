package client.handlers;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import client.commands.HTTPCommand;

public abstract class RequestHandler {

	//public abstract String getRequestHeader();
	
	public abstract void handle(HTTPCommand command, Socket socket)throws Exception;
	
	//VERSION 1 WITH bufferedreader
	/*protected byte[] getBytes(int numberOfBytes, BufferedReader bufferedReader) throws IOException {
		byte[] result =new byte[numberOfBytes];
		for(int i = 0; i < numberOfBytes; i++) {
			result[i] = (byte) bufferedReader.read();
		}
		return result;
	}*/
	
	protected byte[] getBytes(int numberOfBytes, InputStream inputStream) throws IOException {
		byte[] result =new byte[numberOfBytes];
		for(int i = 0; i < numberOfBytes; i++) {
			result[i] = (byte) inputStream.read();
		}
		return result;
	}
	
	
	protected String bytesToString(byte[] bytes) {
		return new String(bytes);
	}
	
	/*
	 //VERSION 1 WITH bufferedreader
	  protected String getHeaderString(BufferedReader bufferedReader) throws IOException {
		String header = "";
		String line = "";
		//keeps reading line, if exist, until blank line
		//the header are separated form the body with a blank line
	    while( ((line = bufferedReader.readLine()) != null) && (!line.trim().isEmpty())){
	    	header += line +"\n";
	    }
	    return header;
	}*/
	
	protected String getHeaderString(InputStream inputStream) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		List<Integer> data = new ArrayList<>();
		int currentByte;
		while( ((currentByte = inputStream.read()) != -1)) {
			if(currentByte == 10 && data.size() >=3 && data.get(data.size() -1) ==13 && data.get(data.size() -2) ==10 && data.get(data.size() -3) ==13 ) {
				break;
			}else {
				data.add(currentByte);
				byteArrayOutputStream.write(currentByte);
			}
		}
		return byteArrayOutputStream.toString("UTF-8");
	}
	
	
	
	
	
	/*public Map<String, String> getHeaderParameters(Socket socket){
		return null;
	}*/
}
