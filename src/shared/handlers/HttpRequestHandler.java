package shared.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Path;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import client.HTTPHeader;
import client.commands.HTTPCommand;
import client.commands.HTTPCommandFactory;
import files.FileSaver;
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
	

	protected byte[] handleOneRequest(HTTPCommand command, InputStream inputStream, HTTPHeader header, boolean save) throws Exception{
		byte[] content = null;
		if(header.containsHeader("Content-Length")) {
			//reads the correct amount of bytes and  stores it in a file
	    	int contentLength = header.getContentLength();
	    	 content = this.getBytes(contentLength, inputStream);
	    	 if(save) {
	    		 this.saveFile(content, command.getHost(), command.getBaseFileName(), header.getContentSubTypeResponse());
	    	 }    	
	    }else if(header.containsHeader("Transfer-Encoding") && "chunked".equals(header.getHeaderValue("Transfer-Encoding")) ) {
	    	//reads all the chunks and stores it in a file
	    	content = this.readChunks(inputStream);
	    	if(save) {
	    		this.saveFile(content, command.getHost(), command.getBaseFileName(), header.getContentSubTypeResponse());
	    	}
	    }else {
	    	System.out.println("Nor Content-Length nor Transfer-Encoding is used in the headers");
	    }
		return content;
	}
	
	public void saveFile(byte[] content, String host, String filename, String filetype) {
		new FileSaver( content,  host, filename, filetype);
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
	
	protected String writeContentLength(int length) throws IOException {
		//return "Content-Length: " + length +"\n";
		return "Content-Length: " + length;
	}

	protected String writeContentType(Path path) throws IOException {
		//return "Content-Type: " + getContentTypeFromPath(path) + "\n";
		return "Content-Type: " + getContentTypeFromPath(path);
	}

	private String getContentTypeFromPath(Path path) {
		String extension = "";
		String fileName = path.getFileName().toString();
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		}
		switch (extension) {
		case "html":
			return "text/html";
		case "png":
			return "image/png";
		case "jpg":
			return "image/jpg";
		default:
			return "text/plain";
		}
	}

	protected String writeDate() throws IOException {
		//return "Date: " + java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC)) + "\n";
		return "Date: " + java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
	}

	public void respond404(Socket socket) throws IOException {
		System.out.println("not found");
		String outString = "HTTP/1.1 404 Not Found\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
		//outputStream.close();
		
	}
	public void respond400(Socket socket) throws IOException {
		System.out.println("bad request 400");
		//String outString = "HTTP/1.1 400 Bad Request\n\n";
		String outString = "HTTP/1.1 400 Bad Request\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
		//outputStream.close();
	}

	public void respond500(Socket socket) throws IOException {
		System.out.println("Server error");
		String outString = "HTTP/1.1 500 Server Error\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
		//outputStream.close();
		
	}

}
