package shared.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import client.commands.HTTPCommand;
import client.commands.HTTPCommandFactory;
import httpproperties.HTTPMethod;

public class HttpRequestHandler {
	
	public HttpRequestHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public byte[] getBytes(int numberOfBytes, InputStream inputStream) throws IOException {
		//used with content-length is given
		byte[] result =new byte[numberOfBytes];
		for(int i = 0; i < numberOfBytes; i++) {
			result[i] = (byte) inputStream.read();
		}
		return result;
	}
	
	public String bytesToString(byte[] bytes) {
		return new String(bytes);
	}
		
	public String getHeaderString(InputStream inputStream) throws IOException {
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
	
	public HTTPCommand getHttpCommandFromHeader(Socket clientSocket) throws IOException {
		String[] headerString = this.getHeaderString(clientSocket.getInputStream()).split(" ");
		HTTPMethod method = HTTPMethod.valueOf(headerString[0]);
		String uriString = headerString[1];
		//String httpVersionString = headerString[2];
		return HTTPCommandFactory.getHTTPCommand(method, uriString, clientSocket.getLocalPort());
	}
	
	public byte[] readChunks(InputStream inputStream) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		
		
		int lengthOfChunk = this.getLengthOfChunk(inputStream);
		//collects all the chunk information at stores it in byteArrayOutputStream
		while(lengthOfChunk != 0) {
			out.write(this.getBytes(lengthOfChunk, inputStream));
			readcrlf(inputStream);
			lengthOfChunk = this.getLengthOfChunk(inputStream);
		}
		
		return out.toByteArray();
	}
	
	
	public int getLengthOfChunk(InputStream inputStream) throws Exception { 
		String hexAmountOfBytes = bytesToString(this.readChunksLine(inputStream));
		return this.hexaDecimalToInteger(hexAmountOfBytes);
	}
	
	public byte[] readChunksLine(InputStream inputStream) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int currentByte;
		while(true) {
			currentByte = inputStream.read();
			if(currentByte == 13) {
				inputStream.mark(2);
				currentByte = inputStream.read();
				if(currentByte == 10) {
					//thats the end of a line
					break;
				}
			}
			if(currentByte == -1) {
				//the end of the stream
				break;
			}
			out.write(currentByte);
		}
		return out.toByteArray();
	}
		
	private int hexaDecimalToInteger(String hex) {
		if(hex == null || hex.trim().isEmpty()) {
			return 0;
		}
		return Integer.parseInt(hex, 16);
	}
	
	
	//this function only read CRLF ("\r\n") or 13 10 in bytes
	private void readcrlf(InputStream inputStream) throws Exception {
		int r = inputStream.read();
		int n = inputStream.read();
		if(r != 13 || n != 10) {
			throw new Exception("Readed line was not a CRLF");
		}
	}

}
