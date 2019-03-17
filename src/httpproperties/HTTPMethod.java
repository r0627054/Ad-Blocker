package httpproperties;

/**
 * The HTTP methods supported in the client.
 *  It stores the method and stores the capital String of the method (methodcode).
 *
 */
public enum HTTPMethod {
	HEAD("HEAD"),
	GET("GET"),
	PUT("PUT"),
	POST("POST");
	
	/**
	 * The variable storing the HTTP method code (capital letters)
	 */
	private final String methodCode;
	
	/**
	 * Initialises the HTTPMethod with the method code
	 * @param methodCode
	 *        | the method code of the http method
	 */
	private HTTPMethod(String methodCode) {
		this.methodCode = methodCode;
	}
	
	/**
	 * Returns the method code of the of the HTTPMETHOD
	 */
	public String getMethodCode() {
		return this.methodCode;
	}
		
}
