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
/**
 * The httpRequestHandler class is a class which has often
 *  used methods of the clientRequestHandler and the ServerRequestHandler.
 *
 */
public class HttpRequestHandler {
	
	/**
	 * Initialises a new Instance of a HttpRequestHandler.
	 * It initialises it without any parameters.
	 */
	public HttpRequestHandler() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Reads the requested amount of bytes and returns it in a byte array.
	 * @param numberOfBytes
	 *        | the amount of bytes which needs to be read from the inputStream.
	 * @param inputStream
	 *        | the inputStream of the current socket.
	 * @return the requested amount of bytes of the inputStream.
	 * @throws IOException
	 */
	public byte[] getBytes(int numberOfBytes, InputStream inputStream) throws IOException {
		//used with content-length is given
		byte[] result =new byte[numberOfBytes];
		for(int i = 0; i < numberOfBytes; i++) {
			result[i] = (byte) inputStream.read();
		}
		return result;
	}
	
	/**
	 * Returns String representation of the byte array.
	 * @param bytes
	 *        | the bytes which needs to be converted to a String
	 * @return
	 */
	public String bytesToString(byte[] bytes) {
		return new String(bytes);
	}
		
	/**
	 * Reads the header out of the inputStream.
	 * @param inputStream
	 *        | the inpuStream which received a request/response.
	 * @return the header of the request/response in a String.
	 * @throws IOException
	 */
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
	
	/**
	 * Reads a fully chuncked message.
	 * @param inputStream
	 *        | the inputStream which has chuncked data.
	 * @return the array of bytes extracted from the chuncked message.
	 * @throws Exception
	 */
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
	

	/**
	 * Handles a Response body which the client received from the server.
	 * @param command
	 *        | the command which sended a request to the server.
	 * @param inputStream
	 *        | the inputStream on which the response is received.
	 * @param header
	 *        | the HTTP header of the response.
	 * @param save
	 *        | Whether or not the response needs to be saved.
	 * @return The content of the body as a byte array.
	 * @throws Exception
	 */
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
	
	/**
	 * Saves the content received from a server in a folder.
	 * @param content
	 *        | the content which needs to be saved.
	 * @param host
	 *        | the host of which the content was received (this will be the folder name)
	 * @param filename
	 *        | the filename which was received form the server.
	 * @param filetype
	 *        | The type of the file, this will be type of the file which will be saved.
	 */
	public void saveFile(byte[] content, String host, String filename, String filetype) {
		new FileSaver( content,  host, filename, filetype);
	}
	
	/**
	 * Returns the the amount of bytes the next chunk will have.
	 * @param inputStream
	 *        | the inputStream on which the chunks are send.
	 * @return the amount of bytes of the next chunk.
	 * @throws Exception
	 */
	public int getLengthOfChunk(InputStream inputStream) throws Exception { 
		String hexAmountOfBytes = bytesToString(this.readChunksLine(inputStream));
		return this.hexaDecimalToInteger(hexAmountOfBytes);
	}
	
	/**
	 * Reads a line of the chunked data until a (13 10) CRLF. 
	 * @param inputStream
	 *        | the inputStream on which the chunked data is received.
	 * @return the byte array of that line of the chunk.
	 * @throws IOException
	 */
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
		
	/**
	 * Converts a hexadecimal number to an Integer.
	 * @param hex
	 *        | the hexaDecimal number represented as a String.
	 * @return the Integer representation of the hexadecimal number.
	 */
	private int hexaDecimalToInteger(String hex) {
		if(hex == null || hex.trim().isEmpty()) {
			return 0;
		}
		return Integer.parseInt(hex, 16);
	}
	
	
	/**
	 * this function only read CRLF ("\r\n") or 13 10 in bytes
	 *  and does nothing more.
	 * @param inputStream
	 *        |the inputStream on which the CRLF needs to be read of.
	 * @throws Exception when the following input is not a CRLF
	 */
	private void readcrlf(InputStream inputStream) throws Exception {
		int r = inputStream.read();
		int n = inputStream.read();
		if(r != 13 || n != 10) {
			throw new Exception("Readed line was not a CRLF");
		}
	}
	
	/**
	 * Returns the header line of the content-length header.
	 * @param length
	 *        | the length of the body.
	 * @return a string representing the content-length header.
	 * @throws IOException
	 */
	protected String writeContentLength(int length) throws IOException {
		//return "Content-Length: " + length +"\n";
		return "Content-Length: " + length;
	}

	/**
	 * Returns the header line of the content-type header.
	 * @param path
	 *        | the type of which the content will be
	 * @return the header line of the content-type header.
	 * @throws IOException
	 */
	protected String writeContentType(Path path) throws IOException {
		//return "Content-Type: " + getContentTypeFromPath(path) + "\n";
		return "Content-Type: " + getContentTypeFromPath(path);
	}

	/**
	 * Returns the content type of a given file.
	 * @param path
	 *        | the path of the file.
	 * @return the content type of a given file.
	 */
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

	/**
	 * Returns the date line of the Date header.
	 * @return a string representing the date header.
	 * @throws IOException
	 */
	protected String writeDate() throws IOException {
		//return "Date: " + java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC)) + "\n";
		return "Date: " + java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
	}
	

	/**
	 * Writes a general 404 response to the ouputStream of the socket.
	 * @param socket 
	 *        | the socket on which a response needs to be written.
	 * @throws IOException
	 */
	public void respond404(Socket socket) throws IOException {
		System.out.println("not found");
		//String outString = "HTTP/1.1 404 Not Found\r\n\r\n";
		String outString = "HTTP/1.1 404 Not Found";
		outString += writeDate() +"\n";
		outString += writeContentLength(0);
		outString +=  "\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
	}
	
	/**
	 * Writes a general 400 response to the outputStream of the socket.
	 * @param socket
	 *        | the socket on which a response needs to be written.
	 * @throws IOException
	 */
	public void respond400(Socket socket) throws IOException {
		System.out.println("bad request 400");
		//String outString = "HTTP/1.1 400 Bad Request\r\n\r\n";
		String outString = "HTTP/1.1 400 Bad Request";
		outString += writeDate() +"\n";
		outString += writeContentLength(0);
		outString +=  "\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
	}

	/**
	 * Writes a general 500 response to the outputStream of the socket.
	 * @param socket 
	 *        | the socket on which a response needs to be written.
	 * @throws IOException
	 */
	public void respond500(Socket socket) throws IOException {
		System.out.println("Server error");
		//String outString = "HTTP/1.1 500 Server Error\r\n\r\n";
		String outString = "HTTP/1.1 500 Server Error";
		outString += writeDate() +"\n";
		outString += writeContentLength(0);
		outString +=  "\r\n\r\n";
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write(outString.getBytes());
	}

}
