package client;

public enum HTTPMethod {
	HEAD("HEAD"),
	GET("GET"),
	PUT("PUT"),
	POST("POST");
	
	private final String methodCode;
	
	private HTTPMethod(String methodCode) {
		this.methodCode = methodCode;
	}
	
	public String getMethodCode() {
		return this.methodCode;
	}
		
}
