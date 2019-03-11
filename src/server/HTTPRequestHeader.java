package server;

import java.util.HashMap;
import java.util.Map;

import client.HTTPHeader;

public class HTTPRequestHeader{
	
	private Map<String,String> headerFields = new HashMap<>();

	public HTTPRequestHeader(String header) {
		extractRequestHeaderInformation(header);
	}
	
	
	private void extractRequestHeaderInformation(String header) {
		String[] lines = header.trim().split("\n");
		for(int i=0; i<lines.length; i++) {
			if(i == 0) {
				String[] splitted = lines[i].split(" ");
				System.out.println(splitted.length);
				headerFields.put("Method", splitted[0]);
				headerFields.put("Path", splitted[1]);
				headerFields.put("Version", splitted[2]);
			}else {
				String[] splitted = lines[i].split(": ");
				headerFields.put(splitted[0].trim(), splitted[1].trim());
			}
		}
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
	
	public String getHttpMethod() {
		return headerFields.get("Method");
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
