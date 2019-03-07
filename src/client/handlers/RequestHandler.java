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
	
	public abstract void handle(HTTPCommand command, Socket socket)throws Exception;
	
	protected byte[] getBytes(int numberOfBytes, InputStream inputStream) throws IOException {
		//used with content-length is given
		byte[] result =new byte[numberOfBytes];
		for(int i = 0; i < numberOfBytes; i++) {
			result[i] = (byte) inputStream.read();
		}
		return result;
	}
	
	
	protected String bytesToString(byte[] bytes) {
		return new String(bytes);
	}
		
	protected String getHeaderString(InputStream inputStream) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		List<Integer> data = new ArrayList<>();
		int currentByte;
		while( ((currentByte = inputStream.read()) != -1)) {
			//header ends with "\r\n\r\n"
			if(currentByte == 10 && data.size() >=3 && data.get(data.size() -1) ==13 && data.get(data.size() -2) ==10 && data.get(data.size() -3) ==13 ) {
				break;
			}else {
				data.add(currentByte);
				out.write(currentByte);
			}
		}
		return out.toString("UTF-8");
	}
	
	protected byte[] readChunks(InputStream inputStream) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int currentByte;
		
		
		for (int i = 0; i < 10; i++) {
			currentByte = inputStream.read();
			System.out.println(currentByte);
		}
		//13
		//10
		/*while(true) {
			currentByte = inputStream.read();
			if(currentByte == 13) {
				inputStream.mark(2);
				currentByte = inputStream.read();
				if(currentByte == 10) {
					//thats the end of a chunk
					break;
				}
			
			}
			if(currentByte == -1) {
				//end of the stream
				break;
			}
			System.out.println(currentByte);
			out.write(currentByte);
		}
		System.out.println("hhhh" +inputStream.read());*/
		System.out.println(out.toString("UTF-8"));
		
		
		return null;
	}
	
	
	
	
	
	
	
	/*public Map<String, String> getHeaderParameters(Socket socket){
		return null;
	}*/
}
