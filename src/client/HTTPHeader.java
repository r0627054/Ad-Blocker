package client;

import java.util.HashMap;
import java.util.Map;

public class HTTPHeader {

	private int statusCode;
	private Map<String,String> headerFields = new HashMap<>();

	
	public HTTPHeader(String header) {
		this.extractResponseHeaderInformation(header);
	}
	
	private void extractResponseHeaderInformation(String header) {
		String[] lines = header.trim().split("\n");
		for(int i=0; i<lines.length; i++) {
			if(i == 0) {
				String[] splitted = lines[i].split(" ");
				//Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
				//The status-code is extracted from the header		
				this.setStatusCode(Integer.parseInt(splitted[1]));
			}else {
				String[] splitted = lines[i].split(": ");
				//System.out.println(splitted[1].trim());
				headerFields.put(splitted[0].trim(), splitted[1].trim());
			}
		}
	}

	public int getStatusCode() {
		return statusCode;
	}

	private void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Map<String, String> getHeaderFields() {
		return headerFields;
	}

	private void setHeaderFields(Map<String, String> headerFields) {
		this.headerFields = headerFields;
	}
	
	public boolean containsHeader(String headerKey) {
		return headerFields.containsKey(headerKey);
	}
	
	
	public int getContentLength() {
		String length = getHeaderFields().get("Content-Length");
		if(length == null) {
			return -1;
		}else {
			return Integer.parseInt(length);
		}
	}
	
	public String getHeaderValue(String key) {
		return headerFields.get(key);
	}
	
	
	public String getContentSubTypeResponse() {
		//GENERAL FORMAT:  type/subtype;parameter=value
		//this method returns the subtype
		String[] splitted =  getHeaderFields().get("Content-Type").split("/");
		try {
			String subtype = splitted[1];
			if(subtype.contains(";")) {
				return subtype.split(";")[0];
			}
			return subtype;
		} catch (Exception e) {
			return "html";
		}
	}
	
	
	
	
	
}
