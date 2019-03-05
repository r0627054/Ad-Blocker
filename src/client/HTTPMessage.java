package client;

public class HTTPMessage {

	private HTTPHeader header;
	private HTTPBody body;
	
	
	public HTTPMessage(HTTPHeader header, HTTPBody body) {
		this.setHeader(header);
		this.setBody(body);
	}
	
	public HTTPHeader getHeader() {
		return header;
	}
	
	private void setHeader(HTTPHeader header) {
		if(header == null) {
			throw new IllegalArgumentException("Http message header cannot be null");
		}
		this.header = header;
	}
	
	public HTTPBody getBody() {
		return body;
	}
	
	private void setBody(HTTPBody body) {
		//body can be null
		this.body = body;
	}
	
	
	
	
	
}
