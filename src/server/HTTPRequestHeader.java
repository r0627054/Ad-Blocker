package server;

import java.util.HashMap;
import java.util.Map;

/**
 * A http request Header has a status code and a map of header fields.
 *
 */
public class HTTPRequestHeader{
	
	/**
	 * The map containing all the header fields of the header.
	 * The key is the header name and the value is the header value.
	 */
	private Map<String,String> headerFields = new HashMap<>();

	/**
	 * Initialise a HTTP header with a header in String format.
	 * @param header
	 *        | the header in string format
	 */
	public HTTPRequestHeader(String header) {
		extractRequestHeaderInformation(header);
	}
	
	/**
	 * Tries to extract all the header information of the String.
	 * 
	 * @param header
	 *        | the header in string format
	 */
	private void extractRequestHeaderInformation(String header) {
		String[] lines = header.trim().split("\n");
		for(int i=0; i<lines.length; i++) {
			if(i == 0) {
				String[] splitted = lines[i].split(" ");
				//System.out.println(splitted.length);
				headerFields.put("Method", splitted[0]);
				headerFields.put("Path", splitted[1]);
				headerFields.put("Version", splitted[2]);
			}else {
				String[] splitted = lines[i].split(": ");
				headerFields.put(splitted[0].trim(), splitted[1].trim());
			}
		}
	}
	
	/**
	 * Returns the a map containing all the header fields.
	 */
	public Map<String, String> getHeaderFields() {
		return headerFields;
	}

	/**
	 * Sets the header fields variable of the HTTP header 
	 * @param headerFields
	 *        | the map containing the header fields. 
	 */
	private void setHeaderFields(Map<String, String> headerFields) {
		this.headerFields = headerFields;
	}
	
	/**
	 * Returns true if a header is found with the given key.
	 * @param headerKey
	 *        | the header key
	 * @return whether or not the HTTPHeader contains a header field.
	 */
	public boolean containsHeader(String headerKey) {
		return headerFields.containsKey(headerKey);
	}
	
	/**
	 * Returns the getContentLength field result.
	 */
	public int getContentLength() {
		String length = getHeaderFields().get("Content-Length");
		if(length == null) {
			return -1;
		}else {
			return Integer.parseInt(length);
		}
	}
	
	/**
	 * Returns the value of a header field
	 * @param key
	 *        | the header field variable
	 * @return
	 */
	public String getHeaderValue(String key) {
		return headerFields.get(key);
	}
	
	/**
	 * Returns the http Method
	 */
	public String getHttpMethod() {
		return headerFields.get("Method");
	}
	
	/**
	 * Returns the subType of the response
	 * By default it returns html
	 * @return
	 */
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
